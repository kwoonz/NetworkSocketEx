package MultiChact_swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame{
	//�ʵ� ����
	private JPanel contentPane;
	private JTextField textField;
	private JButton start;
	JTextArea textArea;
	
	private ServerSocket serverSocket;
	private Socket socket;
	private int port;
	
	//�����尣�� ������ ������ Vector ��ü ����
	private Vector vector = new Vector();  //���� �迭 	
	
	//������
	public Server(){
		init();   //����� ���� �޼ҵ� ȣ��
	}
	public static void main(String[] args) {
		Server frame = new Server();
		frame.setVisible(true);
	}
	public void init(){
		setTitle("ä��-����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 150, 300, 400);
		
		contentPane = new JPanel();
		contentPane.setLayout(null); //����� ���� ��ġ
		setContentPane(contentPane);
		
		JScrollPane js = new JScrollPane();
		js.setBounds(0, 0, 264, 254);
		contentPane.add(js);
		
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setRows(5);
		js.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(98, 264, 154, 37);
		textField.setColumns(10);
		contentPane.add(textField);
		
		JLabel portLable = new JLabel("Port Number");
		portLable.setBounds(12, 264, 98, 37);
		contentPane.add(portLable);
		
		start = new JButton("���� ����");
		
		//�̺�Ʈ ���� �� �ڵ鷯 ó���� �ѹ��� �ذ�
		start.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("") || 
						textField.getText().length() == 0){
					JOptionPane.showMessageDialog(null, 
							"��Ʈ ��ȣ�� �Է��ϼ���", 
							"��� �޽���", 
							JOptionPane.ERROR_MESSAGE);
					textField.requestFocus();
					return;
				}else{
					try{
						port = Integer.parseInt(textField.getText());
						server_start(port); //����� ���� �޼ҵ� ȣ��
					}catch(Exception ex){
						System.out.println(ex);
					}
				}				
			}
		});	
		start.setBounds(0, 325, 264, 37);
		contentPane.add(start);
		textArea.setEditable(false);
	}//end init()
	
	public void server_start(int port)throws IOException{
		serverSocket = new ServerSocket(port);
		start.setText("����������...");
		start.setBackground(Color.GREEN);
		start.setEnabled(false);  //"���� ����" ��ư ��Ȱ��ȭ
		textField.setEditable(false);
		
		if(serverSocket != null){
			Connection();   //����� ���� �޼ҵ� ȣ��
		}
	}//end server_start()
	
	public void Connection(){
		Thread th = new Thread(new Runnable() {			
			@Override
			public void run() {
				while(true){   //���� Loop
					try{
						textArea.append("����� ���� �����...\n");
						socket = serverSocket.accept(); //"���� " ���
						textArea.append("����� ����!!\n");
						
						//������ ��� �����ϸ鼭 ������ �ۼ����ϱ� ���ؼ� ������ �ڵ鷯 ��ü����
						ThreadHandler user = new ThreadHandler(socket,vector);
						vector.add(user);
						//�ش� ���Ϳ� ����� ��ü �߰�
						user.start();
					}catch(IOException e){
						textArea.append("accept(���� )���� �߻�!!\n");
					}
				}				
			}
		});
		th.start();
	}//end Connection()
	
	class ThreadHandler extends Thread{
		private Socket user_socket;
		private Vector user_vactor;
		private String Nickname = "";
		
		private InputStream is;       //�⺻ ���� �Է� ��Ʈ��
		private OutputStream os;      //�⺻ ���� ��� ��Ʈ��
		private DataInputStream dis;  //���� ��Ʈ��
		private DataOutputStream dos; //���� ��Ʈ��		
		
		//������
		public ThreadHandler(Socket socket, Vector vector){
			user_socket = socket;
			user_vactor = vector;
			
			user_network(); //����� ���� �޼ҵ� ȣ��
		}
		//=================================================
		/*
		 * Socket ������ ���
		 * => Ŭ���̾�Ʈ�� ���� ��û�� �ϰ� ������ ���� ������ �ߴٸ�,
		 *    ������ Socket ��ü�κ��� ���� �Է½�Ʈ��(InputStream)��
		 *    ��½�Ʈ��(OutputStream)�� ���� �� �ִ�.
		 */
		public void user_network(){
			try{				
				/*
				 * ���� ��Ʈ���� ��ü������ ������� ������ �� ���� ������
				 * InputStream, OutputStream � �����ؼ� 
				 * ������� �����Ѵ�.
				 */
				is = user_socket.getInputStream(); //�Է� ��Ʈ�� ��ü ������
				dis = new DataInputStream(is); //���� ��Ʈ�� ��ü ����
				
				os = user_socket.getOutputStream(); //��� ��Ʈ�� ��ü ������
				dos = new DataOutputStream(os); //���� ��Ʈ�� ��ü ����
				
				Nickname = dis.readUTF();
				textArea.append("������ ID " + Nickname + "\n");
				//����� ���� �޼ҵ� ȣ��
				send_Message("���� ���� �Ǿ����ϴ�."); 
			}catch(Exception e){
				textArea.append("��Ʈ�� ���� ���� �߻�!!\n");
			}
		}//end User_network()
		public void send_Message(String str){
			try{
				dos.writeUTF(str);
			}catch(IOException e){
				textArea.append("�޽��� �۽� ���� �߻�\n");
			}
		}
		public void broad_cast(String str){
			for(int i=0; i<user_vactor.size(); i++){
				ThreadHandler imsi = (ThreadHandler)user_vactor.elementAt(i);
				imsi.send_Message(Nickname + ":" + str);				
			}
		}
		public void InMessage(String str){
			textArea.append("����ڷκ��� ���� �޽���: " + str+"\n");
			broad_cast(str);
		}
		@Override	//�������� Override ��Ų��!!
		public void run(){
			while(true){
				try{
					String msg = dis.readUTF();
					InMessage(msg);
				}catch(IOException e){
					try{
						dos.close();
						dis.close();
						user_socket.close();
						user_vactor.removeElement(this);
						textArea.append(user_vactor.size()+":���� ���Ϳ� ����� �����\n");
						textArea.append("����� ���� ������\n");
						break;
					}catch(Exception ex){
						System.out.println(ex);
					}
				}
			}
		}		
	}	
}