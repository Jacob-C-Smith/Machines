import java.io.FileNotFoundException;

public class tm {

    public static void main(String[] args) {
        Machine tm;
        try {
            tm = new Machine("file2.txt");
            System.out.printf(tm.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

