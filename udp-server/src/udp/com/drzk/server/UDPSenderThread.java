
package udp.com.drzk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

/**
 * UDP通讯一端
 * @author LENOVO
 * 2018年3月14日
 */
public class UDPSenderThread extends Thread {

    private String m;
    private InetAddress ip;
    private int port;
    private DatagramSocket socket;

    public UDPSenderThread(JSONObject msg, InetAddress add, int p) throws SocketException {
        m = msg.toJSONString();
        ip = add;
        port = p;
        socket = new DatagramSocket();
    }

    public void run() {
        byte[] buf = new byte[256];

        buf = m.getBytes();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(UDPSenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        socket.close();

    }

    public static void main(String[] args) throws IOException {
    	
    /*	BufferedReader cIn;
        String command;
        InetAddress ip = InetAddress.getLocalHost();
        Executor myPool = Executors.newCachedThreadPool();
        myPool.execute(new UDPReceiverThread(2200));
        cIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome");

        while (true) {
            System.out.println("Please, enter a command:");
            command = cIn.readLine();
    	    String message = new String(command);
            message = message.trim();
            if("bye".equalsIgnoreCase(message)) {
            	break;
            }
            Message m = new Message("server", "client", message);
            JSONObject msg = m.toJSON();
            //服务器端UDP线程池，采用2200端口通讯
            myPool.execute(new UDPSenderThread(msg, ip , 2100));
            System.out.println(msg.toJSONString());
        }*/
    	int sendPort = 2100;
    	int receivePort = 2200;
    	String ipAddr = "127.0.0.1";
    	//初始化
    	init(ipAddr, sendPort, receivePort);
   }
   
    /**
     * 初始化接口
     * @param ipAddr 目标ip 
     * @param sendPort 发送消息接口
     * @param receivePort 接收消息接口
     * @throws IOException
     *void
     */
   public static void init(String ipAddr, int sendPort, int receivePort) throws IOException {
	   
	   BufferedReader cIn;
	   String command;
	   InetAddress ip = InetAddress.getByName(ipAddr);
	   Executor pool = Executors.newCachedThreadPool();
	   //初始化服务端接收线程
	   pool.execute(new UDPReceiverThread(receivePort));
	   System.out.println("Welcome");
	   cIn = new BufferedReader(new InputStreamReader(System.in));
	   while(true) {
		   System.out.println("Please, enter a command:");
           command = cIn.readLine();
   	       String message = new String(command);
           message = message.trim();
		   if("bye".equalsIgnoreCase(message)) {
			   break;
		   }
		   Message m = new Message("server","client",message);
		   JSONObject msg = m.toJSON();
		   //服务端线程发送线程池
		   pool.execute(new UDPSenderThread(msg, ip, sendPort));
		   System.out.println(msg.toJSONString());
	   }
	   
   } 
   
    
}
