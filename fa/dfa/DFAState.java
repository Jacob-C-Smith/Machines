package fa.dfa;
import java.io.Serializable;


public class DFAState extends fa.State implements Serializable {
    
    public DFAState ( ) { }

    public DFAState ( String name )
    {
        super(name);
    }

}
