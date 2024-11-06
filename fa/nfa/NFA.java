/** !
 * Nondeterministic Finite Automata implementation
 *
 * @file fa/nfa/NFA.java
 *
 * @author Jon Flores
 * @author Jacob Smith
 */

// Package
package fa.nfa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
// Java standard library
import java.util.Map.Entry;

// Machines
import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

// Classes
public class NFA implements NFAInterface, Serializable {

    // Private fields
    private HashSet<Character> sigma = null;
    private LinkedHashMap<String, NFAState> states = null;
    private NFAState initialState = null;
    private HashSet<NFAState> finalStates = null;
    private NFAState currentState = null;
    private LinkedHashMap<Character, LinkedHashMap<String, HashSet<String>>> transition = null;
    private static int copies = 0;
    private int stringResidual = 0;

    public NFA ( )
    {

        // Construct an alphabet
        this.sigma = new LinkedHashSet<Character>();

        // Construct a collection of states
        this.states = new LinkedHashMap<String, NFAState>();

        // Construct a collection of the final states
        this.finalStates = new LinkedHashSet<NFAState>();

        // Construct a collection of transitions
        this.transition = new LinkedHashMap<Character, LinkedHashMap<String, HashSet<String>>>();

        // Add the empty state
        this.sigma.add('e');

        // Done
    }

    @Override
    public boolean addState ( String name ) {

        // Initialized data
        boolean ret = ( states.get(name) == null );

        // If the state isn't there ...
        if ( ret )

            // ... add it to the state collection
            states.put(name, new NFAState(name));

        // Done
        return ret;
    }

    @Override
    public boolean setFinal(String name) {

        // Initialized data
        NFAState state = states.get(name);

        // Error checking
        if ( state == null ) return false;

        // Add the state to the final state collection
        finalStates.add(state);

        // Done
        return true;
    }

    @Override
    public boolean setStart(String name) {

        // Initialized data
        NFAState state = states.get(name);

        // Error checking
        if ( state == null ) return false;

        // Set the initial state
        initialState = state;

        // Done
        return true;
    }

    @Override
    public void addSigma(char symbol) {

        // Add the symbol to the alphabet
        sigma.add(symbol);

        // Done
        return;
    }

    /*
    public boolean accepts_r(String s)
    {

        // Initialized data
        char currentSymbol = '\0';
        Set<NFAState> nextStates = null;
        boolean ret = false;

        // Base case
        if ( s.length() == 0 )
        {

            // Compute the set of states we can reach by e-closure
            nextStates = eClosure(currentState);

            // Iterate through each state
            for (NFAState maybeEndState : nextStates) 
                for (NFAState definitelyEndState : finalStates) 
                    ret |= definitelyEndState.getName().equals(maybeEndState.getName());

            // Success
            return ret;
        }

        // Store the current symbol
        currentSymbol = s.charAt(0);

        // Compute the set of next states
        nextStates = getToState(currentState, currentSymbol);

        // Foreach state ...
        for (NFAState nextState : nextStates)
        {
    
            // Initialized data
            NFA nfa_i = this.clone();

            // Update the current state of the NFA
            nfa_i.currentState = nextState;

            // Accumulate results
            ret |= nfa_i.accepts_r( (s.length() == 1) ? "" : s.substring(1) );
        }

        // Got one
        if ( ret == true ) return true;
        
        // Success
        return accepts_r( (s.length() == 1) ? "" : s.substring(1) );
    }
    */

    public boolean accepts_r(String s) {

        // Initialize BFS
        Queue<NFA> queue = new LinkedList<>();
    
        // Get the e-closure of the initial state(s) for the cloned NFA
        Set<NFAState> startStates = eClosure(currentState);  // e-closure of the start state
        queue.add(this);  // Add the cloned NFA to the queue for BFS
        
        // Track visited states (for the initial states, we add them to visited)
        Set<NFAState> visited = new TreeSet<>();
        visited.addAll(startStates);
    
        // Process the input string character by character
        for (int i = 0; i < s.length(); i++) {
            char currentSymbol = s.charAt(i);
            Set<NFAState> nextStates = new HashSet<>();
    
            // Process each NFA in the queue
            int queueSize = queue.size();

            for (int j = 0; j < queueSize; j++) {
                NFA currentNFA = queue.poll();
    
                // For the current NFA, get all reachable states for the current symbol
                Set<NFAState> reachableStates = currentNFA.getToState(currentNFA.currentState, currentSymbol);
    
                // Add the e-closure of all reachable states to the next states
                for (NFAState reachable : reachableStates) {
                    Set<NFAState> eClosureStates = currentNFA.eClosure(reachable);
                    for (NFAState eState : eClosureStates) {
                        if (!visited.contains(eState)) {
                            visited.add(eState);
                            nextStates.add(eState);  // Add state to be processed in the next iteration
                        }
                    }
                }

                // For each reachable state, update the cloned NFA's current state and enqueue it for the next level
                for (NFAState state : nextStates) {
                    NFA nfaClone2 = currentNFA.clone();  // Clone the NFA once for this state
                    nfaClone2.currentState = state;  // Set the current state for the cloned NFA
                    queue.add(nfaClone2);  // Add the cloned NFA to the queue for processing in next steps
                }

                queue.add(currentNFA);
                
            }
        }
        
        // After processing all symbols, check if any of the states in the queue is a final state
        for (NFA nfa : queue) {
            Set<NFAState> nextStates = eClosure(nfa.currentState);
            for (NFAState maybeEndState : nextStates) 
                for (NFAState definitelyEndState : finalStates) 
                    if (definitelyEndState.getName().equals(maybeEndState.getName())) 
                        return true;  // Found a final state
        }

        return false;  // No final state found
    }
    
    @Override
    public boolean accepts(String s) {

        // Argument check
        if ( s.length() == 0 ) 
            return finalStates.contains(initialState);
        
        if ( s.length() == 1 && s.charAt(0) == 'e')
            return finalStates.contains(initialState);

        currentState = initialState;

        stringResidual = s.length();
        
        // Success
        return accepts_r(s);
    }

    @Override
    public NFA clone ( ) {

        // Initialized data
        NFA ret = new NFA();

		try {

            // Initialized data
            File f = new File("nfa.serialized");
			FileOutputStream fileOut = new FileOutputStream(f);
            FileInputStream fileIn = null;
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
            ObjectInputStream in = null;

            // Write ourselves to the object output buffer
			out.writeObject(this);

            // Release resources
			out.close();

            // Construct a FileInputStream to read the file
            fileIn = new FileInputStream(f);

            // Construct an object input stream to read the DFA
            in = new ObjectInputStream(fileIn);

            // Construct the DFA from the stream
            ret = (NFA) in.readObject();

            // Release resources
            in.close();
		}
        
        // Error handling
        catch (Exception e)
        {

            // Write errors to standard error
			System.err.println(e);
		}

        // Done
        return ret;
    }

    @Override
    public Set<Character> getSigma() {

        // Initialized data
        LinkedHashSet<Character> ret = new LinkedHashSet<Character>();

        // Iterate through our set
        for (Character character : sigma)

            // Add the character to the (copy of the) alphabet
            ret.add(character);

        // Done
        return ret;
    }

    @Override
    public NFAState getState(String name) {

        // Done
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {

        // Initialized data
        boolean ret = finalStates.contains(getState(name));

        // Done
        return ret;
    }

    @Override
    public boolean isStart(String name) {

        // Done
        return ( initialState.getName().equals(name) );
    }

    @Override
    public synchronized Set<NFAState> getToState(NFAState from, char onSymb) {

        
        // Initialized data
        Set<NFAState> ret = new HashSet<NFAState>();
        Set<String> ret_prime = new HashSet<String>();

        SequencedSet<Entry<Character, LinkedHashMap<String, HashSet<String>>>> transitions_for_iter = transition.sequencedEntrySet();

        // Iterate through each transition
        for(Entry<Character, LinkedHashMap<String, HashSet<String>>> t : transitions_for_iter){

            // Fast exit
            if ( t.getKey().toString().equals("" + onSymb) == false ) continue;

            if ( t.getValue().keySet().contains(from.getName()) == false ){
                continue;
            }

            for(String u : t.getValue().get(from.getName())){
                ret_prime.add(u);
            }
        }

        for (String t : ret_prime) {
            ret.add(new NFAState(t));
        }
        
        Set<NFAState> retFINAL = new HashSet<NFAState>();
        
        for (NFAState state : ret) {
            retFINAL.add(state);
        }
        
        for (NFAState state : ret) {
            Set<NFAState> nfastateset = this.eClosure(state);
            for (NFAState state2 : nfastateset) {
                retFINAL.add(state2);
            }
        }
        
        return retFINAL;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {

        // Argument check
        if ( transition.isEmpty() ) return new HashSet<NFAState>();
        
        // Initialized data
        Set<NFAState> ret = new TreeSet<NFAState>();
        Set<String> ret_prime = new HashSet<String>();

        // Store the parameter in the return
        // !!! NOT ALWAYS !!!Set
        // only self loops
        //ret.add(states.get(s));

        SequencedSet<Entry<Character, LinkedHashMap<String, HashSet<String>>>> transitions_for_iter = transition.sequencedEntrySet();

        // Iterate through each transition
        for(Entry<Character, LinkedHashMap<String, HashSet<String>>> t : transitions_for_iter){

            // Fast exit
            if ( t.getKey().toString().equals("e") == false ) continue;

            //System.out.printf("%s --> ", t.getKey());
            //System.out.printf("%s\n", t.getValue());
            //System.out.printf("\n\n\n=== %s ===\n=== %s ===\n=== %s ===\n", t.getKey(), t.getValue().keySet(), s.getName());

            if ( t.getValue().keySet().contains(s.getName()) == false ){
                continue;
            }

            for(String u : t.getValue().get(s.getName())){
                ret_prime.add(u);
            }
        }
        //System.out.printf("\nFOUND %d TRANSITIONS FROM %s --> %s\n", ret_prime.size(), s.getName(), ret_prime.toString());

        for (String t : ret_prime) {
            ret.add(new NFAState(t));
        }

        for (NFAState state : ret) {
            Set<NFAState> nfastateset = this.eClosure(state);
            for (NFAState state2 : nfastateset) {
                ret.add(state2);
            }
        }

        // Add state s
        ret.add(s);

        // Done
        return ret;
    }

    @Override
    public int maxCopies(String s) {
        int ret = 0;
        // Initialize BFS
        Queue<NFA> queue = new LinkedList<>();
    
        currentState = initialState;

        // Get the e-closure of the initial state(s) for the cloned NFA
        Set<NFAState> startStates = eClosure(currentState);  // e-closure of the start state
        ret += startStates.size();
        queue.add(this);  // Add the cloned NFA to the queue for BFS
        
        // Track visited states (for the initial states, we add them to visited)
        Set<NFAState> visited = new TreeSet<>();
        visited.addAll(startStates);
    
        // Process the input string character by character
        for (int i = 0; i < s.length(); i++) {
            char currentSymbol = s.charAt(i);
            Set<NFAState> nextStates = new TreeSet<>();
    
            // Process each NFA in the queue
            int queueSize = queue.size();

            for (int j = 0; j < queueSize; j++) {
                NFA currentNFA = queue.poll();
    
                // For the current NFA, get all reachable states for the current symbol
                Set<NFAState> reachableStates = currentNFA.getToState(currentNFA.currentState, currentSymbol);
                boolean skp= false;
                // Add the e-closure of all reachable states to the next states
                for (NFAState reachable : reachableStates) {
                    Set<NFAState> eClosureStates = currentNFA.eClosure(reachable);
                    for (NFAState eState : eClosureStates) {
                        if (!visited.contains(eState)) {
                            visited.add(eState);
                            if ( skp == false )
                            {
                                if ( reachableStates.isEmpty() );
                                    ret += reachableStates.size() - 1;
                                skp = true;
                            }
                            nextStates.add(eState);  // Add state to be processed in the next iteration
                        }
                    }
                }

                // For each reachable state, update the cloned NFA's current state and enqueue it for the next level
                for (NFAState state : nextStates) {
                    NFA nfaClone2 = currentNFA.clone();  // Clone the NFA once for this state
                    nfaClone2.currentState = state;  // Set the current state for the cloned NFA
                    queue.add(nfaClone2);  // Add the cloned NFA to the queue for processing in next steps
                }

                queue.add(currentNFA);
                
            }
        }
        
        return ret;  // No final state found
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {

        // Initialized data
        HashSet<String> _toStates = new HashSet<String>();

        // Check onSymb
        if ( sigma.contains(onSymb) == false ) return false;

        // Check fromState
        if ( states.keySet().contains(fromState) == false ) return false;

        // Check toStates
        for (String state : toStates)
            if ( states.keySet().contains(state) == false )
                return false;

        // Check if the set contains the transition character
        if ( this.transition.containsKey(onSymb) == false )

            // Construct a transition lookup for the character
            this.transition.put(onSymb, new LinkedHashMap<String, HashSet<String>>());

        // For each to state ...
        for ( String state : toStates )

            // ... build the lookup
            _toStates.add(state);

        //System.out.printf("%s\n", this.transition.get(onSymb).get(fromState));

        if ( this.transition.get(onSymb).get(fromState) != null )
        {

            for ( String state : this.transition.get(onSymb).get(fromState) ) 
            
                // ... build the lookup
                _toStates.add(state);
        }
                
        // ... store the transition lookup
        this.transition.get(onSymb).put(fromState, _toStates);

        // Done
        return true;
    }

    @Override
    public boolean isDFA() {
        
        // TODO: Check if there are any e transitions
        // TODO: Check if there are more than one transitions on the same symbol
        //       for a given state
        // TODO: Ensure that each state has N transitions, where N 
        //       is the cardinality of the alphabet

        // Initialized data
        boolean ret = true;
        ArrayList<Character> a = new ArrayList<Character>();

        for (String fromState : states.keySet()) {

            
            for ( int i = 0; i < a.size(); i++ )
            {
                LinkedHashMap<String, HashSet<String>> var = transition.get(a.get(i));

                if ( var == null ) 
                    return false;

                if ( var.size() > 1 )
                    return false;

                if ( eClosure(new NFAState(fromState)).size() != 1 )
                    return false;
            }
        }


        return ret;
    }

    @Override
    public String toString() {
        
        // Initialized data
        String q = "";
        String sig = "";
        String delta = "";
        String f = "";
        ArrayList<Character> a = new ArrayList<Character>();

        // Build Q
        for (String state : states.keySet())
            q = q + state + " ";

        // Build sigma
        for (char c : sigma)
        {
            sig = sig + Character.toString(c) + " ";
            a.add(c);
        }

        // Build delta
        for ( char c : a )
            delta += Character.toString(c) + "\t";

        // Append a line feed
        delta += "\n";

        for (String fromState : states.keySet()) {

            // Initialized data
            // Prefix
            delta += fromState + "\t";

            for ( int i = 0; i < a.size(); i++ )
            {
                LinkedHashMap<String, HashSet<String>> var = transition.get(a.get(i));
                delta += ( var == null ) ? "null" + '\t' : transition.get(a.get(i)).get(fromState) + "\t";
            }

            // Suffix
            delta += "\n";
        }

        // Build F
        for (NFAState state : finalStates)
            f = f + state.getName() + " ";

        return String.format(
            "Q = { %s}\n" +
            "Sigma = { %s}\n" +
            "delta = \n" +
            "\t%s" +
            "q0 = %s\n" +
            "F = { %s}\n",
            q,
            sig,
            delta,
            initialState.getName(),
            f
        );
    }
}
