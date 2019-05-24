package hu.elte.szgy.tudor;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TudorApplication {
    //private static Logger log = LoggerFactory.getLogger(TudorApplication.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TudorApplication.class, args);
	}

}
