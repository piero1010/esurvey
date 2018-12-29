package hk.gov.housingauthority.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hk.gov.housingauthority.editor.SqlTimestampPropertyEditor;
import hk.gov.housingauthority.model.Datatable;
import hk.gov.housingauthority.model.Record;
import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyEmailTmpl;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.model.SysCnfg;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.ExportService;
import hk.gov.housingauthority.service.SrvyEmailHistService;
import hk.gov.housingauthority.service.SrvyEmailTmplService;
import hk.gov.housingauthority.service.SrvyPptService;
import hk.gov.housingauthority.service.SrvyRecService;
import hk.gov.housingauthority.service.SrvyTmplService;
import hk.gov.housingauthority.service.SysCnfgService;
import hk.gov.housingauthority.service.UserService;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

@Controller
public class SrvyController extends ViewController {

	private static final Logger logger = LoggerFactory.getLogger(SrvyController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true, 10));
		binder.registerCustomEditor(Timestamp.class, new SqlTimestampPropertyEditor());
	}

	/* show page - start */
	@RequestMapping(value = "/survey", method = RequestMethod.GET)
	public ModelAndView create(Locale locale, Model model) {
		if(CommonFunction.getSsnISSrvyCoor()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}
		SrvyRec srvyRec = new SrvyRec();
		srvyRec.setSrvyYear(Calendar.getInstance().get(Calendar.YEAR));
		ModelAndView modelAndView = new ModelAndView("survey/create", "srvyRec", srvyRec);
		return modelAndView;
	}

	@RequestMapping(value = "/survey/modify/", method = RequestMethod.POST)
	public ModelAndView modify(@RequestParam String id, Model model) {
		if(CommonFunction.getSsnISSrvyCoor()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}
		SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
		SrvyEmailTmplService srvyEmailTmplService = (SrvyEmailTmplService) context.getBean("srvyEmailTmplService");
		UserService userService = (UserService) context.getBean("userService");
		SrvyRec srvyRec = srvyRecService.getSrvyRecByID(Integer.parseInt(id));
		ArrayList<SrvyEmailTmpl> srvyEmailTmplList = srvyEmailTmplService.getEmailTmplBySrvyRecId(Integer.parseInt(id));
		if (srvyEmailTmplList.size() > 0) {
			for (SrvyEmailTmpl srvyEmailTmpl : srvyEmailTmplList) {
				if (Constant.EMAIL_TYPE_INVITATION.equals(srvyEmailTmpl.getEmailType())) {
					srvyRec.setInvtTmpl(srvyEmailTmpl);
				} else {
					srvyRec.setRmdrTmpl(srvyEmailTmpl);
				}
			}
		}
		srvyRec = srvyRecService.getTotPpt(srvyRec);
		ModelAndView modelAndView = new ModelAndView("survey/modify", "srvyRec", srvyRec);
		modelAndView.addObject("srvyRecId", srvyRec.getSrvyRecId());
		modelAndView.addObject("creDate", Record.getDateFormat().format(srvyRec.getCreDate()));
		modelAndView.addObject("userId", srvyRec.getCoor().getUserId());
		modelAndView.addObject("srvyYear", srvyRec.getSrvyYear());
		modelAndView.addObject("srvyTmplId", srvyRec.getSrvyTmpl().getSrvyTmplId());
		modelAndView.addObject("srvyTmplName", srvyRec.getSrvyTmpl().getSrvyTmplName());
		modelAndView.addObject("srvyTtl", srvyRec.getSrvyTtl());
		modelAndView.addObject("srvySts", srvyRec.getSrvySts().getSrvyStsId());
		modelAndView.addObject("srvyRmk", srvyRec.getSrvyRmk());		
		try {
			modelAndView.addObject("rankList", String.join(",",
					userService.getRankList().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList())));
		} catch (Exception ex) {
			modelAndView.addObject("rankList", "");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/survey/list", method = RequestMethod.GET)
	public ModelAndView list(Locale locale, Model model) {
		if(CommonFunction.getSsnISSrvyCoor()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("survey/list", "srvyRec", new SrvyRec());
		return modelAndView;
	}
	/* show page - end */

	@RequestMapping(value = "/survey/dataExport/", method = RequestMethod.POST)
	public void dataExport(HttpServletRequest req, HttpServletResponse resp, @RequestParam String id)
			throws IOException {
		ExportService exportService = (ExportService) context.getBean("exportService");
		try {
			ByteArrayOutputStream byteStream = exportService.exportSrvy(Integer.parseInt(id));
			if (byteStream != null) {
				resp.setContentType("application/ms-excel");
				// resp.setContentLength(outArray.length);
				resp.setHeader("Expires:", "0");
				resp.setHeader("Content-Disposition", "attachment; filename=SurveyData.xlsx");

				OutputStream outStream = resp.getOutputStream();
				byteStream.writeTo(outStream);
			}
		} catch (Exception e) {
		}
	}

	@ResponseBody
	@RequestMapping(value = "/survey/addSurvey", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String addSurvey(@Valid SrvyRec srvyRec, BindingResult bindingResult, Model model) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		if (srvyRec.getSrvyTmpl().getSrvyTmplId() == null) {
			bindingResult.addError(new FieldError("srvyRec", "srvyTmpl.srvyTmplId", "Please select a \"Template Name\"."));
		}
		if (srvyRec.getSrvyPptCatg().getSrvyPptCatgId() == Constant.SURVEY_PARTICIPANT_CATEGORY_WHOLE_DIVISION) {
			if ("".equals(srvyRec.getPptDivCode())) {
				bindingResult.addError(new FieldError("srvyRec", "pptDivCode", "Please select a \"Division of Participants\"."));
			}
		}

		if (bindingResult.hasErrors()) { /* form validation */
			map.put("success", "false");
			map.put("message", getErrStr(bindingResult));
		} else {
			SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
			UserService userService = (UserService) context.getBean("userService");
			SrvyEmailTmplService srvyEmailTmplService = (SrvyEmailTmplService) context.getBean("srvyEmailTmplService");
			SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");
			HashMap<String, SysCnfg> sysCnfgHashMap = sysCnfgService.getHashMapByGroup("MAILTMPL");
			
			srvyRec.getCoor().setUserId(CommonFunction.getSsnUserId());
			User user = userService.getUserById(CommonFunction.getSsnUserId());
			
			srvyRec.setCreDate(new Date());
			int srvyRecId = srvyRecService.insertSrvyRec(srvyRec);
			
			/* set default invitation template */
			SrvyEmailTmpl srvyEmailTmpl = new SrvyEmailTmpl();
			srvyEmailTmpl.setSrvyRecId(srvyRecId);
			srvyEmailTmpl.setEmailType(Constant.EMAIL_TYPE_INVITATION);
			if(sysCnfgHashMap!=null && sysCnfgHashMap.get("INVITATION_SUBJECT")!=null) {
				srvyEmailTmpl.setEmailSubj(sysCnfgHashMap.get("INVITATION_SUBJECT").getSysCnfgVal().replaceAll("#COORDINATOR_NAME#", user.getUserName()));
			}else {
				srvyEmailTmpl.setEmailSubj("");
			}
			if(sysCnfgHashMap!=null && sysCnfgHashMap.get("INVITATION_CONTENT")!=null) {
				srvyEmailTmpl.setEmailCntn(sysCnfgHashMap.get("INVITATION_CONTENT").getSysCnfgVal().replaceAll("#COORDINATOR_NAME#", user.getUserName()));
			}else {
				srvyEmailTmpl.setEmailCntn("");
			}	
			srvyEmailTmplService.insertEmailTmpl(srvyEmailTmpl);
			
			/* set default reminder template */
			srvyEmailTmpl = new SrvyEmailTmpl();
			srvyEmailTmpl.setSrvyRecId(srvyRecId);
			srvyEmailTmpl.setEmailType(Constant.EMAIL_TYPE_REMINDER);
			if(sysCnfgHashMap!=null && sysCnfgHashMap.get("REMINDER_SUBJECT")!=null) {
				srvyEmailTmpl.setEmailSubj(sysCnfgHashMap.get("REMINDER_SUBJECT").getSysCnfgVal().replaceAll("#COORDINATOR_NAME#", user.getUserName()));
			}else {
				srvyEmailTmpl.setEmailSubj("");
			}
			if(sysCnfgHashMap!=null && sysCnfgHashMap.get("REMINDER_CONTENT")!=null) {
				srvyEmailTmpl.setEmailCntn(sysCnfgHashMap.get("REMINDER_CONTENT").getSysCnfgVal().replaceAll("#COORDINATOR_NAME#", user.getUserName()));
			}else {
				srvyEmailTmpl.setEmailCntn("");
			}			
			srvyEmailTmplService.insertEmailTmpl(srvyEmailTmpl);
			if (Constant.SURVEY_PARTICIPANT_CATEGORY_WHOLE_DIVISION == srvyRec.getSrvyPptCatg().getSrvyPptCatgId()) {
				SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
				srvyPptService.insertSrvyPptByDivision(srvyRecId, srvyRec.getPptDivCode());
			}
			map.put("success", "true");
			map.put("message", "New Survey has been created successfully.");
			map.put("id", Integer.toString(srvyRecId));
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/survey/updateSurvey", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String updateSurvey(@Valid SrvyRec srvyRec, BindingResult bindingResult, Model model) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		if (srvyRec.getSrvyTmpl().getSrvyTmplId() == null) {
			bindingResult.addError(new FieldError("srvyRec", "srvyTmpl.srvyTmplId", "Please select a \"Template Name\"."));
		}
		if (srvyRec.getSrvyPptCatg().getSrvyPptCatgId() == Constant.SURVEY_PARTICIPANT_CATEGORY_WHOLE_DIVISION) {
			if ("".equals(srvyRec.getPptDivCode())) {
				bindingResult.addError(new FieldError("srvyRec", "pptDivCode", "Please select a \"Division of Participants\"."));
			}
		}

		if (bindingResult.hasErrors()) { /* form validation */
			map.put("success", "false");
			map.put("message", getErrStr(bindingResult));
		} else {
			SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
			SrvyRec oSrvyRec = srvyRecService.getSrvyRecByID(srvyRec.getSrvyRecId());
			srvyRec.getCoor().setUserId(CommonFunction.getSsnUserId());
			srvyRec.setCreDate(new Date());
			int id = srvyRecService.updateSrvyRec(srvyRec);			
			map.put("success", "true");
			map.put("message", "Survey has been updated successfully.");
			map.put("id", Integer.toString(id));
			if(!oSrvyRec.getSrvyPptCatg().getSrvyPptCatgId().equals(srvyRec.getSrvyPptCatg().getSrvyPptCatgId()) ||
			   !srvyRec.getPptDivCode().equals(oSrvyRec.getPptDivCode())
			){ /* Remove Participants if Participant Category changes or Division of Participants Change */
				SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
				srvyPptService.deleteSrvyPptBySrvyRecId(srvyRec.getSrvyRecId());				
				if (Constant.SURVEY_PARTICIPANT_CATEGORY_WHOLE_DIVISION == srvyRec.getSrvyPptCatg().getSrvyPptCatgId()) {
					srvyPptService.insertSrvyPptByDivision(srvyRec.getSrvyRecId(), srvyRec.getPptDivCode());
				}
			}
			// Survey status changed from Perpare or Trial-Run to Publish
			if((oSrvyRec.getSrvySts().getSrvyStsId()==1 || oSrvyRec.getSrvySts().getSrvyStsId()==2) && srvyRec.getSrvySts().getSrvyStsId()==3){
				SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
				SrvyTmpl srvyTmpl = srvyTmplService.getSrvyTmplByID(srvyRec.getSrvyTmpl().getSrvyTmplId());
				srvyTmpl.getSrvyTmplFrzInd().setSrvyTmplFrzIndId(Constant.TEMPLATE_STATUS_FROZEN);
				srvyTmplService.updateSrvyTmpl(srvyTmpl);
			}
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/deleteSurvey", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String deleteSurvey(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		if(CommonFunction.getSsnISSrvyCoor()!=true){
			map.put("success", "false");
			map.put("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			jsonObject = new JSONObject(map);
			return jsonObject.toString();
		}else if(param.get("srvyRecId")==null) {
			map.put("success", "false");
			map.put("message", "Please select a survey to delete.");
			jsonObject = new JSONObject(map);
			return jsonObject.toString();
		}else {
			SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
			SrvyRec srvyRec = srvyRecService.getSrvyRecByID(Integer.parseInt((String) param.get("srvyRecId")));
			SrvyTmpl srvyTmpl =srvyRec.getSrvyTmpl();
			if(!CommonFunction.getSsnUserSrvyGrpArr().contains(srvyTmpl.getSrvyGrp().getSrvyGrpId().toString())) {
				map.put("success", "false");
				map.put("message", "You do not have permission to delete this survey.");
				jsonObject = new JSONObject(map);
				return jsonObject.toString();
			}else if(srvyRec.getSrvySts().getSrvyStsId()==Constant.SURVEY_STATUS_PUBLISHED || srvyRec.getSrvySts().getSrvyStsId()==Constant.SURVEY_STATUS_COMPLETED) {
				map.put("success", "false");
				map.put("message", "The survey has been published and cannot be deleted.");
				jsonObject = new JSONObject(map);
				return jsonObject.toString();
			}else {
				srvyRecService.deleteSrvyRec(Integer.parseInt((String) param.get("srvyRecId")));
				map.put("success", "true");
				map.put("message", "The survey has been deleted successfully.");
				jsonObject = new JSONObject(map);
				return jsonObject.toString();
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/survey/cloneSurvey", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String cloneSurvey(@RequestParam Map<String, Object> param) {
		/* TODO check if user has right to clone the selected survey */
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		SrvyEmailTmplService srvyEmailTmplService = (SrvyEmailTmplService) context.getBean("srvyEmailTmplService");
		SrvyRec srvyRec = new SrvyRec();
		srvyRec.setLastRecTxnUserId(CommonFunction.getSsnUserId());
		srvyRec.setSrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
		int newSrvyRecId = srvyRecService.cloneSrvyRec(srvyRec);
		SrvyEmailTmpl srvyEmailTmpl = new SrvyEmailTmpl();
		srvyEmailTmpl.setSrvyRecId(newSrvyRecId);
		srvyEmailTmpl.setLastRecTxnUserId(CommonFunction.getSsnUserId());
		srvyEmailTmplService.cloneEmailTmpl(Integer.parseInt((String) param.get("srvyRecId")),srvyEmailTmpl);
		if ("true".equals((String) param.get("isWithPpt"))) {
			SrvyPpt srvyPpt = new SrvyPpt();
			srvyPpt.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			srvyPpt.setSrvyRecId(Integer.parseInt((String) param.get("srvyRecId")));
			srvyPptService.cloneSrvyPpt(srvyPpt, newSrvyRecId);
		}
		map.put("success", "true");
		map.put("message", "Survey has been cloned successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/survey/clearTrialRun", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String clearTrialRun(@RequestParam Map<String, String> param) {
		/* TODO check if user has right to clear data */
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		SrvyEmailHistService srvyEmailHistService = (SrvyEmailHistService) context.getBean("srvyEmailHistService");

		SrvyPpt srvyPpt = new SrvyPpt();
		srvyPpt.setLastRecTxnUserId(CommonFunction.getSsnUserId());
		srvyPpt.setSrvyRecId(Integer.parseInt(param.get("srvyRecId")));
		int numOfDeletedSrvyPpt = srvyPptService.deleteSrvyPpt(srvyPpt);

		SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
		srvyEmailHist.setLastRecTxnUserId(CommonFunction.getSsnUserId());
		srvyEmailHist.setSrvyRecId(Integer.parseInt(param.get("srvyRecId")));
		int numOfDeletedEmailHist = srvyEmailHistService.deleteEmailHist(srvyEmailHist);

		map.put("success", "true");
		map.put("message", "Trial-Run data have been cleared successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/survey/listSurvey", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listSurvey(@RequestParam Map<String, Object> param) {
		SrvyRecService srvyRecService = (SrvyRecService) context.getBean("srvyRecService");
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 1000 : Integer.parseInt((String) param.get("length"));
		SrvyRec srvyRec = this.getDatatableFilter(param);
		HashMap<String, String> filter = new HashMap<String, String>();
		filter.put("SSN_USER_SRVY_GRP", CommonFunction.getSsnUserSrvyGrpStr());
		srvyRec.setFilterList(filter); /*
										 * list surveys in user survey group
										 * only
										 */
		ArrayList<SrvyRec> srvyRecList = srvyRecService.getSrvyRecList(srvyRec, this.getDatatableSorting(param), start, length);
		srvyRecList = srvyRecService.getTotPpt(srvyRecList);
		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(srvyRecService.listToJsonStr(srvyRecList));
		if (srvyRecList.size() > 0) {
			datatable.setRecordsTotal(Integer.toString(srvyRecList.get(0).getTotRec()));
		} else {
			datatable.setRecordsTotal("0");
		}
		return datatable.toJsonStr(datatable);
	}

	@Override
	public String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("SRVY_REC_ID", "CRE_DATE", "USER_DSGN", "SRVY_YEAR", "SRVY_TTL",
				"SRVY_STS", "BGN_DATE", "END_DATE", "PPT_CATG");
		return colList.get(colNo);
	}

	private SrvyRec getDatatableFilter(Map<String, Object> param) {
		int i = 0;
		SrvyRec srvyRec = new SrvyRec();
		try {
			do {
				try {
					if ("startDateFrom".equals(param.get("filter[" + i + "][name]"))
							&& !"".equals(param.get("filter[" + i + "][value]"))) {
						srvyRec.setStartDateFrom(new Timestamp(Record.getDatetimeFormat()
								.parse((String) param.get("filter[" + i + "][value]") + " 00:00:00").getTime()));
					}
				} catch (Exception ex) {
				}
				try {
					if ("startDateTo".equals(param.get("filter[" + i + "][name]"))
							&& !"".equals(param.get("filter[" + i + "][value]"))) {
						srvyRec.setStartDateTo(new Timestamp(Record.getDatetimeFormat()
								.parse((String) param.get("filter[" + i + "][value]") + " 23:59:59").getTime()));
					}
				} catch (Exception ex) {
				}
				if ("srvyTtl".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyRec.setSrvyTtl((String) param.get("filter[" + i + "][value]"));
				}
				if ("coor.userId".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyRec.getCoor().setUserId((String) param.get("filter[" + i + "][value]"));
				}
				if ("srvyYear".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyRec.setSrvyYear(Integer.parseInt((String) param.get("filter[" + i + "][value]")));
				}
				if ("srvyTmpl.srvyTmplId".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyRec.getSrvyTmpl()
							.setSrvyTmplId(Integer.parseInt((String) param.get("filter[" + i + "][value]")));
				}
				if ("srvySts.srvyStsId".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyRec.getSrvySts().setSrvyStsId(Integer.parseInt((String) param.get("filter[" + i + "][value]")));
				}
				if (param.get("filter[" + ++i + "][name]") == null) {
					break;
				}
			} while (true);
		} catch (Exception ex) {
		}
		return srvyRec;
	}
}