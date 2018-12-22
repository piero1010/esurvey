package hk.gov.housingauthority.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hk.gov.housingauthority.editor.SqlTimestampPropertyEditor;
import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.service.SrvyEmailTmplService;
import hk.gov.housingauthority.util.CommonFunction;

@Controller
public class SrvyEmailTmplController extends ViewController {

	private static final Logger logger = LoggerFactory.getLogger(SrvyEmailTmplController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true, 10));
		binder.registerCustomEditor(Timestamp.class, new SqlTimestampPropertyEditor());
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/updateEmailTmpl", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String updateEmailTmpl(@RequestParam Map<String, Object> param) {
		SrvyEmailTmplService srvyEmailTmplService = (SrvyEmailTmplService) context.getBean("srvyEmailTmplService");
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyEmailTmpl srvyEmailTmpl = new SrvyEmailTmpl();
		srvyEmailTmpl.setSrvyRecId(Integer.parseInt((String)param.get("srvyRecId")));
		srvyEmailTmpl.setEmailType((String)param.get("emailType"));
		srvyEmailTmpl.setEmailSubj((String)param.get("emailSubj"));
		srvyEmailTmpl.setEmailCntn((String)param.get("emailCntn"));
		srvyEmailTmpl.setLastRecTxnUserId(CommonFunction.getSsnUserId());
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SrvyEmailTmpl>> violations = (Set) validator.validate(srvyEmailTmpl);
		if(!violations.isEmpty()){
			map.put("success", "false");
			map.put("message", ViewController.getErrStr(violations));
		}else{
			srvyEmailTmplService.updateEmailTmpl(srvyEmailTmpl);
			map.put("success", "true");
			map.put("message", "Email Template has been updated successfully.");
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}



	@Override
	protected String getDatatablColName(int parseInt) {
		// TODO Auto-generated method stub
		return null;
	}
	
}