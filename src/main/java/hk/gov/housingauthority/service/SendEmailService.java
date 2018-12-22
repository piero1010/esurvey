package hk.gov.housingauthority.service;

import javax.mail.MessagingException;

import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.User;

public interface SendEmailService {
	public int sendMail(int maxEmailSend) throws MessagingException; 

	public int sendTestMail(SrvyEmailTmpl srvyEmailTmpl) throws MessagingException;

	public String replaceWithUser(String inString, User user);

	public String replaceWithSrvyRec(String inString, SrvyRec srvyRec);
	
	public String replaceWithItDummyData(String inString);

	public String replaceWithSrvyPpt(String mySubject, SrvyPpt srvyPpt, String url);
}
