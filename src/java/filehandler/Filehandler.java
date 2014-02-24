package filehandler;

/**
 *
 * @author Micke
 */
import java.io.*;
import javax.faces.context.FacesContext;

/**
 * Class created to handles the write to file.
 *
 * @author Micke
 */
public class Filehandler {

     private String outputpath;
    /**
     * Reads the content of the output file.
     *
     * @return Content of the output file as a string.
     */
    public String readFromFile() throws IOException {
        String strLine = "";
        //create file object
        outputpath = "C:\\Users\\Micke\\Desktop\\test\\ProjTask\\logging.txt";
        File file = new File(outputpath);
        StringBuilder cBuf = new StringBuilder();
        BufferedInputStream bin = null;
        try {
            //create FileInputStream object
            FileInputStream fin = new FileInputStream(file);

            //create object of BufferedInputStream
            bin = new BufferedInputStream(fin);

            //create a byte array
            byte[] contents = new byte[1024];

            int bytesRead = 0;
            while ((bytesRead = bin.read(contents)) != -1) {
                strLine = new String(contents, 0, bytesRead);
                cBuf.append("\n");
                cBuf.append(strLine);
                cBuf.append("\n");
            }

        } catch (IOException ioe) {
        }
        return cBuf.toString();
    }

    /**
     * Method to used to write log to a txt file.
     *
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
