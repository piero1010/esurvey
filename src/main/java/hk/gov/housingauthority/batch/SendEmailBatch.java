package hk.gov.housingauthority.batch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hk.gov.housingauthority.service.SendEmailService;
import hk.gov.housingauthority.service.SysCnfgService;
import hk.gov.housingauthority.util.Constant;

public class SendEmailBatch extends QuartzJobBean{
	private static final Logger logger = LoggerFactory.getLogger(SendEmailBatch.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Start SendEmailBatch");
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		SendEmailService sendEmailService = (SendEmailService) context.getBean("sendEmailService");
		SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");
		try {
			
			sendEmailService.sendMail(Integer.parseInt(sysCnfgService.getVaueByCode(Constant.SYS_CNFG_SMTP_MAX_EMAIL_SEND)));
		} catch (Exception e) {
			logger.info(e.toString());
		}
		logger.info("End SendEmailBatch");
	}
}
