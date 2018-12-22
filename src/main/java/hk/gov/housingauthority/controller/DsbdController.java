package hk.gov.housingauthority.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hk.gov.housingauthority.model.ResultDashboard;
import hk.gov.housingauthority.service.SrvyRecService;
import hk.gov.housingauthority.util.CommonFunction;

@Controller
public class DsbdController {
	
	private static final Logger logger = LoggerFactory.getLogger(DsbdController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) {
		ModelAndView modelAndView = new ModelAndView("admin/dashboard");
		if(CommonFunction.getSsnISSrvyCoor()==false){
			modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}
		return modelAndView;
	}
	
	/* AJAX call - start */
	@ResponseBody
	@RequestMapping(value = "/dashboard/getData", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String listMySurvey(@RequestParam Map<String, Object> param) {
		SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
		ArrayList<ResultDashboard> summery = srvyRecService.getDashboardData(CommonFunction.getSsnUserSrvyGrpStr());
		
		try{
			return  mySrvyListToJsonStr(summery);
		}catch(Exception ex){
			return null;
		}
	}
	/* AJAX call - end */

	public String mySrvyListToJsonStr(ArrayList<ResultDashboard> resultList) {
		String result = new String("[");
		for (int i = 0; i < resultList.size(); i++) {
			result += Convert2JsonString(resultList.get(i));
			if (i + 1 != resultList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	public String Convert2JsonString(ResultDashboard resultDasboard) {
		return "{\"id\" : " + resultDasboard.getSrvyRecId()
				+ ", \"content\" : \"" + resultDasboard.getSrvyTtl()
				+ "\", \"start\" : \"" + resultDasboard.getBgnDate()
				+ "\", \"end\" : \"" + resultDasboard.getEndDate() 
				+ "\", \"submit\" : " + resultDasboard.getSbmtPct()
				+ ", \"notSubmit\" : " + resultDasboard.getNotSbmtPct()
				+ ", \"dateList\" : " + resultDasboard.getSrvyDateList()
				+ ", \"cntList\" : " + resultDasboard.getSrvyCntList() + "}";
	}
}