package org.cp.java_netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSocketServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket();

		// 绑定端口
		serverSocket.bind(new InetSocketAddress("localhost", 8080));

		// 开始接受请求，这里会阻塞
		Socket socket = serverSocket.accept();

		// 读取请求，就是读取数据流
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		System.out.println("------------------------------------------------");
		System.out.println(reader.readLine());
		System.out.println("------------------------------------------------");

		// 业务处理，这里省略
		// ...

		// 组装返回，output
		socket.getOutputStream().write("HTTP/1.1 200 OK\r\n".getBytes());
		socket.getOutputStream().write("\r\n".getBytes());
		socket.getOutputStream().write("Hello World!".getBytes());
		socket.getOutputStream().flush();// 冲马桶
	}

}
