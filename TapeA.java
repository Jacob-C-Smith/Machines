public class TapeA implements TapeInterface {

    private char[] pl;
    private char[] nl;
    private int lim;

    private int head = 0;

    public TapeA ( char[] tape )
    {
        //lim = 24576;
        lim = 135;
        pl = new char[lim];
        nl = new char[lim];

        for (int i = 0; i < tape.length; i++)
            pl[i] = (char)(tape[i]-'0');
    }

    @Override
    public char read ( )
    {
        return (head >= 0) ? pl[head] : nl[-head - 1];
    }

    @Override
    public void write ( char c )
    {
        if   (head >= 0) pl[head]      = c;
        else             nl[-head - 1] = c;
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