package tcp.com.drzk.tcpserver;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.Socket;  
import java.net.UnknownHostException;  

/**
 * TCP�ͻ���
 * ����bye�˳��ͻ��˶�
 * @author LENOVO
 * 2018��3��14��
 */
public class TCPClient {  
  

	    /**
	     * �ͻ��˵��÷���ͨѶ
	     * @param ip tcp�����ip��ַ
	     * @param port tcp����˿�
	     *void
	     */
        public void go(String ip, int port) {  
            try {  
                Socket socket = new Socket(ip, port);  
                RecvThread recv = new RecvThread(socket);
                new Thread(recv).start();  
                  
                SendThread send = new SendThread(socket);  
                new Thread(send).start();  
                } catch (UnknownHostException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        
        /**
         * �����߳�
         * @author LENOVO
         * 2018��3��14��
         */
        class RecvThread implements Runnable{  
            private Socket socket = null;  
            private DataInputStream dis = null;  
              
            public RecvThread(Socket s){  
                this.socket = s;  
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
         * �����߳�
         * @author LENOVO
         * 2018��3��14��
         */
        class SendThread implements Runnable{  
            private Socket socket;  
            private DataOutputStream dos;  
              
            public SendThread(Socket s){  
                this.socket = s;  
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
                        if("bye".equals(str))  
                            System.exit(0);  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  
        
        public static void main(String[] args) throws UnknownHostException, IOException{
        	String ip = "127.0.0.1";
        	int port = 8888;
            new TCPClient().go(ip, port);  
        }  
         
}  