package test.tcp.test;

import tcp.com.drzk.tcpserver.TCPServer;

public class TCPServerTest {

	public static void main(String[] args) {
		TCPServer tcpServer = new TCPServer();
		int port = 8888;
		tcpServer.go(port);
	}

}
