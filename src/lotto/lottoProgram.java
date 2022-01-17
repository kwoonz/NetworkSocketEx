package lotto;

import java.util.Arrays;

public class lottoProgram {
	

	
	

	public static void main(String[] args) {
		
			int[] lottoArr = new int[6];	// 로또 번호 6개가 담길 배열
			int bonusNum = 0;	// 보너스 번호
			
			for(int i=0; i<lottoArr.length; i++) {
				int lottoNum = (int)(Math.random()*45) + 1; // 1~45 번호 랜덤생성
				lottoArr[i] = lottoNum;
				
				/*
				 * 로또 번호 중복 체크...
				 * 현재까지 배열에 담은 숫자중 랜덤생성한 숫자와 동일한 숫자가 있는지 체크
				 */
				for(int j=0; j<i; j++) { 
					if(lottoArr[i] == lottoArr[j]) {
						i --;
						break;
					}
				}
			}
			
			/*
			 * 보너스 번호도 로또 번호와 중복인지 체크...
			 */
			boolean flag = true;
			while(flag) {
				bonusNum = (int)(Math.random()*45) + 1;
				for(int i=0; i<lottoArr.length; i++) {
					if(lottoArr[i] == bonusNum) {	// 보너스번호와 로또번호가 중복된다면 -> 새로운 숫자 생성하고 다시 체크.
						flag = true;
						break;
					}
					flag = false;
				}
			}
			Arrays.sort(lottoArr);
			
			System.out.println("생성된 로또번호는 다음과 같습니다 ...");
			for(int i=0; i<lottoArr.length; i++) {
				System.out.print(lottoArr[i] + " ");
			}
			System.out.println("보너스번호는 : " + bonusNum);
		}
		
	}


