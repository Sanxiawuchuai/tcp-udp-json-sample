
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
import org.json.simple.JSONValue;

/**
 * UDP通讯一端
 * @author LENOVO
 * 2018年3月14日
 */
public class UDPReceiverThread extends Thread {

    private DatagramSocket socket;

    public UDPReceiverThread(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            System.err.println("Could not use the specified port.");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String s = new String(buf);
                s = s.trim();
                System.out.println(s);
                JSONObject o = (JSONObject) JSONValue.parse(s);
                Message m = new Message(o);
                System.out.println(m.toString());

            } catch (IOException ex) {
                Logger.getLogger(UDPReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
    	//客户端线程池，与服务端通讯端口2200
      /*  Executor myPool = Executors.newCachedThreadPool();
        myPool.execute(new UDPReceiverThread(2100));//服务端接收线程池
        BufferedReader cIn;
        String command;
        InetAddress ip = InetAddress.getLocalHost();
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
            //服务器端UDP发送线程池，采用2100端口通讯
            myPool.execute(new UDPSenderThread(msg, ip , 2200));
            System.out.println(msg.toJSONString());
        }
        */
    	int sendPort = 2200;
    	int receivePort = 2100;
    	String ipAddr = "127.0.0.1";
    	//初始化
    	init(ipAddr, sendPort, receivePort);
   }
    
    /**
     * 初始化接口
     * @param ipAddr 目标ip地址
     * @param sendPort 发送消息端口
     * @param receivePort 接收消息端口
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
 			   System.exit(0);
 		   }
 		   Message m = new Message("server","client",message);
 		   JSONObject msg = m.toJSON();
 		   //服务端线程发送线程池
 		   pool.execute(new UDPSenderThread(msg, ip, sendPort));
 		   System.out.println(msg.toJSONString());
 	   }
 	   
    } 
    
}
