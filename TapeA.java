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

    @Override
    public String toString ( )
    {

        String s = "";

        for (int i = lim - 132; i != -1; i--) 
        {
            char nc = (char)(nl[i] + '0');
            if (nc == '0') nc = '☐';
            if ( head < 0 && i == -head - 1 )
            {
                String r = Colorful.format(String.valueOf(nc), Colorful.Color.RED);
                s += r;
            }
            else
                s += nc;
        }
        
        for (int i = 0; i < lim; i++) 
        {
            char pc = (char)(pl[i] + '0');
            if (pc == '0') pc = '☐';

            if ( head >= 0 && i == head )
            {
                String r = Colorful.format(String.valueOf(pc), Colorful.Color.RED);
                s += r;
            }
            else
                s += (char)pc;
        }

        return s;
    }
}