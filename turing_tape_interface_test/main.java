import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class main {

    public static int sz = 0;

    public static void print_time_pretty ( double seconds )
    {

        // Initialized data
        double _seconds     = seconds;
        long days         = 0,
             hours        = 0,
             minutes      = 0,
             __seconds    = 0,
             milliseconds = 0,
             microseconds = 0;

        // Days
        while ( _seconds > 86400.0 ) { days++;_seconds-=286400.0; };

        // Hours
        while ( _seconds > 3600.0 ) { hours++;_seconds-=3600.0; };

        // Minutes
        while ( _seconds > 60.0 ) { minutes++;_seconds-=60.0; };

        // Seconds
        while ( _seconds > 1.0 ) { __seconds++;_seconds-=1.0; };

        // milliseconds
        while ( _seconds > 0.001 ) { milliseconds++;_seconds-=0.001; };

        // Microseconds        
        while ( _seconds > 0.000001 ) { microseconds++;_seconds-=0.000001; };

        // Print days
        if ( days != 0 )
            System.out.printf("%d D, ", days);
        
        // Print hours
        if ( hours != 0 )
            System.out.printf("%d h, ", hours);

        // Print minutes
        if ( minutes != 0 )
            System.out.printf("%d m, ", minutes);

        // Print seconds
        if ( __seconds != 0 )
            System.out.printf("%d s, ", __seconds);
        
        // Print milliseconds
        if ( milliseconds != 0 )
            System.out.printf("%d ms, ", milliseconds);
        
        // Print microseconds
        if ( microseconds != 0 )
            System.out.printf("%d us", microseconds);
        
        // Done
        return;
    }

    public static void testTape ( TapeInterface t )
    {
        long t0 = 0, t1 = 0;
        double tDelta = 0;
        boolean running = true;
        int state = 0;

        t0 = System.nanoTime();

        for (int i = 0; i < sz - 1; i++) {

            switch (state) {
                case 0:
                    {
                        char c = t.read();
                        if ( c == '0' ) 
                        {
                            state = 0;
                            t.right();
                            t.write('0');
                        }
                        else if ( c == '1' )
                        {
                            state = 1;
                            t.right();
                            t.write('0');
                        }
                        else
                        {
                            t.left();
                            state = 3;
                        }
                    }
                    break;
                case 1:
                    {
                        char c = t.read();
                        if ( c == '0' ) 
                        {
                            state = 2;
                            t.right();
                            t.write('0');
                        }
                        else if ( c == '1' )
                        {
                            state = 1;
                            t.right();
                            t.write('1');
                        }
                        else
                        {
                            t.left();
                            state = 3;
                        }
                    }
                    break;
                case 2:
                    {
                        char c = t.read();
                        if ( c == '0' ) 
                        {
                            state = 1;
                            t.right();
                            t.write('1');
                        }
                        else if ( c == '1' )
                        {
                            state = 2;
                            t.right();
                            t.write('1');
                        }
                        else
                        {
                            t.left();
                            state = 3;
                        }
                    }
                    break;
                case 3:

                    running = false;

                    break;
            }
        }

        t1 = System.nanoTime();

        // 
        tDelta = ((double)(t1 - t0)) / (double) 1000000000;

        System.out.printf("%f\n", tDelta);
    }

    public static void main(String[] args) {
        
        for (String string : args) {
            char input[] = null;
            Scanner s;
            try {
                s = new Scanner(new File(string));
                String iS = s.next();
                sz = iS.length();
                input = new char[sz];
                for (int i = 0; i < sz; i++) {
                    input[i] = iS.charAt(i);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    
            TapeInterface al = new TapeAL(input);
            TapeInterface v  = new TapeV(input);
            TapeInterface st = new TapeS(input);
            TapeInterface a  = new TapeA(input);

            System.out.printf("Array List: "); testTape(al); System.out.printf("\n");
            System.out.printf("Vector: "); testTape(v); System.out.printf("\n");   
            System.out.printf("Stack: "); testTape(st); System.out.printf("\n");   
            System.out.printf("Array: "); testTape(a); System.out.printf("\n");   
        }
    }
}