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
       
        // // Initialized data
        // DFA dfa = new DFA();

        // // Add characters
        // dfa.addSigma('0');
        // dfa.addSigma('1');
        
        // // Add states
        // dfa.addState("a");
        // dfa.addState("b");

        // // Add transitions
        // dfa.addTransition("a", "a", '0');
        // dfa.addTransition("a", "b", '1');
        // dfa.addTransition("b", "a", '0');
        // dfa.addTransition("b", "b", '1');

        // // Add initial state
        // dfa.setStart("a");
        // dfa.setFinal("b");

        // DFA dfa2 = dfa.swap('0', '1');

        DFA dfa = new DFA();

		dfa.addSigma('0');
		dfa.addSigma('1');
		
		dfa.addState("3");
		dfa.setFinal("3");
		
		dfa.addState("0");
		dfa.setStart("0");
		
		dfa.addState("1");
		dfa.addState("2");
		
		
		dfa.setFinal("c");
		dfa.setStart("a");
		dfa.addState("2");
		
		dfa.addTransition("0", "1", '0');
		dfa.addTransition("0", "0", '1');
		dfa.addTransition("1", "3", '0');
		dfa.addTransition("1", "2", '1');
		dfa.addTransition("2", "1", '0');
		dfa.addTransition("2", "1", '1');
		dfa.addTransition("3", "3", '0');
		dfa.addTransition("3", "3", '1');
		
		dfa.addTransition("3", "a", '1');
		dfa.addTransition("c", "a", '1');
		dfa.addTransition("3", "a", '2');
		
        System.out.println(dfa.toString());

        // Success
        System.exit(0);
    }
}
