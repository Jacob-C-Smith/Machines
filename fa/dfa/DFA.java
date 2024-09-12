/** !
 * Deterministic Finite Automata implementation
 * 
 * @file fa/dfa/DFA.java
 * 
 * @author Jon Flores
 * @author Jacob Smith
 */

// Package
package fa.dfa;

// Java standard library
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Machines
import fa.State;

// Classes
public class DFA implements DFAInterface, Serializable {

    // Private fields
    private Set<Character> sigma = null;
    private Map<String, State> states = null;
    private State initialState = null;
    private Set<State> finalStates = null;
    private State currentState = null;
    private HashMap<Character, HashMap<String,String>> transition = null;
    
    public DFA ( )
    {

        // Construct an alphabet
        this.sigma = new HashSet<Character>();

        // Construct a collection of states
        this.states = new HashMap<String, State>();

        // Construct a collection of the final states
        this.finalStates = new HashSet<State>();

        // Construct a collection of transitions
        this.transition = new HashMap<Character, HashMap<String,String>>();

        // Done
    }

    @Override
    public boolean addState(String name) {
        
        // Initialized data
        boolean ret = ( states.get(name) == null );
        
        // If the state isn't there ...
        if ( ret )
                
            // ... add it to the state collection
            states.put(name, new DFAState(name));
        
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

    private boolean acceptsRecursive ( String s )
    {
        // Base case
        if ( s.length() == 0 )
        {
    
            // Accept 
            return finalStates.contains(currentState) ;
        }

        // Initialized data
        char c = s.charAt(0);
        HashMap<String, String> tr = null;

        // Error check
        if ( sigma.contains(c) == false ) return false; 

        // Store the transition lookup for character c
        tr = transition.get(c);

        currentState = getState(
            tr.get(
                currentState.getName()
            )
        );
        
        // Do it again
        return acceptsRecursive(s.substring(1));
    }

    @Override
    public boolean accepts(String s) {
        
        // Set the current state
        currentState = initialState;

        // Done
        return acceptsRecursive(s);
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
    public boolean addTransition(String fromState, String toState, char onSymb) {
        
        // Check onSymb
        if ( sigma.contains(onSymb) == false ) return false;

        // Check toState
        if ( states.keySet().contains(toState) == false ) return false;
        
        // Check fromState
        if ( states.keySet().contains(fromState) == false ) return false;

        // Check if the set contains the transition character
        if ( this.transition.containsKey(onSymb) == false )
        
            // Construct a transition lookup for the character
            this.transition.put(onSymb, new HashMap<String, String>());
        
        // Store the transition lookup
        this.transition.get(onSymb).put(fromState, toState);;

        // Done
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {

        DFA ret = new DFA();

		try {
            File f = new File("dfa.serialized");
			FileOutputStream fileOut = new FileOutputStream(f); // File.createTempFile("1234", "5678")
            FileInputStream fileIn = null;
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
            ObjectInputStream in = null;

			out.writeObject(this);
			out.close();

            fileIn = new FileInputStream(f);
            in = new ObjectInputStream(fileIn);

            ret = (DFA) in.readObject();
            
		} catch (Exception e) {
			System.out.println(e);
		}

        return ret;
    }
    
    @Override
    public String toString()
    {
        
        // Initialized data
        String q = "";
        String sig = "";
        String delta = "";
        String f = "";

        // Build Q
        for (String state : states.keySet()) 
            q = q + state + " ";
        
        // Build sigma
        for (char c : sigma) 
            sig = sig + Character.toString(c) + " ";
    
        // Build delta
        for ( char c : transition.keySet() )
            delta += Character.toString(c) + " ";
        
        // Append a line feed
        delta += "\n";

        for (String fromState : states.keySet()) {

            // Initialized data
            String c = states.get(fromState).getName();

            
            // Prefix
            delta += fromState + " ";

            for ( char z : transition.keySet() )
            {
                delta += transition.get(z).get(fromState) + " ";
            }

            // Suffix
            delta += "\n";
        }

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
