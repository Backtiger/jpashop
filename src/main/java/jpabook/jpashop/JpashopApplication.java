package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //스프링부트 어노태이션의 하위에 있는 어노태이션을 모두 스캔하여 빈에등록
public class JpashopApplication {


	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module()
	{
		Hibernate5Module hibernate5Module = new Hibernate5Module();
	//	hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,true);
		return hibernate5Module;
	}
}
