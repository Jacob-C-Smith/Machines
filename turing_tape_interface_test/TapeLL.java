import java.util.LinkedList;

public class TapeLL implements TapeInterface {

    private LinkedList<Character> l = new LinkedList<Character>();
    private int head = 0;

    public TapeLL ( char[] tape )
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