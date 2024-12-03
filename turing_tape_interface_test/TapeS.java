import java.util.Stack;

public class TapeS implements TapeInterface {

    private Stack<Character> l = new Stack<Character>();
    private int head = 0;

    public TapeS ( char[] tape )
    {
        for (int i = 0; i < tape.length; i++) 
            l.add(tape[i]);
    }

    @Override
    public char read ( )
    {
        return l.get(head);
    }

    @Override
    public void write ( char c )
    {
        l.set(head, c);
    }
    
    @Override
    public void left ( )
    {
        head--;
    }

    @Override
    public void right ( )
    {
        head++;
    }
}