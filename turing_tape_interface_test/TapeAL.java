import java.util.ArrayList;

public class TapeAL implements TapeInterface {

    private ArrayList<Character> l = new ArrayList<Character>();
    private int head = 0;

    public TapeAL ( char[] tape )
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