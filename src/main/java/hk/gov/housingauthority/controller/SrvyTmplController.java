package hk.gov.housingauthority.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.service.SrvyTmplService;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

@Controller
public class SrvyTmplController extends ViewController{
	private static final Logger logger = LoggerFactory.getLogger(SrvyTmplController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	/* show page - start */
	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public ModelAndView create(Locale locale, Model model) {
		if(CommonFunction.getSsnISSrvyCoor()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("template/create", "SrvyTmpl", new SrvyTmpl());
		return modelAndView;
	}

	@RequestMapping(value = "/template/modify/", method = RequestMethod.POST)
	public ModelAndView modify(@RequestParam String id, Model model) {
		if(CommonFunction.getSsnISSrvyCoor()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}

		SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
		ModelAndView modelAndView = new ModelAndView("template/modify", "SrvyTmpl",
				srvyTmplService.getSrvyTmplByID(new Integer(id)));

		return modelAndView;
	}

	@RequestMapping(value = "/template/list", method = RequestMethod.GET)
	public ModelAndView list(Locale locale, Model model) {
		if(CommonFunction.getSsnISSrvyCoor()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			return modelAndView;
		}
		SrvyTmpl srvyTmpl = new SrvyTmpl();
		srvyTmpl.getSrvyTmplFrzInd().setSrvyTmplFrzIndId(Constant.TEMPLATE_STATUS_ACTIVE);
		ModelAndView modelAndView = new ModelAndView("template/list", "SrvyTmpl",srvyTmpl);
		return modelAndView;
	}
	/* show page - end */

	/* AJAX call - start */
	@ResponseBody
	@RequestMapping(value = "/template/listTemplate", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listTemplate(@RequestParam Map<String, Object> param) {
		SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 10 : Integer.parseInt((String) param.get("length"));
		SrvyTmpl srvyTmpl = this.getSrvyTmplFromFilter(param);
		HashMap<String, String> filter = new HashMap<String, String>();
		filter.put("SSN_USER_SRVY_GRP",CommonFunction.getSsnUserSrvyGrpStr());
		srvyTmpl.setFilterList(filter); /* list survey template in user survey group only */
		List<SrvyTmpl> srvyTmplList = srvyTmplService.getSrvyTmplList(srvyTmpl, this.getSortingForDatatable(param), start, length);
		try {
			return getDatatableJsonStr((String) param.get("draw"), Integer.toString(srvyTmplList.get(0).getTotRec()), srvyTmplService.srvyTmplListToJsonStr(srvyTmplList));
		} catch (Exception ex) {
			return getDatatableJsonStr((String) param.get("draw"), "0",
					srvyTmplService.srvyTmplListToJsonStr(srvyTmplList));
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/template/cloneTemplate", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String cloneTemplate(@RequestParam Map<String, String> param) {
		SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		SrvyTmpl srvyTmpl = new SrvyTmpl();
		srvyTmpl.setSrvyTmplId(Integer.parseInt(param.get("srvyTmplId")));

		srvyTmpl.setLastRecTxnUserId(CommonFunction.getSsnUserId());
		srvyTmplService.cloneSrvyTmpl(srvyTmpl);
		map.put("success", "true");
		map.put("message", "Template has been cloned successfully.");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/template/addTemplate", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String addTemplate(@Valid SrvyTmpl srvyTmpl, BindingResult bindingResult, Model model) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		String message = "";
		if (bindingResult.hasErrors()) { /* form validation */
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				message = "● " + fieldError.getDefaultMessage() + "<br/>" + message;
			}
			map.put("success", "false");
			map.put("message", message);
		} else {
			try {
				srvyTmpl.getSrvyUser().setUserId(CommonFunction.getSsnUserId());
				/* TODO Category by survey */
				srvyTmpl.setSrvyRptCat(Constant.REPORT_CATEGORY_TRAINING_OUTSIDE);
				srvyTmpl.getSrvyTmplFrzInd().setSrvyTmplFrzIndId(Constant.TEMPLATE_STATUS_ACTIVE);
				SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
				int id = srvyTmplService.insertSrvyTmpl(srvyTmpl);
				map.put("success", "true");
				map.put("message", "New Template has been created successfully.");
				map.put("id", Integer.toString(id));
			} catch (Exception ex) {
				map.put("success", "false");
				map.put("message", "System is busy, please try again.");
			}
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/template/updateTemplate", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String updateTemplate(@Valid SrvyTmpl srvyTmpl, BindingResult bindingResult, Model model) {
		/* TODO check user has right to update the survey template */

		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		String message = "";
		if (bindingResult.hasErrors()) { /* form validation */
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				message = "● " + fieldError.getDefaultMessage() + "<br/>" + message;
			}
			map.put("success", "false");
			map.put("message", message);
		} else {
			try {
				SrvyTmplService srvyTmplService = (SrvyTmplService) context.getBean("srvyTmplService");
				if (srvyTmplService.updateSrvyTmpl(srvyTmpl) != 0) {
					map.put("success", "true");
					map.put("message", "Template has been updated successfully.");
				} else {
					throw new Exception();
				}
			} catch (Exception ex) {
				map.put("success", "false");
				map.put("message", "System is busy, please try again.");
			}
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/* AJAX call - end */

	/* functions - start */
	private String getSortingForDatatable(Map<String, Object> param) {
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

	protected String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("SRVY_TMPL_ID", "SRVY_TMPL_NAME", "SRVY_GRP_NAME", "SRVY_USER_ID",
				"SRVY_CRE_DATE", "SRVY_TMPL_FRZ_IND");
		return colList.get(colNo);
	}

	private String getDatatableJsonStr(String draw, String recordsTotal, String data) {
		String result = "{\"recordsFiltered\":\"" + recordsTotal + "\",\"draw\":\"" + draw + "\",\"data\":" + data
				+ ",\"recordsTotal\":\"" + recordsTotal + "\"}";
		return result;
	}

	private SrvyTmpl getSrvyTmplFromFilter(Map<String, Object> param) {
		int i = 0;
		SrvyTmpl srvyTmpl = new SrvyTmpl();
		try {
			do {
				if ("srvyTmplId".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyTmpl.setSrvyTmplId(Integer.parseInt((String) param.get("filter[" + i + "][value]")));
				}
				if ("srvyTmplName".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyTmpl.setSrvyTmplName((String) (param.get("filter[" + i + "][value]")));
				}
				if ("srvyGrp.srvyGrpId".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyTmpl.getSrvyGrp()
							.setSrvyGrpId(Integer.parseInt((String) (param.get("filter[" + i + "][value]"))));
				}
				if ("srvyTmplFrzInd.srvyTmplFrzIndId".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					srvyTmpl.getSrvyTmplFrzInd().setSrvyTmplFrzIndId((String) (param.get("filter[" + i + "][value]")));
				}
				if (param.get("filter[" + ++i + "][name]") == null) {
					break;
				}
			} while (true);
		} catch (Exception ex) {
		}
		return srvyTmpl;
	}
	/* functions - end */
	
	
}