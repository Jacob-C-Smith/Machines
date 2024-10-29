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

import java.util.*;
// Java standard library
import java.util.Map.Entry;

// Machines
import fa.State;
import fa.dfa.DFAState;

// Classes
public class NFA implements NFAInterface {

    // Private fields
    private HashSet<Character> sigma = null;
    private LinkedHashMap<String, NFAState> states = null;
    private NFAState initialState = null;
    private HashSet<NFAState> finalStates = null;
    private NFAState currentState = null;
    private LinkedHashMap<Character, LinkedHashMap<String, HashSet<String>>> transition = null;

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

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToState'");
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {

        // Argument check
        if ( transition.isEmpty() ) return new HashSet<NFAState>();
        
        // Initialized data
        Set<NFAState> ret = new HashSet<NFAState>();
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

        return ret;
    }

    @Override
    public int maxCopies(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxCopies'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDFA'");
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
