package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //스프링부트 어노태이션의 하위에 있는 어노태이션을 모두 스캔하여 빈에등록
public class JpashopApplication {


	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

}
