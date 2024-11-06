package fa.nfa;

public class NFAState extends fa.State implements Comparable<NFAState> {
    
    public NFAState ( ) { }

    public NFAState ( String name )
    {
        super(name);
    }

    @Override
    public int compareTo ( NFAState s ) 
    {
        return getName().compareTo(s.getName());
    }

}
