//importing necessary method
import java.io.File;

public class FileHandler {

    // Private constructor to prevent instantiation
    private FileHandler() {

        throw new UnsupportedOperationException();
    }

    // method to list server filer in serverFiles folder
    public static String[] listServerFiles() {

        //create an array for the server files
        String[] serverFiles;

        /*create a File object that links to the serverFiles folder
        and then list them in the array*/
        File f = new File("./serverFiles");
        serverFiles = f.list();

        //return array
        return serverFiles;
    }
}
