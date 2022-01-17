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


// ���� ä���ϱ� ���� Thread ���
public class ServerEx03 extends Thread {
	// ���� �����ڵ��� ���� ������ �����ϴ� �÷��� �غ�
	// ������ ���̵��� ������ ���� �ϰų� ���� ��Ʈ������ �����Ѵ�.
	static Hashtable<String, PrintWriter> map = new Hashtable<String, PrintWriter>();
	BufferedReader br;
	String userId;
	
	
	
	public ServerEx03(String userId, BufferedReader br) {
		this.userId = userId;
		this.br = br;
	}

	// main �����忡���� �����ڸ� ��ٸ��� ������ �Ѵ�.
	// �����ڰ� ���ӵǸ� �������� ��������� map�� �����ϰ� 
	// �������� �����带 ���� ��Ų��.
	// ������� Ŭ���̾�Ʈ�� ���� �����͸� ����ϴ� ������ �Ѵ�.
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String userId = " ";
		
			try {
				serverSocket = new ServerSocket(9999);
				System.out.println("�������� Ŭ���̾�Ʈ ���� ��� ��...");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			while (true) {
				try {
					// ���� ���� �غ��ϰ� - ����� ������ ����� ���� �ְ� - �ٽô��
					Socket socket = serverSocket.accept(); // ������ �Ϸ� �ɶ����� ���
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					userId = br.readLine();// � ������ ���Դ��� �־���
					if("no-user".equals(userId)) { // "no-user"
						System.out.println("����� ������ �����ϴ�!");
						// �ؿ� SocketException���� �������� ó���� �����
						throw new SocketException();
					}
					System.out.println(userId + "���� ���� �Ͽ����ϴ�."); // � ������ �������� ���
					// PrintWriter�� print.out��ü�� ������
					pw = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
					
					// map���ٰ� ���� �����ϰ�
					map.put(userId, pw);

					// ���� ���� �� ������ �����ϰ�
					new ServerEx03(userId, br).start();

				} catch (SocketException e) {
					try {
						if (br != null)br.close();
						if (pw != null)pw.close();
						System.out.println(userId + "Ŭ���̾�Ʈ ������ ���������ϴ�.");
					} catch (IOException e1) {
						System.out.println("Ŭ���̾�Ʈ ���� ���� ����!");
					}
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("������ ����� ����!");
				}
			} // end of while..
		}
	
	// ������, main�� ���ε��� �ݺ�
	@Override
	public void run() {
		while(true) {
			 String line = null;
	         try {
	        	// ���� �����Ͱ� ������ �д´�
	            line = br.readLine();
	            Enumeration<String> keys = map.keys(); // userId�� key
	            while(keys.hasMoreElements()) {
	               String key = keys.nextElement();
	               PrintWriter pw = map.get(key);
	               pw.println(userId + " : " + line);
	               pw.flush();
	            }
	         } catch (SocketException e) {
	            try {
	               if(br != null) br.close();
	               System.out.println(userId + "Ŭ���̾�Ʈ ������ ���������ϴ�.");
	            } catch (IOException e1) {
	            	
	               e1.printStackTrace();
	            }
	            break;
	         } 
	         catch (IOException e) {
	            System.out.println("Ŭ���̾�Ʈ ������ ���������ϴ�.");
	            break;
	         }
	      }
	   }
}

