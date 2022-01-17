package kwangwoon.test02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// 다중으로 채팅할땐 Thread 상속
// 1대1 할때는 Thead 상속 x
public class ServerEx02 {

	public static void main(String[] args) {
		// Buffered객체는 키보드로 입력 받아서 텍스트로 받겠다.
		BufferedReader in = null;
		BufferedWriter out = null;
		
		ServerSocket listener = null;
		// 접속하는 소켓
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			// 서버 소켓 생성
			listener = new ServerSocket(9999); // 서버 소켓 생성
			System.out.println("연결을 기다리고 있습니다.....");
			// 클라이언트로부터 연결 요청 대기
			socket = listener.accept(); 
			System.out.println("연결 되었습니다.");
			
			// 실제로 입력/출력을 받겠다
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true) {
				// 소켓으로부터 입력받은 것을 inputMessage 에 받는다
				String inputMessage = in.readLine();
				if(inputMessage.equalsIgnoreCase("bye")) { // 이퀄즈와 이퀄즈이그노케이스 차이점은 소문자,대문자 상관없이 다 받겠다는 뜻
				System.out.println("클라이언트에서 bye로 연결을 종료하였음");
				break; // "bye"를 받으면 연결 종료
				}
				System.out.println("클라이언트: " + inputMessage);
				System.out.println("보내기 >> "); // 프롬프트
				String outputMassage = sc.nextLine(); // 키보드에서 한 행 읽기
				out.write(outputMassage + "\n"); // 키보드에서 읽은 문자열 전송
				out.flush(); // out의 스트림 버퍼에 있는 모든 문자열 전송
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(sc != null) sc.close(); // scanner 닫기
				if(sc != null) socket.close(); // 통신용 소켓 닫기
				if(sc != null) listener.close(); // 서버 소켓 닫기
			} catch (IOException e) {
				System.out.println("클라이언트와 채팅 중 오류가 발행하였습니다.");
				e.printStackTrace();
			} 
		}
	}
}
