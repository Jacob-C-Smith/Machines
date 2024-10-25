// Import 
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import fa.dfa.DFA;
import fa.nfa.NFA;
import fa.nfa.NFAState;

public class main {

    /** !
     * DFA example
     * 
     * @param void
     * 
     * @return void
     */
    public static void dfa_main ( )
    {

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

        // Done
        return;
    }

    /** !
     * NFA example
     * 
     * @param void
     * 
     * @return void
     */
    public static void nfa_main ( )
    {
        
        // Initialized data
        NFA nfa = new NFA();
        Set<NFAState> q0EpsilonClosure = null;
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
		nfa.addSigma('0');
		nfa.addSigma('1');
		
        // Add states
		nfa.addState("a");
		nfa.addState("b");
		nfa.addState("c");
		nfa.addState("d");
		
        // Set the initial state(s)
		nfa.setStart("a");
		
	    // Add the transitions between states
        // nfa.addTransition("a", Set.of("a"), '0');
        nfa.addTransition("a", Set.of("b"), 'e');
        nfa.addTransition("b", Set.of("d"), 'e');
		nfa.addTransition("c", Set.of("b"), 'e');
		nfa.addTransition("a", Set.of("c"), '1');

        // Compute epsilon closure on q0
        q0EpsilonClosure = nfa.eClosure(new NFAState("a"));

        // Print states that can be reached from q0
        for ( String string : q0EpsilonClosure )
            System.out.printf("%s, ");

        System.out.printf("\n");

        // Formatting
        Colorful.printf(" - Constructed NFA - \n", Colorful.Color.LIGHT_CYAN);

        // Print the NFA
        System.out.println(nfa.toString());

        // Formatting
        Colorful.printf(" - Results - \n", Colorful.Color.LIGHT_CYAN);

        // Test some strings
        for (String string : strings) 
        {
            
            // Initialized data
            Boolean accepts = nfa.accepts(string);

            System.out.printf(" %-3s --> ", string);  

            // Print the results to standard out            
            if   ( accepts ) Colorful.printf("Accepts\n", Colorful.Color.LIGHT_GREEN);
            else             Colorful.printf("Rejects\n", Colorful.Color.RED);
            
        }

        // Done
        return;
    }

    // Entry point
    public static void main (String[] args) {
       
        // Parse command line arguments
        for (String string : args) 

            // DFA example
            if ( string.equals("dfa") ) dfa_main();
            
            // NFA example
            else if ( string.equals("nfa") ) nfa_main();
        
        // Success
        System.exit(0);
    }
}
