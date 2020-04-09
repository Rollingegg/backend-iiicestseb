package group.iiicestseb.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jh
 */
@EnableScheduling
//@MapperScan("group.iiicestseb.backend.mapper")
@SpringBootApplication
public class OasisApplication {

	public static void main(String[] args) {
		SpringApplication.run(OasisApplication.class, args);
	}

}
