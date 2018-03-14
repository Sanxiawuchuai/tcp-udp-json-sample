
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
 * UDPͨѶһ��
 * @author LENOVO
 * 2018��3��14��
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
    	//�ͻ����̳߳أ�������ͨѶ�˿�2200
      /*  Executor myPool = Executors.newCachedThreadPool();
        myPool.execute(new UDPReceiverThread(2100));//����˽����̳߳�
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
            //��������UDP�����̳߳أ�����2100�˿�ͨѶ
            myPool.execute(new UDPSenderThread(msg, ip , 2200));
            System.out.println(msg.toJSONString());
        }
        */
    	int sendPort = 2200;
    	int receivePort = 2100;
    	String ipAddr = "127.0.0.1";
    	//��ʼ��
    	init(ipAddr, sendPort, receivePort);
   }
    
    /**
     * ��ʼ���ӿ�
     * @param ipAddr Ŀ��ip��ַ
     * @param sendPort ������Ϣ�˿�
     * @param receivePort ������Ϣ�˿�
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
 			   System.exit(0);
 		   }
 		   Message m = new Message("server","client",message);
 		   JSONObject msg = m.toJSON();
 		   //������̷߳����̳߳�
 		   pool.execute(new UDPSenderThread(msg, ip, sendPort));
 		   System.out.println(msg.toJSONString());
 	   }
 	   
    } 
    
}
