package hanghae99.JJimGGongMall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class JJimGGongMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(JJimGGongMallApplication.class, args);
	}

}
