package ex;

import java.math.BigDecimal;

public class E01_Enum {
/*
    구의 면적 = 4πr²
 */
	
	// 행성 객체 선언
	public enum Planet {
	    수성(2439),
	    금성(6052),
	    지구(6371),
	    화성(3390),
	    목성(69911),
	    토성(58232),
	    천왕성(25362),
	    해왕성(24622);
		
		// 반지름 변수 선언
	    private int radius;
	    
	    // 값을 반환하는 메서드
	    Planet(int radius) {
	    	this.radius = radius;
	    }
	    
	    public int getRadius() {
	    	return this.radius;
	    }
	    
	    // 구의 면적 = 4πr²
	    public double getSize() {
	    	return 4 * Math.PI * radius * radius ;
	    }
	    
	    // 지수 표현 제거
	    public String getStringSize() {
	    	return new BigDecimal(Math.round(4 * Math.PI * radius * radius)).toString();
	    }
	    
	}
	
	public static void main(String[] args) {
		
		System.out.println("──────────────────────────────────────────");
		System.out.println("행성의 면적 > ");
		System.out.println("──────────────────────────────────────────");
		
		for(Planet planet : Planet.values()) {
			System.out.println(planet.name() + "의 면적 : " + planet.getSize() + "km²");
		}
		
		System.out.println();
		System.out.println("──────────────────────────────────────────");
		System.out.println("지수 표현 제거 > ");
		System.out.println("──────────────────────────────────────────");
		
		for(Planet planet : Planet.values()) {
			System.out.println(planet.name() + "의 면적 : " + planet.getStringSize() + "km²");
		}
		System.out.println("──────────────────────────────────────────");
	}

}
