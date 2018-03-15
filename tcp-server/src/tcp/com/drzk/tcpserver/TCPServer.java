package tcp.com.drzk.tcpserver;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.ServerSocket;  
import java.net.Socket;  

/**
 * tcp服务端
 * 输入bye退出服务端
 * @author LENOVO
 * 2018年3月14日
 */
public class TCPServer {  
  
	/**
	 * tcp服务端启动方法
	 * @param port 服务端口号
	 *void
	 */
    public void go(int port){  
        ServerSocket serverSocket;  
        Socket socket = null;  
        try {  
        	serverSocket = new ServerSocket(port);  
        	socket = serverSocket.accept();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        SendThread send = new SendThread(socket);  
        new Thread(send).start();  
          
        RecvThread recv = new RecvThread(socket);  
        new Thread(recv).start();  
    }  
    
    /**
     * 发送线程
     * @author LENOVO
     * 2018年3月14日
     */
    class SendThread implements Runnable{  
        private Socket s = null;  
        private DataOutputStream dos = null;  
          
        public SendThread(Socket s){  
            this.s = s;  
            try {  
                dos = new DataOutputStream(s.getOutputStream());  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
          
        @Override  
        public void run() {  
            String str = "";   
            while(true){  
                System.out.println("input message:");  
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
                try {  
                    str = br.readLine();
                    Message msg = new Message("server", "client", str.trim());
                    dos.writeUTF(msg.toJSON().toJSONString());
                    System.out.println(msg.toJSON().toJSONString());
                    if("bye".equals(str))  
                        System.exit(0);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
    
    /**
     * 接收线程
     * @author LENOVO
     * 2018年3月14日
     */
    class RecvThread implements Runnable{  
        private Socket s = null;  
        private DataInputStream dis = null;  
          
        public RecvThread(Socket s){  
            this.s = s;  
            try {  
                dis = new DataInputStream(s.getInputStream());  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
          
        @Override  
        public void run() {  
            String str = "";  
            while(true){  
                try {  
                    str = dis.readUTF();  
                    if("bye".equals(str))  
                        break;  
                    System.out.println("received message:"+str);  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
    
    /** 
     * @param args 
     * @throws IOException  
     */  
    public static void main(String[] args) throws IOException {
    	    int port = 8888;
            new TCPServer().go(port);  
    }  
       
}  
