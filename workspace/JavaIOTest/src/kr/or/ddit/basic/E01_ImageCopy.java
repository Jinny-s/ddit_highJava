package kr.or.ddit.basic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class E01_ImageCopy {

	public static void main(String[] args) {
		/*
		// 버퍼 미사용
		try {
			FileInputStream fis = new FileInputStream("d:/D_Other/Tulips.jpg");
			FileOutputStream fos = new FileOutputStream("d:/D_Other/복사본_Tulips.jpg");
			
			int c = 0;
			while((c = fis.read()) != -1) {
				fos.write(c);
			}
			System.out.println("이미지 파일 복사 완료!");
			
			fis.close();
			fos.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		// 버퍼 사용
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			File jpg = new File("d:/D_Other/Tulips.jpg");
			File copyJpg = new File("d:/D_Other/복사본_Tulips.jpg");
			
			fos = new FileOutputStream("d:/D_Other/Tulips.jpg");
			bos = new BufferedOutputStream(fos, 1024);
			
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
