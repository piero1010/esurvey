package hk.gov.housingauthority.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import hk.gov.housingauthority.model.SrvyAdtnInfo;
import hk.gov.housingauthority.service.SrvyAdtnInfoService;
import hk.gov.housingauthority.util.CommonFunction;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SrvyAdtnInfoController {

	private static final Logger logger = LoggerFactory.getLogger(SrvyAdtnInfoController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@ResponseBody
	@RequestMapping(value = "/survey/addAdtnInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String addAdtnInfo(@RequestParam Map<String, Object> param) {
		
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			SrvyAdtnInfo srvyAdtnInfo = new SrvyAdtnInfo();
			srvyAdtnInfo.setSrvyRecId(Integer.parseInt((String)param.get("srvyRecId")));
			srvyAdtnInfo.setAdtnInfoName(((String)param.get("adtnInfoName")).toUpperCase());
			srvyAdtnInfo.setAdtnInfoDesc((String)param.get("adtnInfoDesc"));
			srvyAdtnInfo.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			SrvyAdtnInfoService srvyAdtnInfoService = (SrvyAdtnInfoService) context.getBean("srvyAdtnInfoService");
		
			if("".equals(srvyAdtnInfo.getAdtnInfoName())){
				map.put("success", "false");
				map.put("message", "The \"Code\" is mandatory.");
			}else if(srvyAdtnInfoService.isSrvyAdtnInfoExists(srvyAdtnInfo)){
				map.put("success", "false");
				map.put("message", "Additional Information already exists.");
			}else{
				int id = srvyAdtnInfoService.insertSrvyAdtnInfo(srvyAdtnInfo);
				map.put("success", "true");
				map.put("message", "New Additional Information has been created successfully.");
				map.put("id", Integer.toString(id));
			}
		} catch (Exception ex) {
			map.put("success", "false");
			map.put("message", "System is busy, please try again.");
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/editAdtnInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String editAdtnInfo(@RequestParam Map<String, Object> param) {
		
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			SrvyAdtnInfo srvyAdtnInfo = new SrvyAdtnInfo();
			srvyAdtnInfo.setSrvyAdtnInfoId(Integer.parseInt((String)param.get("adtnInfoId")));
			srvyAdtnInfo.setAdtnInfoDesc((String)param.get("adtnInfoDesc"));
			srvyAdtnInfo.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			SrvyAdtnInfoService srvyAdtnInfoService = (SrvyAdtnInfoService) context.getBean("srvyAdtnInfoService");
			srvyAdtnInfoService.updateSrvyAdtnInfo(srvyAdtnInfo);
			map.put("success", "true");
			map.put("message", "Additional Information has been updated successfully.");
		} catch (Exception ex) {
			map.put("success", "false");
			map.put("message", "System is busy, please try again.");
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/deleteAdtnInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String deleteAdtnInfo(@RequestParam Map<String, Object> param) {
		
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			SrvyAdtnInfo srvyAdtnInfo = new SrvyAdtnInfo();
			srvyAdtnInfo.setSrvyAdtnInfoId(Integer.parseInt((String)param.get("adtnInfoId")));

			srvyAdtnInfo.setLastRecTxnUserId(CommonFunction.getSsnUserId());
			SrvyAdtnInfoService srvyAdtnInfoService = (SrvyAdtnInfoService) context.getBean("srvyAdtnInfoService");
			srvyAdtnInfoService.deleteSrvyAdtnInfo(srvyAdtnInfo);
			map.put("success", "true");
			map.put("message", "Additional Information has been deleted successfully.");
		} catch (Exception ex) {
			map.put("success", "false");
			map.put("message", "System is busy, please try again.");
		}
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/listAdtnInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listAdtnInfo(@RequestParam Map<String, Object> param) {
		/* TODO list related Survey AdtnInfo only */
		SrvyAdtnInfoService srvyAdtnInfoService = (SrvyAdtnInfoService) context.getBean("srvyAdtnInfoService");
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 1000 : Integer.parseInt((String) param.get("length"));
		
		SrvyAdtnInfo srvyAdtnInfoFilter = new SrvyAdtnInfo();
		srvyAdtnInfoFilter.setSrvyRecId(Integer.parseInt((String)param.get("srvyRecId")));
		ArrayList<SrvyAdtnInfo> srvyAdtnInfoList = srvyAdtnInfoService.getSrvyAdtnInfoList(srvyAdtnInfoFilter, this.getDatatableSorting(param), start, length);
	
		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(srvyAdtnInfoService.listToJsonStr(srvyAdtnInfoList));
		if(srvyAdtnInfoList.size()>0){
			datatable.setRecordsTotal(Integer.toString(srvyAdtnInfoList.get(0).getTotRec()));
		}else{
			datatable.setRecordsTotal("0");
		}
		return datatable.toJsonStr(datatable);
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
		} catch (Exception ex) {}
		return sorting;
	}
		
	private String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("ADTN_INFO_NAME", "ADTN_INFO_DESC");
		return colList.get(colNo);
	}

}