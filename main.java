// Import 
import fa.dfa.DFA;

public class main {

    /** !
     * Parse command line arguments
     * 
     * @param args the args parameter of the entry point
     * 
     * @return 1 on success, 0 on error
     */
    /* 
    private static int parse_command_line_arguments ( String[] args )
    {

        // Success
        return 1;
    }*/

    
    // Entry point
    public static void main (String[] args) {
       
        // Initialized data
        DFA dfa = new DFA();
        String strings[] =
        { 
            "0",
            "1",
            "00",
            "01",
            "10",
            "11",
            "000",
            "001",
            "010",
            "011",
            "100",
            "101",
            "110",
            "111"
        };

        // Construct the alphabet
		dfa.addSigma('0');
		dfa.addSigma('1');
		
        // Add states
		dfa.addState("3");
		dfa.addState("0");
		dfa.addState("1");
		dfa.addState("2");
		
		// Set the final state
		dfa.setFinal("3");

        // Set the initial state(s)
		dfa.setStart("0");
		
        // Add the transitions between states
		dfa.addTransition("0", "1", '0');
		dfa.addTransition("0", "0", '1');
		dfa.addTransition("1", "3", '0');
		dfa.addTransition("1", "2", '1');
		dfa.addTransition("2", "1", '0');
		dfa.addTransition("2", "1", '1');
		dfa.addTransition("3", "3", '0');
		dfa.addTransition("3", "3", '1');
		
        // Formatting
        Colorful.printf(" - Constructed DFA - \n", Colorful.Color.LIGHT_CYAN);
        
        // Print the DFA
        System.out.println(dfa.toString());

        // Formatting
        Colorful.printf(" - Results - \n", Colorful.Color.LIGHT_CYAN);

        // Test some strings
        for (String string : strings) 
        {
            
            // Initialized data
            Boolean accepts = dfa.accepts(string);

            System.out.printf(" %-3s --> ", string);  

            // Print the results to standard out            
            if   ( accepts ) Colorful.printf("Accepts\n", Colorful.Color.LIGHT_GREEN);
            else             Colorful.printf("Rejects\n", Colorful.Color.RED);
            
        }
        
        // Success
        System.exit(0);
    }
}
