package hk.gov.housingauthority.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hk.gov.housingauthority.model.Datatable;
import hk.gov.housingauthority.model.SysCnfg;
import hk.gov.housingauthority.service.SysCnfgService;
import hk.gov.housingauthority.util.CommonFunction;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CodeController extends ViewController {

	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public ModelAndView create(Locale locale, Model model) {
		if(CommonFunction.getSsnISSysAdmin()==false){
			ModelAndView modelAndView = new ModelAndView("systemMessage");
	        modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
	        logger.warn(CommonFunction.getSsnUserId()+" not authorized to use the function");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("code/list", "sysCnfg", new SysCnfg());
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/code/addCode", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String addCode(@Valid SysCnfg sysCnfg, BindingResult bindingResult, Model model) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		if (bindingResult.hasErrors()) { /* form validation */
			map.put("success", "false"); 
			map.put("message", getErrStr(bindingResult));
		} else {
			SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");
			sysCnfg.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			sysCnfgService.insertSysCnfg(sysCnfg);
			map.put("success", "true");
			map.put("message", "Code has been added successfully.");
			logger.info(CommonFunction.getSsnUserId()+" added code "+
			sysCnfg.getSysCnfgId()+" "+sysCnfg.getSysCnfgGrp()+" "+
			sysCnfg.getSysCnfgVal()+" "+sysCnfg.getSysCnfgDesc());			
		}
		jsonObject = new JSONObject(map); 
		return jsonObject.toString();  
	}

	@ResponseBody
	@RequestMapping(value = "/code/updateCode", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String updateCode(@Valid SysCnfg sysCnfg, BindingResult bindingResult, Model model) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		if (bindingResult.hasErrors()) { /* form validation */
			map.put("success", "false");
			map.put("message", getErrStr(bindingResult));
		} else {
			SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");

			sysCnfg.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			if (sysCnfgService.updateSysCnfg(sysCnfg) != 0) {
				map.put("success", "true");
				map.put("message", "Code has been updated successfully.");
				logger.info(CommonFunction.getSsnUserId()+" updated code "+
						sysCnfg.getSysCnfgId()+" "+sysCnfg.getSysCnfgGrp()+" "+
						sysCnfg.getSysCnfgVal()+" "+sysCnfg.getSysCnfgDesc());
			}else{/* if nothing is updated. */
				map.put("success", "false");
				map.put("message", "System is busy, please try again.");
			}
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/code/deleteCode", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String deleteCode(@RequestParam Map<String, Object> param) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			SysCnfg sysCnfg = new SysCnfg();
			sysCnfg.setSysCnfgId((String) param.get("sysCnfgId"));

			sysCnfg.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");
			sysCnfgService.deleteSysCnfg(sysCnfg);
			map.put("success", "true");
			map.put("message", "Code has been deleted successfully.");
		} catch (Exception ex) {
			map.put("success", "false");
			map.put("message", "System is busy, please try again.");
			logger.error(CommonFunction.getSsnUserId()+ " " +ex);
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/code/listCode", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listCode(@RequestParam Map<String, Object> param) {
		SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 1000 : Integer.parseInt((String) param.get("length"));
		SysCnfg sysCnfg = new SysCnfg();
		ArrayList<SysCnfg> sysCnfgList = sysCnfgService.getSysCnfgList(sysCnfg, this.getDatatableSorting(param), start, length);

		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(sysCnfgService.listToJsonStr(sysCnfgList));
		if (sysCnfgList.size() > 0) {
			datatable.setRecordsTotal(Integer.toString(sysCnfgList.get(0).getTotRec()));
		} else {
			datatable.setRecordsTotal("0");
		}
		return datatable.toJsonStr(datatable);
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
		} catch (Exception ex) {
		}
		return sorting;
	}

	protected String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("SYS_CNFG_ID", "SYS_CNFG_VAL", "SYS_CNFG_GRP", "SYS_CNFG_DESC");
		return colList.get(colNo);
	}

}