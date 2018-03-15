package test.tcp.test;

import tcp.com.drzk.tcpserver.TCPClient;

public class TCPClientTest {

	public static void main(String[] args) {
         TCPClient tcpClient = new TCPClient();
         String ip = "127.0.0.1";
         int port = 8888;
         tcpClient.go(ip, port);
	}

}
