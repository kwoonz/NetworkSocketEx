package kwangwoon.test01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerEx01 {

	public static void main(String[] args) {
		// 서버에서 클라이언트 요청 대기
		
		// 서버를 쓸 수있는 객체 생성 
		byte[] by = new byte[65535];
		DatagramPacket packet = null;
		DatagramSocket socket = null;
		InetAddress ia = null;
		
		
		// IP를 먼저 찾기 - 간단한 소켓 생성
		try {
			ia = InetAddress.getLocalHost();
			//System.out.println("LocalHost >>> " + ia.toString());
			socket = new DatagramSocket(7777, ia);
			
			// packet을 만들어서 넘겨줘야한다 - by의 길이만큼
			packet = new DatagramPacket(by, by.length);
			System.out.println("서버 준비 ...");
			socket.receive(packet);
			
			// packet 받기 - by로 받아서 나머지 공백 없애기
			packet.setData(by);
			
			// 데이터가 들어오면 읽는다. 
			System.out.println(new String(by).trim());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
