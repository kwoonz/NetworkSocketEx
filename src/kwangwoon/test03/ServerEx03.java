package kwangwoon.test03;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Hashtable;


// 다중 채팅하기 위해 Thread 상속
public class ServerEx03 extends Thread {
	// 다중 접속자들의 접속 정보를 저장하는 컬렉션 준비
	// 접속한 아이디의 소켓을 저장 하거나 연결 스트리밍을 저장한다.
	static Hashtable<String, PrintWriter> map = new Hashtable<String, PrintWriter>();
	BufferedReader br;
	String userId;
	
	
	
	public ServerEx03(String userId, BufferedReader br) {
		this.userId = userId;
		this.br = br;
	}

	// main 쓰레드에서는 접속자를 기다리는 역할을 한다.
	// 접속자가 접속되면 접속자의 출력정보를 map에 저장하고 
	// 접속자의 스레드를 실행 시킨다.
	// 스레드는 클라이언트가 보낸 데이터를 출력하는 역할을 한다.
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String userId = " ";
		
			try {
				serverSocket = new ServerSocket(9999);
				System.out.println("서버에서 클라이언트 접속 대기 중...");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			while (true) {
				try {
					// 서버 소켓 준비하고 - 사용자 들어오면 입출력 정보 주고 - 다시대기
					Socket socket = serverSocket.accept(); // 접속이 완료 될때까지 대기
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					userId = br.readLine();// 어떤 유저가 들어왔는지 넣어줌
					if("no-user".equals(userId)) { // "no-user"
						System.out.println("사용자 정보가 없습니다!");
						// 밑에 SocketException으로 내려가서 처리를 해줘라
						throw new SocketException();
					}
					System.out.println(userId + "님이 접속 하였습니다."); // 어떤 유저가 들어오는지 출력
					// PrintWriter은 print.out객체랑 같은것
					pw = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
					
					// map에다가 정보 저장하고
					map.put(userId, pw);

					// 서버 실행 및 스레드 실행하고
					new ServerEx03(userId, br).start();

				} catch (SocketException e) {
					try {
						if (br != null)br.close();
						if (pw != null)pw.close();
						System.out.println(userId + "클라이언트 연결이 끊어졌습니다.");
					} catch (IOException e1) {
						System.out.println("클라이언트 연결 해제 오류!");
					}
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("데이터 입출력 오류!");
				}
			} // end of while..
		}
	
	// 스레드, main이 따로따로 반복
	@Override
	public void run() {
		while(true) {
			 String line = null;
	         try {
	        	// 위에 데이터가 들어오면 읽는다
	            line = br.readLine();
	            Enumeration<String> keys = map.keys(); // userId가 key
	            while(keys.hasMoreElements()) {
	               String key = keys.nextElement();
	               PrintWriter pw = map.get(key);
	               pw.println(userId + " : " + line);
	               pw.flush();
	            }
	         } catch (SocketException e) {
	            try {
	               if(br != null) br.close();
	               System.out.println(userId + "클라이언트 연결이 끊어졌습니다.");
	            } catch (IOException e1) {
	            	
	               e1.printStackTrace();
	            }
	            break;
	         } 
	         catch (IOException e) {
	            System.out.println("클라이언트 연결이 끊어졌습니다.");
	            break;
	         }
	      }
	   }
}

