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
            int before_copies = copies;

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

    @Override
    public boolean accepts(String s) {

        // Argument check
        if ( s.length() == 0 ) 
            return finalStates.contains(initialState);
        
        currentState = initialState;
        
        // Success
        return accepts_r(s);
    }

    @Override
    public NFA clone ( ) {

        // Initialized data
        NFA ret = new NFA();

        copies++;

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
    public Set<NFAState> getToState(NFAState from, char onSymb) {

        
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

        for (NFAState state : ret) {
            Set<NFAState> nfastateset = this.eClosure(state);
            for (NFAState state2 : nfastateset) {
                ret.add(state2);
            }
        }

        return ret;
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
        
        copies = 0;

        accepts(s);

        // Success
        return copies;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {

        // Initialized data
        HashSet<String> _toStates = new HashSet<String>();

        // Check onSymb
        if ( sigma.contains(onSymb) == false ) return false;
        
        // Check for existing transitions
        if ( transition.get(onSymb) != null )
            if ( transition.get(onSymb).keySet().contains(fromState) )
                return false;

        // Check fromState
        if ( states.keySet().contains(fromState) == false ) return false;

        // Check toStates
        for (String state : states.keySet())
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

        System.out.printf("%s\n", this.transition.get(onSymb).get(fromState));

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
        return false;
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
