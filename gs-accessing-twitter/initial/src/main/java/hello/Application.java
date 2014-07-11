package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	/*
	 * SPRING BOOTSTRAP MAIN
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

//POST request for "https://api.twitter.com/oauth/request_token" resulted in 406 (Not Acceptable); invoking error handler