package hk.gov.housingauthority.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import hk.gov.housingauthority.service.SendEmailServiceImpl;

public class SystemConfigurationDaoTester {	

	@Test
	public void tester() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		SendEmailServiceImpl sendEmailServiceImpl = (SendEmailServiceImpl) context.getBean("sendEmailService");

		//sendEmailServiceImpl.sendMail();

		
		
	}
}
