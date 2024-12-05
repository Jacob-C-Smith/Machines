import java.io.FileNotFoundException;
import java.util.Scanner;

public class tm_demo_mode {

    public static void main(String[] args) {

        // Initialized data
        Machine tm = null;
        String file = "file2.txt";
        
        // Print the path to the Turing machine file
        System.out.printf("Simulating %s\n", file);

        // Construct the turing machine
        try { tm = new Machine(file); }
        
        // Catch errors
        catch ( FileNotFoundException e ) { e.printStackTrace(); }

        // Run the turing machine
        while ( tm.isHalted() == false )
        {
            tm.printTapeLF();
            tm.step();  
            try { Thread.sleep(5); } catch (Exception e) { }
        }
        tm.printTapeLF();
        
        // Print the sum of the tape
        System.out.printf("\nTape sum: %d\n",tm.tapeSum());
    }
}

