package site.cleanfree.be_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class CleanfreeMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanfreeMainApplication.class, args);
	}

}
