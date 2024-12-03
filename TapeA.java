public class TapeA implements TapeInterface {

    private char[] l;
    private int head = 0;

    public TapeA ( char[] tape )
    {
        l = tape;
    }

    @Override
    public char read ( )
    {
        return l[head];
    }

    @Override
    public void write ( char c )
    {
        l[head] = c;
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