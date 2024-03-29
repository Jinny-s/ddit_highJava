package kr.or.ddit.basic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class T07_FileWriterTest {
/**
 * FileWriter(문자기반 스트림) 예제
 */
	public static void main(String[] args) {
		// 사용자가 입력한 내용을 그대로 파일로 저장하기
		
		// 콘솔(표준 입력장치)과 연결된 입력용 문자 스트림 생성
		// InputStreamReader => 바이트 기반 스트림을 문자기반 스트림으로
		//						변환해 주는 보조 스트림
		InputStreamReader isr = new InputStreamReader(System.in);
		
		FileWriter fw = null; // 파일 출력용 문자 기반 스트림
		
		try {
			// 파일 출력용 문자 스트림 객체 생성
			fw = new FileWriter("d:/D_Other/testChar.txt");
			
			int c;
			
			System.out.println("아무거나 입력하세요.");
			
			// 콘솔에서 입력할 때 입력의 끝 표시는 Ctrl + z 키를 누르면 된다.
			while((c=isr.read()) != -1) {
				//System.out.println(c);
				fw.write(c); // 콘솔에서 입력 받은 값을 파일에 출력하기
			}
			System.out.println("작업 끝...");
			
			isr.close();
			fw.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
