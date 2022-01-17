package kwangwoon.test03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientEx03 extends Thread {
	static Scanner sc = new Scanner(System.in);
	BufferedReader br; // <- PrintWriter�� �̿��ؼ� �������� ���� ������ �б�
	
	public ClientEx03(BufferedReader br) {
		this.br = br;
	}
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
			
			// �а� ���� ��ü �غ�
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// Ŭ���̾�Ʈ ������ ���� �Ҷ� userId�� args�� �Բ� �Է� �޵��� �Ѵ�.
			System.out.println("args.length =>" + args.length);
			System.out.println("args[0] => " + args[0]);
			if(args.length == 0) {
				System.out.println("����� ���̵� �����ϴ�.");
				System.out.println("ex) java -cp . kwangwoon.test03.ClientEx03 userId");
				pw.println("no-user");
				pw.flush();
				if(socket != null) socket.close();
				return;
			}
			String userId = args[0];
			pw.println(userId);
			pw.flush();
			
			// Ŭ���̾�Ʈ ���� �� br��ü �ʱ�ȭ
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new ClientEx03(br).start(); // Ŭ���̾�Ʈ ���� �� ������ ����.
			
			// Ŭ���̾�Ʈ ���� �����̸� �޼��� �Է� ��� ����
			while(true) {
				//System.out.print("�Է�: ");
				String line = sc.nextLine();
				pw.println(line);
				pw.flush();
			}
			
		} catch (UnknownHostException e) {
			System.out.println("������ ã�� ���ߴ�!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // main�� ��
	
	@Override
	public void run() {
		// �������� �������� �޽��� ���
		while(true) {
			try {
				String line = br.readLine();
				System.out.println(line);
				Thread.sleep(100); // 0.1�ʿ� �ѹ��� �о� ���Բ�
				
			} catch (SocketException e) {
				System.out.println("������ ������ ��������.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("������ ������ ��� ����!");
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
