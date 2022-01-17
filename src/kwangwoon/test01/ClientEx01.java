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
		// ������ �����͸� �����ϴ� ���
		String str = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("���� �Է� : ");
		str = sc.nextLine();
		
		InetAddress ia = null;
		DatagramPacket packet = null;
		DatagramSocket socket = null;
		
		try {
			ia = InetAddress.getLocalHost();
			// packet�� �����̶�� ���� �ϸ�ȴ�.
			packet = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 7777);
			// ���⼭�� Ŭ���̾�Ʈ ip�� ���ʿ䰡 ��� �ȳ־��� - ���� ��ǻ�ʹϱ�
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
