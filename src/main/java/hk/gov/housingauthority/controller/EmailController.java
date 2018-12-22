package hk.gov.housingauthority.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hk.gov.housingauthority.model.Datatable;
import hk.gov.housingauthority.model.Properties;
import hk.gov.housingauthority.model.Record;
import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyEmailSts;
import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.model.SrvyEmailType;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.SendEmailService;
import hk.gov.housingauthority.service.SrvyEmailHistService;
import hk.gov.housingauthority.service.SrvyEmailTmplService;
import hk.gov.housingauthority.service.SrvyPptService;
import hk.gov.housingauthority.service.SrvyRecService;
import hk.gov.housingauthority.service.UserService;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@ResponseBody
	@RequestMapping(value = "/survey/listEmailHistory", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listEmailHistory(@RequestParam Map<String, Object> param) {
		/* TODO list related History only */
		SrvyEmailHistService srvyEmailHistService = (SrvyEmailHistService) context.getBean("srvyEmailHistService");
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 1000 : Integer.parseInt((String) param.get("length"));
		ArrayList<SrvyEmailHist> srvyEmailHistList = srvyEmailHistService.getEmailHistList(this.getDatatableFilter(param), this.getDatatableSorting(param), start, length);
		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(srvyEmailHistService.listToJsonStr(srvyEmailHistList));
		if (srvyEmailHistList.size() > 0) {
			datatable.setRecordsTotal(Integer.toString(srvyEmailHistList.get(0).getTotRec()));
		} else {
			datatable.setRecordsTotal("0");
		}
		return datatable.toJsonStr(datatable);
	}

	@ResponseBody
	@RequestMapping(value = "/testEmailTmpl", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String testEmailTmpl(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			SendEmailService sendEmailService = (SendEmailService) context.getBean("sendEmailService");
			UserService userService = (UserService) context.getBean("userService");
			User user = userService.getUserById(CommonFunction.getSsnUserId());
			String emailSubj = (String) param.get("emailSubj");
			String emailCntn = (String) param.get("emailCntn");

			SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
			SrvyRec srvyRec = srvyRecService.getSrvyRecByID(Integer.parseInt((String) param.get("srvyRecId")));

			Properties sysProp = (Properties)context.getBean("sysProp");
			HashMap<String, String> sysPropHash = sysProp.getHashMap();
			
			SrvyEmailTmpl srvyEmailTmpl = new SrvyEmailTmpl();
			srvyEmailTmpl.setSrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
			
			emailSubj = sendEmailService.replaceWithUser(emailSubj, user);
			emailSubj = sendEmailService.replaceWithSrvyRec(emailSubj, srvyRec);
			emailSubj = sendEmailService.replaceWithItDummyData(emailSubj);
			emailSubj = sendEmailService.replaceWithUser(emailSubj, user);
			
			emailCntn = sendEmailService.replaceWithUser(emailCntn, user);
			emailCntn = sendEmailService.replaceWithSrvyRec(emailCntn, srvyRec);
			emailCntn = sendEmailService.replaceWithItDummyData(emailCntn);
			
			srvyEmailTmpl.setSrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
			srvyEmailTmpl.setEmailSubj(emailSubj);
			srvyEmailTmpl.setEmailCntn(emailCntn);

			sendEmailService.sendTestMail(srvyEmailTmpl);
			map.put("success", "true");
			map.put("message", "Test email has been sent to your email successfully.");
		} catch (MessagingException e) {
			System.out.println(e);
			map.put("success", "false");
			map.put("message", "Test email has been sent .");
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/mail/sendInvitationToSelected", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String sendInvitationToSelected(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		ArrayList<SrvyPpt> srvyPptList = srvyPptService.getSrvyPpt((String) param.get("srvyPptIds"));
		sendEmailManually(param, srvyPptList, Constant.EMAIL_TYPE_INVITATION);
		map.put("success", "true");
		map.put("message", "Invitation email has been sent successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/mail/sendReminderToSelected", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String sendReminderToSelected(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		ArrayList<SrvyPpt> srvyPptList = srvyPptService.getSrvyPpt((String) param.get("srvyPptIds"));
		for (SrvyPpt srvyPpt : srvyPptList) {
			if(srvyPpt.getSbmtSts()!= null && !"N".equals(srvyPpt.getSbmtSts())){
				srvyPptList.remove(srvyPpt);
			}
		}
		sendEmailManually(param, srvyPptList, Constant.EMAIL_TYPE_REMINDER);
		map.put("success", "true");
		map.put("message", "Reminder email has been sent successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/mail/sendReminderToAll", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String sendReminderToAll(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		ArrayList<SrvyPpt> srvyPptList = srvyPptService.getSrvyPptListBySrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
		for (SrvyPpt srvyPpt : srvyPptList) {
			if(srvyPpt.getSbmtSts()!= null && !"N".equals(srvyPpt.getSbmtSts())){ /* remove ppt who has submitted */
				srvyPptList.remove(srvyPpt);
			}
		}
		sendEmailManually(param, srvyPptList, Constant.EMAIL_TYPE_REMINDER);
		map.put("success", "true");
		map.put("message", "Reminder email has been sent successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/mail/sendInvitationToAll", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String sendInvitationToAll(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		ArrayList<SrvyPpt> srvyPptList = srvyPptService.getSrvyPptListBySrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
		sendEmailManually(param, srvyPptList, Constant.EMAIL_TYPE_INVITATION);
		map.put("success", "true");
		map.put("message", "Invitation email has been sent successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	/* private function */
	private void sendEmailManually(Map<String, Object> param, ArrayList<SrvyPpt> srvyPptList, String emailType){
		
		Properties sysProp = (Properties)context.getBean("sysProp");
		HashMap<String, String> sysPropHash = sysProp.getHashMap();
		
		SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
		
		SrvyEmailTmplService srvyEmailTmplService = (SrvyEmailTmplService) context.getBean("srvyEmailTmplService");
		SrvyEmailHistService srvyEmailHistService = (SrvyEmailHistService) context.getBean("srvyEmailHistService");
		SendEmailService sendEmailService = (SendEmailService) context.getBean("sendEmailService");
		SrvyEmailTmpl srvyEmailTmpl = srvyEmailTmplService.getEmailTmpl(Integer.parseInt((String) param.get("srvyRecId")),emailType);
		
		SrvyRec srvyRec = srvyRecService.getSrvyRecByID(Integer.parseInt((String) param.get("srvyRecId")));
				
		for (SrvyPpt srvyPpt : srvyPptList) {
			String subject = srvyEmailTmpl.getEmailSubj();
			String content = srvyEmailTmpl.getEmailCntn();
			subject = sendEmailService.replaceWithUser(subject, srvyPpt.getUser());
			subject = sendEmailService.replaceWithSrvyPpt(subject, srvyPpt,sysPropHash.get("URL_LOGIN"));
			subject = sendEmailService.replaceWithSrvyRec(subject, srvyRec);
			content = sendEmailService.replaceWithUser(content, srvyPpt.getUser());
			content = sendEmailService.replaceWithSrvyPpt(content, srvyPpt,sysPropHash.get("URL_LOGIN"));
			content = sendEmailService.replaceWithSrvyRec(content, srvyRec);
			
			SrvyEmailType srvyEmailType = new SrvyEmailType();
			srvyEmailType.setSrvyEmailTypeId(emailType);
			SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
			srvyEmailHist.setSrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
			srvyEmailHist.setSrvyPptId(srvyPpt.getSrvyPptId());
			srvyEmailHist.setSrvyEmailType(srvyEmailType);
			srvyEmailHist.setEmailTo(srvyPpt.getUser().getUserEmail());
			srvyEmailHist.setEmailSubj(subject);
			srvyEmailHist.setEmailCntn(content);
			srvyEmailHist.setEmailSendDate(new Timestamp(System.currentTimeMillis()));
			SrvyEmailSts srvyEmailSts = new SrvyEmailSts();
			srvyEmailSts.setSrvyEmailStsId(Constant.SEND_STATUS_WAITING_FOR_SEND);
			srvyEmailHist.setSrvyEmailSts(srvyEmailSts);
			srvyEmailHist.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			srvyEmailHist.setLastRecTxnTypeCode(Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
			srvyEmailHist.setLastRecTxnDate(new Timestamp(System.currentTimeMillis()));
			srvyEmailHistService.insertEmailHist(srvyEmailHist);			
		}
	}
	private String getDatatableSorting(Map<String, Object> param) {
		int i = 0;
		String sorting = "";
		try {
			do {
				sorting += getDatatablColName(Integer.parseInt((String) param.get("order[" + i + "][column]"))) + " "
						+ (String) param.get("order[" + i + "][dir]");
				if (param.get("order[" + ++i + "][dir]") == null) {
					break;
				} else {
					sorting += ",";
				}
			} while (true);
		} catch (Exception ex) {
		}
		return sorting;
	}

	private String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("EMAIL_SEND_DATE", "EMAIL_TYPE", "EMAIL_TO", "EMAIL_STS", "TRY_CNT");
		return colList.get(colNo);
	}

	private SrvyEmailHist getDatatableFilter(Map<String, Object> param) {
		int i = 0;
		SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
		try {
			do {
				try {
					if ("srvyRecId".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
						srvyEmailHist.setSrvyRecId(Integer.parseInt((String) param.get("filter[" + i + "][value]")));
					}
				} catch (Exception ex) {
				}
				try {
					if ("srvyEmailHist.emailSendDateFrom".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
						srvyEmailHist.setEmailSendDateFrom(new Timestamp(Record.getDatetimeFormat().parse((String) param.get("filter[" + i + "][value]") + " 00:00:00").getTime()));
					}
				} catch (Exception ex) {
				}
				try {
					if ("srvyEmailHist.emailSendDateTo".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
						srvyEmailHist.setEmailSendDateTo(new Timestamp(Record.getDatetimeFormat().parse((String) param.get("filter[" + i + "][value]") + " 23:59:59").getTime()));
					}
				} catch (Exception ex) {
				}
				if ("srvyEmailHist.srvyEmailType.srvyEmailTypeId".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyEmailHist.getSrvyEmailType().setSrvyEmailTypeId((String) param.get("filter[" + i + "][value]"));
				}
				if ("srvyEmailHist.emailTo".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyEmailHist.setEmailTo((String) param.get("filter[" + i + "][value]"));
				}
				if ("srvyEmailHist.srvyEmailSts.srvyEmailStsId".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyEmailHist.getSrvyEmailSts().setSrvyEmailStsId((String) param.get("filter[" + i + "][value]"));
				}
				if (param.get("filter[" + ++i + "][name]") == null) {
					break;
				}
			} while (true);
		} catch (Exception ex) {
		}
		return srvyEmailHist;
	}
}