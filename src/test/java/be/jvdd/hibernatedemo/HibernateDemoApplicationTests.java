package be.jvdd.hibernatedemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = HibernateDemoApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestDocs
class HibernateDemoApplicationTests {

	@Test
	void contextLoads() {
	}



}
