package filehandler;

/**
 *
 * @author Micke
 */
import java.io.*;

public class Filehandler {

    public void log(Object arg) {

        try {

            FileWriter fstream = new FileWriter("C:\\Users\\Micke\\Desktop\\test\\ProjTask\\logging.txt", true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            fbw.write(arg.toString());
            fbw.newLine();
            fbw.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
