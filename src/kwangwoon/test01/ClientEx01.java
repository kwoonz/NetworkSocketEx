package kwangwoon.test01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientEx01 {

	public static void main(String[] args) {
		// 서버에 데이터를 전송하는 기능
		String str = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("내용 입력 : ");
		str = sc.nextLine();
		
		InetAddress ia = null;
		DatagramPacket packet = null;
		DatagramSocket socket = null;
		
		try {
			ia = InetAddress.getLocalHost();
			// packet은 우편물이라고 생각 하면된다.
			packet = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 7777);
			// 여기서는 클라이언트 ip를 알필요가 없어서 안넣었다 - 같은 컴퓨터니까
			socket = new DatagramSocket();
			socket.send(packet);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
