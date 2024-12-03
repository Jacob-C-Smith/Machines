/**
 * Array list implementation of tape interface
 * 
 * @file TapeAL.java
 * 
 * @author Jacob Smith
 */
import java.util.ArrayList;

// Classes
public class TapeAL implements TapeInterface
{

    // Private fields
    private ArrayList<Character> l = new ArrayList<Character>();
    private int head = 0;

    // Public methods
    public TapeAL ( char[] tape )
    {
        // Copy the tape
        for (int i = 0; i < tape.length; i++) 
            l.add(tape[i]);
    }

    @Override
    public char read ( ) { return l.get(head); }

    @Override
    public void write ( char c ) { l.set(head, c); }
    
    @Override
    public void left ( ) { head--; }

    @Override
    public void right ( ) { head++; }
}