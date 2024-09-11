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

        // Add characters
        dfa.addSigma('0');
        dfa.addSigma('1');
        
        // Add states
        dfa.addState("a");
        dfa.addState("b");

        // Add transitions
        dfa.addTransition("a", "a", '0');
        dfa.addTransition("a", "b", '1');
        dfa.addTransition("b", "a", '0');
        dfa.addTransition("b", "b", '1');

        // Add initial state
        dfa.setStart("a");
        dfa.setFinal("b");

        // Print the DFA
        System.out.printf("%s\n", dfa.toString());

        // Test strings
        //dfa.accepts("ab");

        // Success
        System.exit(0);
    }
}
