package Broadcast;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {
	// static�̸� ä���� �ϳ��̴� - �̰��� Ŭ������ �ϳ��̴�.
	HashMap hm = new HashMap();
	public ChatServer() {
		try {
			ServerSocket server = new ServerSocket(10001);
			System.out.println("������ ��ٸ��ϴ�.");
			
			while (true) {
				System.out.println(".....");
				Socket sock = server.accept(); // Ŭ���̾�Ʈ�� ������ ����ϰ� �ִ�.
				ChatThread ct = new ChatThread(sock, hm); // ������ ���ö����� ChatThread�� �ִ�.
				ct.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		new ChatServer();
	}
}

class ChatThread extends Thread {
	private Socket sock;
	private String id;
	private BufferedReader br; // �޼��� �о���̴� ���� 
	private HashMap hm;
	private boolean initFlag = false;
	// ������
	public ChatThread(Socket sock, HashMap hm) {
		System.out.println("ChatThread - Server");
		this.sock = sock; // ��������
		this.hm = hm; // ������ ����
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			id = br.readLine(); // userId �����ϰ�
			broadcast(id + "���� �����߽��ϴ�."); // userId ���
			System.out.println("������ ������� ���̵�� " + id + "�Դϴ�.");
			synchronized (hm) {
				hm.put(this.id, pw);
			}
			initFlag = true;
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	public void run() {
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.equals("/quit"))
					break;
				if (line.indexOf("/to") == 0) { // �ӼӸ��̰�
					sendmsg(line);
				} else {
					broadcast(id + " : " + line); // ��üä���̴�.
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			synchronized (hm) {
				hm.remove(id);
			}
			broadcast(id + " ���� ���� �����߽��ϴ�.");
			try {
				if (sock != null)
					sock.close();
			} catch (Exception ex) {}
		}
	}

	public void sendmsg(String msg) { // �ӼӸ� ����� ���� �������.
		int start = msg.indexOf(" ") + 1;
		int end = msg.indexOf(" ", start);
		if (end != -1) {	// �ش����ڿ���
			String to = msg.substring(start, end);
			String msg2 = msg.substring(end + 1);
			Object obj = hm.get(to); // to�� key���̵ȴ�.
			if (obj != null) {
				PrintWriter pw = (PrintWriter) obj; 
				pw.println(id + "���� ������ �ӼӸ��� �����̽��ϴ�. :" + msg2);
				pw.flush();
			}
		}
	}

	public void broadcast(String msg) {
		synchronized (hm) {
			Collection collection = hm.values();
			Iterator iter = collection.iterator();
			while (iter.hasNext()) {
				PrintWriter pw = (PrintWriter) iter.next();
				pw.println(msg);
				pw.flush();
			}
		}
	}
}