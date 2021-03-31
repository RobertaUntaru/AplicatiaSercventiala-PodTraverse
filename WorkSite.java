import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class WorkSite {
    
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
   
    public static void main(String args[]) throws IOException, ClassNotFoundException{
    	//start time
    	long start = System.currentTimeMillis();
    	//create the socket server object
        server = new ServerSocket(port);
        //wait for client connection message
        System.out.println("Waiting for the client request");
        //keep listens until receives 'exit' call or program terminates
        while(true){
        	//creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert object
            String message = (String) ois.readObject();
           
            //print message with beams receiver
            System.out.println("Beams Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             
            //intialize the time worked for a beam
            int time = 1; //one hour
            
            //write object to socket
            oos.writeObject(time);
            
            //cloase resources
            ois.close();
            oos.close();
            socket.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) 
            	break;
        }
       
        //close the ServerSocket object
        server.close();
        //calculate the end time
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        //print the time 
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
    }
    
}
