/** !
 * Colorful print statements via ANSI terminal
 * 
 * @author Jacob Smith
 * @file   Colorful.java
 * @date   Feb 12, 2024
 * 
 * @note   I wrote this in 455. I figured it would be a good drop-in for the DFA driver
 */
public class Colorful
{

    /**
     * This enumeration maps color names to their ANSI foreground codes
     */
    public static enum Color
    {
        BLACK(30),
        RED(41),
        GREEN(32),
        YELLOW(33),
        BLUE(34),
        MAGENTA(35),
        CYAN(36),
        LIGHT_GRAY(37),
        DARK_GRAY(90),
        LIGHT_RED(91),
        LIGHT_GREEN(92),
        LIGHT_YELLOW(93),
        LIGHT_BLUE(94),
        LIGHT_MAGENTA(95),
        LIGHT_CYAN(96),
        WHITE(97);
        
        // The ANSI foreground code
        private int ANSICode;

        // Enum constructor
        Color ( int ANSICode ) { this.ANSICode = ANSICode; }

        // Enum getter
        public int getColorCode ( ) { return ANSICode; }
    };

    /**
     * printf with ANSI colors
     * 
     * @param format  the format string
     * @param c       the foreground color. See Colorful.Colors for a complete list
     * @param objects variadic parameters
     * 
     * @return void
     * 
     * @note This was copied almost verbatum from  my logging library: https://github.com/Jacob-C-Smith/log/blob/main/log.c
     */
    public static final void printf ( String format, Color c, Object...objects )
    {
        // Set the ANSI terminal color using escape code 27, and the color from the caller
        System.out.printf("\u001B[%dm", c.getColorCode());

        // Print what the caller wants to standard out
        System.out.format(format, objects);

        // Reset the ANSI terminal color
        System.out.printf("\u001B[0m");
    }

    /**
     * String.format with ANSI colors
     * 
     * @param format  the format string
     * @param c       the foreground color. See Colorful.Colors for a complete list
     * @param objects variadic parameters
     * 
     * @return void
     * 
     * @note This was copied almost verbatum from  my logging library: https://github.com/Jacob-C-Smith/log/blob/main/log.c
     */
    public static final String format ( String format, Color c, Object...objects )
    {
        // Done
        return String.format("\u001B[%dm\u001B[1m" + format + "\u001B[0m", c.getColorCode(), objects);
    }

    // Entry point
    public static void main ( String args [] )
    {

        // Print column headers
        System.out.printf(
            "\n" +
            " Dark Colors   Light Colors\n" +
            "------------- -------------\n"
        );

        // Test each color
        Colorful.printf("Hello, World! ", Color.BLACK);
        Colorful.printf("Hello, World!\n", Color.WHITE);
        Colorful.printf("Hello, World! ", Color.DARK_GRAY);
        Colorful.printf("Hello, World!\n", Color.LIGHT_GRAY);
        Colorful.printf("Hello, World! ", Color.RED);
        Colorful.printf("Hello, World!\n", Color.LIGHT_RED);
        Colorful.printf("Hello, World! ", Color.GREEN);
        Colorful.printf("Hello, World!\n", Color.LIGHT_GREEN);
        Colorful.printf("Hello, World! ", Color.YELLOW);
        Colorful.printf("Hello, World!\n", Color.LIGHT_YELLOW);
        Colorful.printf("Hello, World! ", Color.BLUE);
        Colorful.printf("Hello, World!\n", Color.LIGHT_BLUE);
        Colorful.printf("Hello, World! ", Color.MAGENTA);
        Colorful.printf("Hello, World!\n", Color.LIGHT_MAGENTA);
        Colorful.printf("Hello, World! ", Color.CYAN);
        Colorful.printf("Hello, World!\n", Color.LIGHT_CYAN);
    }
}