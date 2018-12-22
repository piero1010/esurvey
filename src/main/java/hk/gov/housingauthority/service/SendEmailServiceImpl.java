package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hk.gov.housingauthority.batch.SendEmailBatch;
import hk.gov.housingauthority.dao.SrvyAdtnInfoDao;
import hk.gov.housingauthority.dao.SrvyEmailHistDao;
import hk.gov.housingauthority.dao.SrvyPptDao;
import hk.gov.housingauthority.dao.SysCnfgDao;
import hk.gov.housingauthority.model.SrvyAdtnInfo;
import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyEmailSts;
import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.SysCnfg;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

public class SendEmailServiceImpl implements SendEmailService {
	private static final Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);
	
	private Properties props;
	private Session session;
	private MimeMessage message;
	private SysCnfgDao sysCnfgDao;
	private SrvyAdtnInfoDao srvyAdtnInfoDao;
	private SrvyEmailHistDao srvyEmailHistDao;
	private SrvyPptDao srvyPptDao;
	private Transport transport;

	private void setConnection() throws MessagingException {
		HashMap<String, SysCnfg> sysCnfgHashMap = sysCnfgDao.getHashMapByGroup("SMTP");
		props = System.getProperties();
		props.put("mail.smtp.host", sysCnfgHashMap.get(Constant.SYS_CNFG_SMTP_HSTNM).getSysCnfgVal());
		props.put("mail.smtp.auth", "true");
		session = Session.getDefaultInstance(props, null);
		message = new MimeMessage(session);
		//message.setHeader("Content-Transfer-Encoding", "utf-8");
		message.setHeader("Content-Type", "text/html");
		Address from_Address = new InternetAddress(sysCnfgHashMap.get(Constant.SYS_CNFG_SMTP_EMAIL).getSysCnfgVal());
		message.setFrom(from_Address);
		transport = session.getTransport("smtp");
		transport.connect("10.1.0.71", "", "");
	}

	public void setSysCnfgDao(SysCnfgDao sysCnfgDao) throws MessagingException {
		this.sysCnfgDao = sysCnfgDao;
	}

	@Override
	public int sendMail(int maxEmailSend) throws MessagingException {
		logger.info("Start sendMail with max email send: "+maxEmailSend);
		
		this.setConnection();
		ArrayList<SrvyEmailHist> srvyEmailHistList = srvyEmailHistDao.getPendingEmailHistList(maxEmailSend);

		try {
			logger.info("Total Email buffered: " + srvyEmailHistList.size());
			for (SrvyEmailHist srvyEmailHist : srvyEmailHistList) {
				try {
					Address[] toAddress = InternetAddress.parse(srvyEmailHist.getEmailTo());
					logger.info("Sending email to: "+ toAddress.toString());
					message.setRecipients(Message.RecipientType.TO, toAddress);
					message.setSubject(srvyEmailHist.getEmailSubj());
					message.setContent(srvyEmailHist.getEmailCntn().replaceAll("<br>", "").replaceAll("</div><div>", "<br>").replaceAll("<div>", "").replaceAll("</div>", ""), "text/html; charset=utf-8");
					message.saveChanges();
					transport.sendMessage(message, message.getAllRecipients());
					SrvyEmailSts srvyEmailSts = new SrvyEmailSts();
					srvyEmailSts.setSrvyEmailStsId(Constant.SEND_STATUS_SUCCESS);
					srvyEmailHist.setSrvyEmailSts(srvyEmailSts);
					srvyEmailHist.setLastRecTxnUserId("AdminBatch");
					srvyEmailHist.setTryCnt(srvyEmailHist.getTryCnt() + 1);
					srvyEmailHistDao.updateEmailHist(srvyEmailHist);
					try{
						srvyPptDao.updateSrvyPptEmailTime(srvyEmailHist.getSrvyPptId(),srvyEmailHist.getSrvyEmailType().getSrvyEmailTypeId());
					}catch(Exception ex){
						logger.info(ex.toString());
					}
				} catch (Exception ex) {
					System.out.println(ex);
					srvyEmailHist.setLastRecTxnUserId("AdminBatch");
					int tryCnt = srvyEmailHist.getTryCnt() + 1;
					srvyEmailHist.setTryCnt(tryCnt);
					if (tryCnt >= 3) {/*
										 * if re-try more than three times, set
										 * email status to FAIL
										 */
						SrvyEmailSts srvyEmailSts = new SrvyEmailSts();
						srvyEmailSts.setSrvyEmailStsId(Constant.SEND_STATUS_FAIL);
						srvyEmailHist.setSrvyEmailSts(srvyEmailSts);
					}
					srvyEmailHistDao.updateEmailHist(srvyEmailHist);
					logger.info("Send Email Fail with Mail History ID: "+srvyEmailHist.getSrvyEmailHistId()+
								"Ppt ID: "+srvyEmailHist.getSrvyPptId() +
								"Total Fail Time: "+tryCnt+
								" "+ex.toString()
								);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				transport.close();
			} catch (Exception ex) {
			}
		}
		logger.info("End sendMail");
		return srvyEmailHistList.size();
	}

	@Override
	public int sendTestMail(SrvyEmailTmpl srvyEmailTmpl) throws MessagingException {
		logger.info("Start sendTestMail");
		this.setConnection();
		try {
			Address[] toAddress = InternetAddress.parse(CommonFunction.getSsnUserEmail());
			logger.info("Sending email to: "+ toAddress.toString());
			message.setRecipients(Message.RecipientType.TO, toAddress);
			message.setSubject(srvyEmailTmpl.getEmailSubj());
			message.setContent(srvyEmailTmpl.getEmailCntn().replaceAll("<br>", "").replaceAll("</div><div>", "<br>").replaceAll("<div>", "").replaceAll("</div>", ""), "text/html; charset=utf-8");	
			message.saveChanges();
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception ex) {
			logger.info(ex.toString());
		} finally {
			try {
				transport.close();
			} catch (Exception ex) {
			}
		}
		logger.info("End sendTestMail");
		return 1;
	}

	public String replaceWithUser(String inString, User user) {
		try {
			inString = inString.replaceAll("#PARTICIPANT_DESIGNATION#", user.getUserDsgn());
		} catch (Exception ex) {
		}
		try {
			inString = inString.replaceAll("#PARTICIPANT_NAME#", user.getUserName());
		} catch (Exception ex) {
		}
		try {
			inString = inString.replaceAll("#PARTICIPANT_DIVISION#", user.getUserDivCode());
		} catch (Exception ex) {
		}
		return inString;
	}

	public String replaceWithSrvyRec(String inString, SrvyRec srvyRec) {
		ArrayList<SrvyAdtnInfo> srvyAdtnInfoList = srvyAdtnInfoDao.getSrvyAdtnInfoList(srvyRec.getSrvyRecId());
		if (srvyAdtnInfoList != null && srvyAdtnInfoList.size() > 0) {
			for (SrvyAdtnInfo srvyAdtnInfo : srvyAdtnInfoList) {
				try {
					inString = inString.replaceAll("#" + srvyAdtnInfo.getAdtnInfoName() + "#", srvyAdtnInfo.getAdtnInfoDesc());
				} catch (Exception ex) {
				}
			}
		}		
		try {
			inString = inString.replaceAll("#SURVEY_TITLE#", srvyRec.getSrvyTtl());
		} catch (Exception ex) {
		}
		return inString;
	}

	public SrvyAdtnInfoDao getSrvyAdtnInfoDao() {
		return srvyAdtnInfoDao;
	}

	public void setSrvyAdtnInfoDao(SrvyAdtnInfoDao srvyAdtnInfoDao) {
		this.srvyAdtnInfoDao = srvyAdtnInfoDao;
	}

	public SrvyEmailHistDao getSrvyEmailHistDao() {
		return srvyEmailHistDao;
	}

	public void setSrvyEmailHistDao(SrvyEmailHistDao srvyEmailHistDao) {
		this.srvyEmailHistDao = srvyEmailHistDao;
	}

	public SysCnfgDao getSysCnfgDao() {
		return sysCnfgDao;
	}

	@Override
	public String replaceWithItDummyData(String inString) {
		try {
			inString = inString.replaceAll("#IT_SERVICE_NAME#", "e-Survey System");
		} catch (Exception ex) {
		}
		try {
			inString = inString.replaceAll("#IT_SERVICE_CODE#", "ESY");
		} catch (Exception ex) {
		}
		try {
			inString = inString.replaceAll("#SURVEY_URL#", "[SURVEY URL HERE]" );
		} catch (Exception ex) {
		}
		try {
			inString = inString.replaceAll("#NAME_OF_CONTRACTOR#", "IT Team 13");
		} catch (Exception ex) {
		}
		return inString;
	}

	@Override
	public String replaceWithSrvyPpt(String inString, SrvyPpt srvyPpt, String url) {
		try {
			inString = inString.replaceAll("#SURVEY_URL#", url + "/survey/response/" + srvyPpt.getSrvyPptId());
		} catch (Exception ex) {
		}
		return inString;
	}

	public SrvyPptDao getSrvyPptDao() {
		return srvyPptDao;
	}

	public void setSrvyPptDao(SrvyPptDao srvyPptDao) {
		this.srvyPptDao = srvyPptDao;
	}
}
