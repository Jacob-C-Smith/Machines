// Import
import java.io.File;

/**
 * Tape interface
 * 
 * @file TapeAL.java
 * 
 * @author Jacob Smith
 */
public interface TapeInterface
{
    
    /**
     * What is the value under the read head?
     * 
     * @param void 
     * 
     * @return value under the read head
     */
    public char read ( );

    /**
     * Replace the character under the read head
     * 
     * @param c the character 
     * 
     * @return void
     */
    public void write ( char c );

    /**
     * Move the read head to the left
     * 
     * @param void 
     * 
     * @return void
     */
    public void left ( );
    
    /**
     * Move the read head to the right
     * 
     * @param void 
     * 
     * @return void
     */
    public void right ( );

    /**
     * Dump the contents of the tape
     * 
     * @param void
     * 
     * @return the contents of the tape
     */
    @Override
    public String toString ( );

    /**
     * Dump the contents of the tape to standard out
     * 
     * @param void
     * 
     * @return int 
     */
    public void print ( );
    
    /**
     * Dump the contents of the tape to a file
     * 
     * @param file the file to write to
     * 
     * @return int 
     */
    public void fprint ( File file );
}
