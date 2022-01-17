package MultiChact_swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainView extends JFrame{
	private JPanel contentPane;
	private JTextField textField;
	
	private String id;
	private String ip;
	private int port;
	
	JButton sendBtn;   //"����" ��ư
	JTextArea textArea;
	
	private Socket socket;
	private InputStream is;       //�⺻ ���� �Է� ��Ʈ��
	private OutputStream os;      //�⺻ ���� ��½�Ʈ��
	private DataInputStream dis;  //���� ��Ʈ��
	private DataOutputStream dos; //���� ��Ʈ��
	
	//������
	public MainView(String id, String ip, int port){
		this.id = id;
		this.ip = ip;
		this.port = port;
		
		init();		//����� ���� �޼ҵ� ȣ��
		start();	//����� ���� �޼ҵ� ȣ��
		
		textArea.append("�Ű������� �Ѿ�� ��: "+id+" "+ip+" "+port+"\n");
		network();  //����� ���� �޼ҵ� ȣ��		
	}
	public void init(){
		setTitle("ä��-Ŭ���̾�Ʈ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 288, 390);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);  //����� ���� ��ġ
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 272, 300);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(0, 312, 155, 42);
		textField.setColumns(10);
		contentPane.add(textField);
		
		sendBtn = new JButton("����");
		sendBtn.setBounds(161, 312, 111, 42);
		contentPane.add(sendBtn);
		
		textArea.setEnabled(false); //textArea ��ü ��Ȱ��ȭ
		setVisible(true);
	}
	public void start(){
		sendBtn.addActionListener(new Myaction());
		//�̺�Ʈ ���� �� �ڵ鷯 ó���� �ѹ��� �ذ�
		textField.addKeyListener(new KeyAdapter() {
			@Override
			//textField ��ü���� �޽��� �Է� �� "����" ��ư�� ������ �ʰ�
			//"Enter" Ű�� ���� ��� �ذ� ���
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					//����� ���� �޼ҵ� ȣ��
					send_Message(textField.getText());
					textField.setText("");
					textField.requestFocus();
				}
			}
		});
	}//end start()
	public void send_Message(String str){
		try{
			dos.writeUTF(str);
		}catch(IOException e){
			textArea.append("�޽��� �۽� ����");
		}
	}
	class Myaction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == sendBtn){
				send_Message(textField.getText());
				textField.setText("");
				textField.requestFocus();
			}			
		}		
	}
	public void network(){
		//������ �����ϱ� ���ؼ� �غ� �۾�
		try{
			socket = new Socket(ip, port);
			if(socket != null){
				Connection(); //����� ���� �޼ҵ� ȣ��
			}
		}catch(UnknownHostException e){
			
		}catch(IOException e){
			textArea.append("���� ���� ����!!\n");
		}
	}//end network()
	public void Connection(){
		try{
			//���� IO ��Ʈ�� ��ü �����ϴ� ���
			is = socket.getInputStream(); //�⺻ ���� ��Ʈ�� ��ü ������
			dis = new DataInputStream(is); //���� ��Ʈ�� ��ü ����
			
			os = socket.getOutputStream(); //�⺻ ���� ��Ʈ�� ��ü ������
			dos = new DataOutputStream(os); //���� ��Ʈ�� ��ü ����
			
		}catch(IOException e){
			textArea.append("��Ʈ�� ���� ����");
		}
		send_Message(id);
		
		Thread th = new Thread(new Runnable() {			
			@Override
			public void run() {
				while(true){ //���� Loop
					try{
						//������ ���� �޽��� ���� 
						String msg = dis.readUTF(); 
						textArea.append(msg + "\n");
					}catch(IOException e){
						textArea.append("�޽��� ���� ����!!\n");
						try{
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							break;
						}catch(IOException ex){
							System.out.println(ex);
						}						
					}					
				}
			}
		});
		th.start();
	}	
}