package Broadcast;

import java.net.*;
import java.io.*;

public class ChatClient {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("���� : java ChatClient id ������ ���� ip");
			System.exit(1);
		}
		Socket sock = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		boolean endflag = false;

		try {
			/******************************************************************
			 * �Է¹��� ip�� 10001�� ��Ʈ�� ����( args[0] : id, args[1] : ���� ip)
			 * 1. ������ �����ϱ� ���� Socket �����ϰ�,
			 * Socket���κ��� InputStream�� OutputStream�� ���ͼ�
			 * ���� Buffered�� PrintWriter ���·� ��ȯ��Ŵ
			 ******************************************************************/
			sock = new Socket(args[1], 10001);
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			/******************************************************************
			 * 2. Ű����κ��� �Է¹ޱ� ���� BufferedReader�� ������ ��,
			 * �����κ��� ���޵� ���ڿ��� ����Ϳ� ����ϴ� InputThread ��ü�� ����
			 ******************************************************************/
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			// ������� id�� ������ �����Ѵ�
			pw.println(args[0]);
			pw.flush();
			InputThread it = new InputThread(sock, br); // ������ �޼��� �ޱ� ����
			it.start();
			/******************************************************************
			 * 3. Ű����κ��� �� �پ� �Է¹޾� ������ ����(/quit�� �Է¹ޱ� ������)
			 ******************************************************************/
			String line = null;
			while ((line = keyboard.readLine()) != null) {
				pw.println(line);
				pw.flush();
				if (line.equals("/quit")) {
					endflag = true;
					break;
				}
			}
			System.out.println("Ŭ���̾�Ʈ�� ������ �����մϴ�.");
		} catch (Exception ex) {
			if (!endflag)
				System.out.println(ex);
		} finally {
			try {
				if (pw != null)
					pw.close();
			} catch (Exception ex) {}
			try {
				if (sock != null)
					sock.close();
			} catch (Exception ex) {}
		}
	}
}

/******************************************************************
 * 4. �����κ��� ���� ���� ���ڿ��� ����Ϳ� ����ϴ� InputThread ��ü�� �����Ͽ�
 * BuffereadReader�� Socket ��ü�� ���ڷ� ���� ����
 ******************************************************************/

class InputThread extends Thread {
	private Socket sock = null;
	private BufferedReader br = null;
	public InputThread(Socket sock, BufferedReader br) {
		this.sock = sock;
		this.br = br;
	}

	/******************************************************************
	 * 5. �����κ��� ���ڿ��� �о� �鿩 ����Ϳ� �����
	 ******************************************************************/
	public void run() {
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception ex) {}
			try {
				if (sock != null) {
					sock.close();
				}
			} catch (Exception ex) {}
		}
	}
}