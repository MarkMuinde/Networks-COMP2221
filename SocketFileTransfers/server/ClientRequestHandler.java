//importing necessary methods
import java.io.*;
import java.util.logging.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientRequestHandler implements Runnable {

    /*privatisation of a Socket object, an integer for the client number
    and a logger object for logging*/
    private Socket clientSocket;
    private int clientNo;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    //Client request handler class
    public ClientRequestHandler(Socket clientSocket, int clientNo) {

        this.clientSocket = clientSocket;
        this.clientNo = clientNo;
    }

    @Override
    public void run() {

        //get the input & output streams from the client's socket
        try (
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream())) {

            //get date & time for log file, initialize client & server messages and IP of client
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd:HH:mm");
            String clientMessage = "";
            String serverMessage = "";
            String clientIP = "";

            while (true) {

                /* read UTF as DataInputStream object and assign it to clientMessage variable,
                 and then note the time */
                clientMessage = inputStream.readUTF();
                LocalDateTime now = LocalDateTime.now();

                //get client socket address as a string and log the client message, time & IP as string
                clientIP = clientSocket.getRemoteSocketAddress().toString();
                String clientLogMessage = String.format("%s:client %s:%s", dtf.format(now), clientIP, clientMessage);

                //write the client log message to the log file
                writeLogMessageToLogFile(clientLogMessage);
                logger.log(Level.INFO, clientLogMessage);

                //if cmd input is list
                if (clientMessage.equals("list")) { 

                    /*array for list of server files as strings which will join 
                     the serverFiles array from filehandler.java*/
                    String[] serverFilesList = FileHandler.listServerFiles();
                    String serverFiles;
                    serverFiles = String.join("\n - ", serverFilesList);

                    //write message to client and flush output stream
                    serverMessage = "From Server to Client-" + clientNo + " Server Files: \n - " + serverFiles;
                    outputStream.writeUTF(serverMessage);
                    outputStream.flush();

                } 
                
                //if cmd input is get
                else if (clientMessage.substring(0, 3).equals("get")) {

                    //create File object using filepath for serverFiles and get file input stream
                    String filePath = "./serverFiles/" + clientMessage.substring(4);
                    File myFile = new File(filePath);
                    FileInputStream fileInputStream = getFileInputStream(myFile);

                    //get bytes of file intp array
                    byte[] bytes = new byte[(int) myFile.length()];

                    //as long as the input stream is not empty
                    if (fileInputStream != null) {

                        /*count for bytes of file, which as long as 
                        is not empty bytes will write to ouput stream */
                        int count;
                        while ((count = fileInputStream.read(bytes)) > 0) {

                            outputStream.write(bytes, 0, count);
                        }

                        //close file input stream and output str
                        fileInputStream.close();
                        outputStream.close();
                    }

                } 
                
                //if cmd argument is exit
                else if (clientMessage.equalsIgnoreCase("exit()")) {
                    break;
                }

            }

            //close socket for next client
            clientSocket.close();
            logger.log(Level.INFO, "Client - {0} exit!! ", clientNo);

        } 
        
        //catch exception
        catch (Exception e) {

            logger.log(Level.SEVERE, e.toString());
        }
    }

    //get file input stream method
    private FileInputStream getFileInputStream(File file) {

        FileInputStream fileOutputStream = null;
        try {

            fileOutputStream = new FileInputStream(file);
            return fileOutputStream;
        } 
        
        //catch exception
        catch (FileNotFoundException ex) {

            logger.log(Level.SEVERE, "File not found.");
        }

        return fileOutputStream;
    }

    //method to write log message to log file
    private void writeLogMessageToLogFile(String message) {

        //create a log.txt file and write to it using a BufferedWriter object
        try (FileWriter myWriter = new FileWriter("log.txt", true); 
            BufferedWriter bw = new BufferedWriter(myWriter)) {

            bw.write(message);
            bw.newLine();
        } 
        
        //catch the exception
        catch (IOException e) {
            
            e.printStackTrace();
        }
    }

}
