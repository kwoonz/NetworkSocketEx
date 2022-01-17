package kwangwoon.test02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// �������� ä���Ҷ� Thread ���
// 1��1 �Ҷ��� Thead ��� x
public class ServerEx02 {

	public static void main(String[] args) {
		// Buffered��ü�� Ű����� �Է� �޾Ƽ� �ؽ�Ʈ�� �ްڴ�.
		BufferedReader in = null;
		BufferedWriter out = null;
		
		ServerSocket listener = null;
		// �����ϴ� ����
		Socket socket = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			// ���� ���� ����
			listener = new ServerSocket(9999); // ���� ���� ����
			System.out.println("������ ��ٸ��� �ֽ��ϴ�.....");
			// Ŭ���̾�Ʈ�κ��� ���� ��û ���
			socket = listener.accept(); 
			System.out.println("���� �Ǿ����ϴ�.");
			
			// ������ �Է�/����� �ްڴ�
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true) {
				// �������κ��� �Է¹��� ���� inputMessage �� �޴´�
				String inputMessage = in.readLine();
				if(inputMessage.equalsIgnoreCase("bye")) { // ������� �������̱׳����̽� �������� �ҹ���,�빮�� ������� �� �ްڴٴ� ��
				System.out.println("Ŭ���̾�Ʈ���� bye�� ������ �����Ͽ���");
				break; // "bye"�� ������ ���� ����
				}
				System.out.println("Ŭ���̾�Ʈ: " + inputMessage);
				System.out.println("������ >> "); // ������Ʈ
				String outputMassage = sc.nextLine(); // Ű���忡�� �� �� �б�
				out.write(outputMassage + "\n"); // Ű���忡�� ���� ���ڿ� ����
				out.flush(); // out�� ��Ʈ�� ���ۿ� �ִ� ��� ���ڿ� ����
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if(sc != null) sc.close(); // scanner �ݱ�
				if(sc != null) socket.close(); // ��ſ� ���� �ݱ�
				if(sc != null) listener.close(); // ���� ���� �ݱ�
			} catch (IOException e) {
				System.out.println("Ŭ���̾�Ʈ�� ä�� �� ������ �����Ͽ����ϴ�.");
				e.printStackTrace();
			} 
		}
	}
}
