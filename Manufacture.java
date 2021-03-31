import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Manufacture {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //start time
    	long start = System.currentTimeMillis();
    	//get the localhost IP address
        InetAddress host = InetAddress.getLocalHost();
        //Initialize
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
 
        long NumberOfBeamsNeeded = 617643; //get the number of beams
        int sum = 0; 		//number of hours worked
        long NumberLeftReq; //number of left beams requiered
        int count = 1; 		//count the number of days worked until the bridge is finished
        //initially the number of left beams is equal with number of beams needed
        NumberLeftReq = NumberOfBeamsNeeded;
        //print at beginning the number of beams needed
        System.out.println("---------------> Total number of beams needed: "  + NumberOfBeamsNeeded+ "<------------------");
        //go through all the beams needed
        for(int i=1 ; i <= NumberOfBeamsNeeded ; i++){
        	//establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket
            oos = new ObjectOutputStream(socket.getOutputStream());
           //print the number of beams needed left
            System.out.println("Number of beams needed: " + NumberLeftReq);
            //if there are not beams left
            if(NumberLeftReq < 0)
            	//send to the served message exit to know to stop
            	oos.writeObject("exit");
            else 
            	//write in server the beam sent
            	oos.writeObject(""+i);
            NumberLeftReq--; //discrement the number of beams
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            //get the time worked from server
            int time = (int) ois.readObject();
            sum = sum + time; //calculate the number of hours worked
            if(sum == 24)     //if there are 24 hours worked
            {
            	//print message if a day is completed
            	System.out.println("--------------------------> Day " + count + " completed");
            	count++; //increment the number of days
            	sum=0; 	 //the day is reseted to 0
            }
        }
            //close resources
            ois.close();
            oos.close();
            //the end time
            long end = System.currentTimeMillis();
            NumberFormat formatter = new DecimalFormat("#0.00000");
            //print the time of running in seconds
            System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
        }
    
    }

