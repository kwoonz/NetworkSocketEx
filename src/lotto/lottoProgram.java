package lotto;

import java.util.Arrays;

public class lottoProgram {
	

	
	

	public static void main(String[] args) {
		
			int[] lottoArr = new int[6];	// �ζ� ��ȣ 6���� ��� �迭
			int bonusNum = 0;	// ���ʽ� ��ȣ
			
			for(int i=0; i<lottoArr.length; i++) {
				int lottoNum = (int)(Math.random()*45) + 1; // 1~45 ��ȣ ��������
				lottoArr[i] = lottoNum;
				
				/*
				 * �ζ� ��ȣ �ߺ� üũ...
				 * ������� �迭�� ���� ������ ���������� ���ڿ� ������ ���ڰ� �ִ��� üũ
				 */
				for(int j=0; j<i; j++) { 
					if(lottoArr[i] == lottoArr[j]) {
						i --;
						break;
					}
				}
			}
			
			/*
			 * ���ʽ� ��ȣ�� �ζ� ��ȣ�� �ߺ����� üũ...
			 */
			boolean flag = true;
			while(flag) {
				bonusNum = (int)(Math.random()*45) + 1;
				for(int i=0; i<lottoArr.length; i++) {
					if(lottoArr[i] == bonusNum) {	// ���ʽ���ȣ�� �ζǹ�ȣ�� �ߺ��ȴٸ� -> ���ο� ���� �����ϰ� �ٽ� üũ.
						flag = true;
						break;
					}
					flag = false;
				}
			}
			Arrays.sort(lottoArr);
			
			System.out.println("������ �ζǹ�ȣ�� ������ �����ϴ� ...");
			for(int i=0; i<lottoArr.length; i++) {
				System.out.print(lottoArr[i] + " ");
			}
			System.out.println("���ʽ���ȣ�� : " + bonusNum);
		}
		
	}


