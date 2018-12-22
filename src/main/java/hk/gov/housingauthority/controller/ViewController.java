package hk.gov.housingauthority.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import hk.gov.housingauthority.model.SrvyEmailSts;
import hk.gov.housingauthority.model.SrvyEmailType;
import hk.gov.housingauthority.model.SrvyPptCatg;
import hk.gov.housingauthority.model.SrvySts;
import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.service.SrvyGrpService;
import hk.gov.housingauthority.service.SrvyTmplService;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

public abstract class ViewController {
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	
	public static <T>String getErrStr(Set<ConstraintViolation<T>> violations){
		String errMsg = "";
		if(!violations.isEmpty()){
			for (ConstraintViolation<T> violation : violations) {
				errMsg = "● "+violation.getMessage()+"<br/>" + errMsg;
			}
		}
		return errMsg;
	}
	
	public static <T>String getErrStr(BindingResult bindingResult){
		String errMsg = "";
		if(bindingResult.hasErrors()){
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				if ("typeMismatch".equals(fieldError.getCode())) {
					if ("srvyYear".equals(fieldError.getField())) {
						errMsg = "● The \"Year\" is not proper. Please check.<br/>" + errMsg;
					} else if ("autoRmdrDate".equals(fieldError.getField())) {
						errMsg = "● The \"Auto Reminder Date\" is not proper. Please check.<br/>" + errMsg;
					} else if ("bgnDate".equals(fieldError.getField())) {
						errMsg = "● The \"Start Date\" is not proper. Please check.<br/>" + errMsg;
					} else if ("endDate".equals(fieldError.getField())) {
						errMsg = "● The \"End Date\" is not proper. Please check.<br/>" + errMsg;
					}
				} else {
					errMsg = "● " + fieldError.getDefaultMessage() + "<br/>" + errMsg;
				}
			}
		}
		return errMsg;
	}
	
	protected String getDatatableSorting(Map<String, Object> param) {
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
		} catch (Exception ex) {}
		return sorting;
	}
	
	
	@ModelAttribute("srvyTmplHashMap")
	public LinkedHashMap<String, String> getSrvyTmplHashMap() {
		SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
		List<String> list = new ArrayList<String>();
		if(CommonFunction.getSsnUserSrvyGrpArr()!=null && CommonFunction.getSsnUserSrvyGrpArr().size()>0){
			for(String grp : CommonFunction.getSsnUserSrvyGrpArr()){
				list.add(grp);
			}
		}
		List<SrvyTmpl> srvyTmplList = srvyTmplService.getSrvyTmplListByCoorGrpList(list);
		LinkedHashMap<String, String> srvyTmplMap = new LinkedHashMap<String, String>();
		srvyTmplMap.put("", "");// set empty value
		for (int i = 0; i < srvyTmplList.size(); i++) {
			srvyTmplMap.put(Integer.toString(srvyTmplList.get(i).getSrvyTmplId()),Integer.toString(srvyTmplList.get(i).getSrvyTmplId()) +" - "+ srvyTmplList.get(i).getSrvyTmplName());
		}
		return srvyTmplMap;
	}

	@ModelAttribute("srvyStsHashMap")
	public LinkedHashMap<String, String> getSrvyStsHashMap() {
		return SrvySts.toLinkedHashMap();
	}

	@ModelAttribute("sbmtStsHashMap")
	public Map<String, String> getSbmtStsHashMap() {
		Map<String, String> sbmtStsHashMap = new HashMap<String, String>();
		sbmtStsHashMap.put("", "");
		sbmtStsHashMap.put("Y", "Yes");
		sbmtStsHashMap.put("N", "No");
		return sbmtStsHashMap;
	}
	
	@ModelAttribute("pptCatgHashMap")
	public LinkedHashMap<String, String> getPptCatgHashMap() {
		return SrvyPptCatg.toLinkedHashMap();
	}

	@ModelAttribute("pptDivCodeHashMap")
	public LinkedHashMap<String, String> getPptDivCodeHashMap() {
		LinkedHashMap<String, String> pptDivCodeHashMap = new LinkedHashMap<String, String>();
		pptDivCodeHashMap.put("", "");
		pptDivCodeHashMap.put("CSD", "CSD");
		pptDivCodeHashMap.put("DCD", "DCD");
		pptDivCodeHashMap.put("EMD", "EMD");
		pptDivCodeHashMap.put("PSO", "PSO");
		pptDivCodeHashMap.put("SD", "SD");
		return pptDivCodeHashMap;
	}
	
	@ModelAttribute("emailTypeHashMap")
	public LinkedHashMap<String, String> getEmailTypeHashMap() {
		return SrvyEmailType.toLinkedHashMap();
	}
	
	@ModelAttribute("emailStsHashMap")
	public LinkedHashMap<String, String> getEmailStsHashMap() {
		return SrvyEmailSts.toLinkedHashMap();
	}
	
	@ModelAttribute("srvyGrpHashMap")
	public Map<String, String> getSrvyGrpHashMap() {
		SrvyGrpService srvyGrpService = (SrvyGrpService) context.getBean("srvyGrpService");
		Map<String, String> srvyGrpMap = new LinkedHashMap<String, String>();		
		srvyGrpMap.putAll(srvyGrpService.getSrvyGrpHashMap());
		return srvyGrpMap;
	}

	@ModelAttribute("srvyTmplFrzIndHashMap")
	public Map<String, String> getSrvyTmplFrzIndHashMap() {
		Map<String, String> srvyTmplFrzIndMap = new LinkedHashMap<String, String>();
		srvyTmplFrzIndMap.put("", "");
		srvyTmplFrzIndMap.put(Constant.TEMPLATE_STATUS_ACTIVE, "Active");
		srvyTmplFrzIndMap.put(Constant.TEMPLATE_STATUS_FROZEN, "Frozen");
		return srvyTmplFrzIndMap;
	}
	
	protected abstract String getDatatablColName(int parseInt);
}
