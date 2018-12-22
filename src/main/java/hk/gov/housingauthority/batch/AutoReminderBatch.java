package hk.gov.housingauthority.batch;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hk.gov.housingauthority.dao.SrvyEmailHistDao;
import hk.gov.housingauthority.dao.SrvyEmailTmplDao;
import hk.gov.housingauthority.dao.SrvyPptDao;
import hk.gov.housingauthority.dao.SrvyRecDao;
import hk.gov.housingauthority.dao.UserDao;
import hk.gov.housingauthority.model.Properties;
import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyEmailSts;
import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.model.SrvyEmailType;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.SendEmailService;
import hk.gov.housingauthority.util.Constant;

public class AutoReminderBatch extends QuartzJobBean {
	private static final Logger logger = Logger.getLogger(AutoReminderBatch.class);
	
    @Override
    protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException {
    	logger.info("Start AutoReminderBatch");
		try {	
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			UserDao userDao = (UserDao) context.getBean("userDao");
			SrvyRecDao srvyRecDao = (SrvyRecDao) context.getBean("srvyRecDao");
			SrvyPptDao srvyPptDao = (SrvyPptDao) context.getBean("srvyPptDao");
			SrvyEmailTmplDao srvyEmailTmplDao = (SrvyEmailTmplDao) context.getBean("srvyEmailTmplDao");
			SrvyEmailHistDao srvyEmailHistDao = (SrvyEmailHistDao) context.getBean("srvyEmailHistDao");
			SendEmailService sendEmailService = (SendEmailService) context.getBean("sendEmailService");
			
			Properties sysProp = (Properties)context.getBean("sysProp");
			HashMap<String, String> sysPropHash = sysProp.getHashMap();
			
			SrvyPpt srvyPpt;
			int currSrvyRecId = 0;
			User user;
			SrvyRec srvyRec = new SrvyRec();
			SrvyEmailTmpl srvyEmailTmpl =new SrvyEmailTmpl();
			SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
			
			String mySubject;
			String myContent;
			
			ArrayList<SrvyPpt> srvyPptList = srvyPptDao.getRmdrSrvyPpt();
			for (int i = 0; i < srvyPptList.size(); i++) {
				srvyPpt = srvyPptList.get(i);

				user = userDao.getUserById(srvyPpt.getUser().getUserId());

				if (user.getUserEmail() != null) {
					if (currSrvyRecId != srvyPpt.getSrvyRecId()) {
						currSrvyRecId = srvyPpt.getSrvyRecId();
						srvyRec = srvyRecDao.getSrvyRecByID(currSrvyRecId);
						srvyEmailTmpl = srvyEmailTmplDao.getEmailTmpl(currSrvyRecId, Constant.EMAIL_TYPE_REMINDER);
					}

					// add email history
					mySubject = srvyEmailTmpl.getEmailSubj();
					myContent = srvyEmailTmpl.getEmailCntn();

					mySubject = sendEmailService.replaceWithUser(mySubject, user);
					mySubject = sendEmailService.replaceWithSrvyPpt(mySubject, srvyPpt, sysPropHash.get("URL_LOGIN"));
					mySubject = sendEmailService.replaceWithSrvyRec(mySubject, srvyRec);

					myContent = sendEmailService.replaceWithUser(myContent, user);
					myContent = sendEmailService.replaceWithSrvyPpt(myContent, srvyPpt, sysPropHash.get("URL_LOGIN"));
					myContent = sendEmailService.replaceWithSrvyRec(myContent, srvyRec);					
					
					srvyEmailHist = new SrvyEmailHist();
					srvyEmailHist.setSrvyRecId(currSrvyRecId);

					SrvyEmailType srvyEmailType = new SrvyEmailType();
					srvyEmailType.setSrvyEmailTypeId(Constant.EMAIL_TYPE_REMINDER);
					srvyEmailHist.setSrvyEmailType(srvyEmailType);

					srvyEmailHist.setEmailTo(user.getUserEmail());
					srvyEmailHist.setEmailSubj(mySubject);
					srvyEmailHist.setEmailCntn(myContent);
					srvyEmailHist.setEmailSendDate(new Timestamp(System.currentTimeMillis()));

					SrvyEmailSts srvyEmailSts = new SrvyEmailSts();
					srvyEmailSts.setSrvyEmailStsId(Constant.SEND_STATUS_WAITING_FOR_SEND); 
					srvyEmailHist.setSrvyEmailSts(srvyEmailSts);
					
					srvyEmailHist.setLastRecTxnUserId("AutoReminderBatch");
					srvyEmailHist.setLastRecTxnTypeCode(Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
					srvyEmailHist.setLastRecTxnDate(new Timestamp(System.currentTimeMillis()));
					srvyEmailHistDao.insertEmailHist(srvyEmailHist);

					logger.info("Generated Reminder to " + user.getUserId());
				}
			}			
		} catch (Exception e) {
			logger.info(e.toString());
		}
		logger.info("End AutoReminderBatch");
    }
}

