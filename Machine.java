import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Machine
{
    private TapeInterface t;
    private ArrayList<Transition> r;
    char alphabet[];
    char transitions[/* on symb */][/* from state */];

    
    /*
     c : char = read()
     curr_state = transitions[c][curr_state]
     */

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

        // Initialized data
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        // Store the quantity of states
        state_quantity = scanner.nextInt();
        
        // Advance to the next line
        scanner.nextLine();

        // Parse the alphabet
        alphabet = scanner.nextLine().toCharArray();

        // Allocate the transition LUT
        transitions = new char[alphabet.length][state_quantity];

        // // Construct transition LUT
        // while ( scanner.hasNextLine() ) {
        //    
        // }

        // Uninitialized data
        Pattern old_delimiter;
        
        // Push the scanner delimiter
        old_delimiter = scanner.delimiter();

        // Set the scanner delimiter to a comma
        scanner.useDelimiter(",");

        //
        System.out.printf("(FROM, TO, ON, READ, WRITE, DIRECTION)\n");

        // Parse transitions
        {
            for (int i = 0; i < state_quantity - 1; i++)
            {
                for (int j = 0; j < alphabet.length + 1; j++)
                {
                    
                    Scanner s = new Scanner(scanner.nextLine());
                    s.useDelimiter(",");

                    int to = s.nextInt();
                    int wr = s.nextInt();
                    char d = ( s.next().charAt(0) == 'L' ) ? 'L' : 'R';

                    // Print each transition
                    System.out.printf("(%d   , %d , %d , %d   , %d    , %c)\n",
                        i,
                        to,
                        j,
                        j,
                        wr,
                        d
                    );

                    s.close();
                }
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
        
        
    }
    
    @Override
    public String toString ( )
    {
        return "";
    }
}
