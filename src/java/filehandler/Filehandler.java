package filehandler;

/**
 *
 * @author Micke
 */
import java.io.*;

/**
 * Class created to handles the write to file.
 * @author Micke
 */
public class Filehandler {

    /**
     * Method to used to write log to a txt file.
     * @param arg The message that is going to be logged.
     */
    public void writeLogToFile(Object arg) {

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
