package com.project.uber.UberApp;

import com.project.uber.UberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
		String[] email= {"royav61597@opposir.com", "firigi5380@anypng.com"};
//		royav61597@opposir.com, firigi5380@anypng.com, imran@mail.com, sara@mail.com, nida@mail.com, fatima@mail.com, rahul@mail.com, armaan@mail.com, rizwan@mail.com, meera@mail.com
//		email.add(); email.add(); // email.add("imran@mail.com"); email.add("sara@mail.com"); email.add("nida@mail.com");
//		email.add("fatima@mail.com"); email.add("rahul@mail.com"); email.add("armaan@mail.com"); email.add("rizwan@mail.com"); email.add("meera@mail.com");
//		{"salmanisohail26@gmail.com", "sohelsalmani64@gmail.com","neman91618@aqqor.com"};
		emailSenderService.sendEmail(email,
				"This is the test mail for Bulk",
				"Hello, this is the body of the Bulk test mail.");
	}

}
