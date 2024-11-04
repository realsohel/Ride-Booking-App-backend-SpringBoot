package com.project.uber.UberApp;

import com.project.uber.UberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAppApplicationTests {
	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"neman91618@aqqor.com",
				"This is the test mail",
				"Hello, this is the body of the test mail."
		);
	}

	@Test
	void sendBulkEmail(){
		String email[]={"salmanisohail26@gmail.com", "sohelsalmani64@gmail.com","neman91618@aqqor.com"};
		emailSenderService.sendEmail(email,
				"This is the test mail for Bulk",
				"Hello, this is the body of the Bulk test mail.");
	}

}
