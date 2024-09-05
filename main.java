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
    private static int parse_command_line_arguments ( String[] args )
    {

        // Success
        return 1;
    }

    
    // Entry point
    public static void main(String[] args) {
        
        // Initialized data
        DFA dfa = new DFA();

        
        dfa.addSigma('0');
        dfa.addSigma('1');

        // Success
        System.exit(0);
    }
}
