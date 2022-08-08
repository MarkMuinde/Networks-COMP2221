//importing necessary methods
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Client {

	//initialising a Logger object
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	//main method
	public static void main(String[] args) throws Exception {

		/*
		using localhost IP, a DataInputStream object and a DataOutputStream object 
		for the sockets
		*/
		try (Socket socket = new Socket("127.0.0.1", 8888);
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());){
			
			//initialise a String object for client input and server output
			String clientMessage = "";
			String serverMessage = "";

			//check if list is used as cmd line argument
			if (args[0].equals("list")) {

				//assign "list" to clientMessage variable
				clientMessage = args[0];

				//output client message using writeUTF method and then flush
				outputStream.writeUTF(clientMessage);
				outputStream.flush();

				/*read UTF as DataInputStream object and assign it 
				to serverMessage variable, and then log it*/
				serverMessage = inputStream.readUTF();
				logger.log(Level.INFO, serverMessage);

			} 

			// check if list is used as cmd line argument
			else if (args[0].equals("get") && args.length == 2) {

				/*initialise a File object to get the file 
				requested in cmd line argument*/
				File file = new File("." + args[1]);

				// Get the size of the file
				byte[] bytes = new byte[2002];

				//assign the cmd line argument to clientmESSAGE VARIABLE
				clientMessage = args[0] + " " + args[1];

				// output client message using writeUTF method and then flush
				outputStream.writeUTF(clientMessage);
				outputStream.flush();

				//get File object output stream
				FileOutputStream fileOut = getFileOutputStream(file);

				//as long as the FileOutputStream object isn't null
				if (fileOut != null) {

					//while there are bytes in the input stream, write
					while (inputStream.read(bytes) > 0) {

						fileOut.write(bytes);
					}

					//close the stream
					fileOut.close();
				}

			} 
			
			//if invalid command, print it to client & log it
			else {

				logger.log(Level.SEVERE, "Invalid Command");
			}
		} 
		
		//catch any other error
		catch (Exception e) {
			
			logger.log(Level.SEVERE, e.getMessage());
		}
		
	}

	//initialise a FileoutputStream object for the files in serverFiles folder
	private static FileOutputStream getFileOutputStream(File file) throws FileNotFoundException {

		FileOutputStream fileOutputStream = null;

		try {

			fileOutputStream = new FileOutputStream(file);
			return fileOutputStream;
		} 
		
		catch (FileNotFoundException ex) {

			logger.log(Level.SEVERE, "File not found.");
		}

		return fileOutputStream;
	}
}
