package hello

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan
class Application {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}