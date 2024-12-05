import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Machine
{

    // Constants
    private final int INDEX_WRITE     = 0;
    private final int INDEX_TO        = 1;
    private final int INDEX_DIRECTION = 2;

    // Private fields
    private char alphabet[];
    private char transitions[/* on symb */][/* from state */][/* 3-tuple: (write, to, direction) */];
    
    private TapeInterface t;
    private boolean halt;
    private int steps = 0;

    private int current_state = 0;
    private int final_state;
    
    /** !
     * Construct a Turing machine from a plaintext file
     * 
     * @param path path to the file
     * 
     * @return this
     * @throws FileNotFoundException 
     */
    public Machine ( String path ) throws FileNotFoundException
    {
        
        // Uninitialized data
        int state_quantity = 0;
        int alphabet_size = 0;
        Pattern old_delimiter;
        String string = "";

        // Initialized data
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        
        // Construct the tape
        t = new TapeA(string.toCharArray());

        // Store the quantity of states
        state_quantity = scanner.nextInt();

        // Store the final state
        final_state = state_quantity - 1;
        
        // Advance to the next line
        scanner.nextLine();

        // Parse the alphabet
        alphabet_size = scanner.nextInt();
        
        // Advance to the next line
        scanner.nextLine();

        // Construct the alphabet array
        alphabet = new char[alphabet_size];

        for (int i = 0; i < alphabet.length; i++)
            alphabet[i] = (char) i;
        
        // Allocate the transition LUT
        transitions = new char[alphabet.length+1][state_quantity-1][3];

        // Push the scanner delimiter
        old_delimiter = scanner.delimiter();

        // Set the scanner delimiter to a comma
        scanner.useDelimiter(",");

        // Parse transitions
        for (int i = 0; i < state_quantity - 1; i++)
        {
            for (int j = 0; j < alphabet.length + 1; j++)
            {
                
                // Initialized data
                Scanner s = new Scanner(scanner.nextLine());
                int to = 0;
                int wr = 0;
                char d = 0;
                
                // Set the delimiter
                s.useDelimiter(",");

                // Parse the (to, write, direction) tuple 
                to = s.nextInt();
                wr = s.nextInt();
                d = s.next().charAt(0);

                // Store the (to, write, direction) tuple 
                transitions[j][i][INDEX_WRITE]     = (char) wr;
                transitions[j][i][INDEX_TO]        = (char) to;
                transitions[j][i][INDEX_DIRECTION] = d;
                
                // Clean up
                s.close();
            }
        }

        // Pop the scanner delimiter
        scanner.useDelimiter(old_delimiter);
        
        // Release the scanner
        scanner.close();

        // Done
    }

    public void step ( )
    {

        // Initialized data
        char onsymb = t.read();
        char transition_tuple[] = transitions[(int)(onsymb)][current_state];

        // Update the tape
        t.write((char)(transition_tuple[INDEX_WRITE]));
        if   (transition_tuple[INDEX_DIRECTION] == 'L') t.left();
        else t.right();

        // Update the state
        current_state = transition_tuple[INDEX_TO];

        // Test for halt
        halt = (current_state == final_state);

        // Increment steps
        steps++;
    }
    
    @Override
    public String toString ( )
    {
        return "";
    public boolean isHalted()
    {

        // Done
        return halt;
    }
    }
}
