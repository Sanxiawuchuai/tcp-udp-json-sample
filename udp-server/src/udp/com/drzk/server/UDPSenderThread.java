
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
 * UDPͨѶһ��
 * @author LENOVO
 * 2018��3��14��
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
            //��������UDP�̳߳أ�����2200�˿�ͨѶ
            myPool.execute(new UDPSenderThread(msg, ip , 2100));
            System.out.println(msg.toJSONString());
        }*/
    	int sendPort = 2100;
    	int receivePort = 2200;
    	String ipAddr = "127.0.0.1";
    	//��ʼ��
    	init(ipAddr, sendPort, receivePort);
   }
   
    /**
     * ��ʼ���ӿ�
     * @param ipAddr Ŀ��ip 
     * @param sendPort ������Ϣ�ӿ�
     * @param receivePort ������Ϣ�ӿ�
     * @throws IOException
     *void
     */
   public static void init(String ipAddr, int sendPort, int receivePort) throws IOException {
	   
	   BufferedReader cIn;
	   String command;
	   InetAddress ip = InetAddress.getByName(ipAddr);
	   Executor pool = Executors.newCachedThreadPool();
	   //��ʼ������˽����߳�
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
		   //������̷߳����̳߳�
		   pool.execute(new UDPSenderThread(msg, ip, sendPort));
		   System.out.println(msg.toJSONString());
	   }
	   
   } 
   
    
}
