// Package
package fa.dfa;

// Java standard library
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

// Machines
import fa.State;

// Classes
public class DFA implements DFAInterface {

    // Private fields
    private Set<Character> sigma = null;
    private Map<String, State> states = null;
    private State initialState = null;
    private Set<State> finalStates = null;
    
    public DFA ( )
    {

        // Construct an alphabet
        this.sigma = new HashSet<Character>();

        // Construct a collection of states
        this.states = new HashMap<String, State>();

        // Construct a collection of the final states
        this.finalStates = new HashSet<State>();

        // Done
    }

    @Override
    public boolean addState(String name) {
        
        // Initialized data
        boolean ret = ( states.get(name) == null );
        
        // If the state isn't there ...
        if ( ret )
                
            // ... add it to the state collection
            states.put(name, null);
        
        // Done
        return ret;
    }

    @Override
    public boolean setFinal(String name) {
        
        // Initialized data
        State state = states.get(name);

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
        State state = states.get(name);

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
        HashSet<Character> ret = new HashSet<Character>();

        // Iterate through our set
        for (Character character : sigma) 

            // Add the character to the (copy of the) alphabet
            ret.add(character);
        
        // Done
        return ret;
    }

    @Override
    public State getState(String name) {
        
        // Done
        return states.get(name);
    }

    @Override
    public boolean isFinal(String name) {
            
        // Initialized data
        boolean ret = finalStates.contains(name);
        
        // Done
        return ret;    
    }

    @Override
    public boolean isStart(String name) {
        
        // Done
        return ( initialState.getName().equals(name) );
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTransition'");
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swap'");
    }
    
}
