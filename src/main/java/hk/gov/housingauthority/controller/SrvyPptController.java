package hk.gov.housingauthority.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import hk.gov.housingauthority.model.Datatable;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.service.FileUploadService;
import hk.gov.housingauthority.service.SrvyPptService;
import hk.gov.housingauthority.service.UserService;

/**
 * Handles requests for the application home page.	
 */
@Controller
public class SrvyPptController {
	
	private static final Logger logger = LoggerFactory.getLogger(SrvyPptController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	@ResponseBody
	@RequestMapping(value = "/survey/addParticipant", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String addParticipant(@RequestParam Map<String, Object> param) {
		UserService userService = (UserService) context.getBean("userService");
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		if(userService.isValidUsers((String)param.get("users"))){
			srvyPptService.insertSrvyPpt(Integer.parseInt((String)param.get("srvyRecId")),(String)param.get("users"));
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/upload", method = RequestMethod.POST)
    public String excelFileUpload(@RequestParam("srvyRecId") int srvyRecId, @RequestParam("file") MultipartFile file) {
		FileUploadService uploadService = (FileUploadService) context.getBean("uploadService");		
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		String uploadResult = "";
		
		if (file.getOriginalFilename().contains(".xls") || file.getOriginalFilename().contains(".xlsx")) {
        	try {
        		File convFile = new File(file.getOriginalFilename());
        	    convFile.createNewFile(); 
        	    FileOutputStream fos = new FileOutputStream(convFile); 
        	    fos.write(file.getBytes());
        	    fos.close();
                uploadResult = uploadService.uploadFileData(convFile, srvyRecId);
                map.put("message", uploadResult);
            } catch (IOException e) {
            	map.put("message", "Failure occured during upload '" + file.getOriginalFilename() + "'!");
                e.printStackTrace();
            }
		} else {
			 map.put("message", "Invalid file format! Please select a Excel file to upload!");
        }
        
        jsonObject = new JSONObject(map);
        return jsonObject.toString();
    }
	
	@ResponseBody
	@RequestMapping(value = "/survey/deleteParticipant", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String deleteParticipant(@RequestParam Map<String, Object> param) {
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		srvyPptService.deleteSrvyPpt(Integer.parseInt((String)param.get("srvyRecId")),(String)param.get("srvyPptIds"));
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/survey/listParticipant", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listParticipant(@RequestParam Map<String, Object> param) {
		
		SrvyPptService srvyPptService = (SrvyPptService) context.getBean("srvyPptService");
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 500 : Integer.parseInt((String) param.get("length"));
		
		/* TODO list related Participant only */
		SrvyPpt srvyPptFilter = this.getDatatableFilter(param);
		HashMap<String, String> filter = new HashMap<String, String>();
		filter.put("notDeleted", "D");
		srvyPptFilter.setFilterList(filter);
		List<SrvyPpt> srvyPptList = srvyPptService.getSrvyPptList(srvyPptFilter, this.getDatatableSorting(param), start, length);
		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(srvyPptService.listToJsonStr(srvyPptList));
		if(srvyPptList.size()>0){
			datatable.setRecordsTotal(Integer.toString(srvyPptList.get(0).getTotRec()));
		}else{
			datatable.setRecordsTotal("0");
		}
		return datatable.toJsonStr(datatable);
	}
	
	private SrvyPpt getDatatableFilter(Map<String, Object> param) {
		int i = 0;
		SrvyPpt srvyPpt = new SrvyPpt();
		try {
			do {
				try{
					if ("srvyRecId".equals(param.get("filter[" + i + "][name]")) && !"".equals(param.get("filter[" + i + "][value]"))) {
						srvyPpt.setSrvyRecId(Integer.parseInt((String) param.get("filter[" + i + "][value]")));
					}
				}catch(Exception ex){}
				if (param.get("filter[" + ++i + "][name]") == null) {
					break;
				}
			} while (true);
		} catch (Exception ex){}
		return srvyPpt;
	}
	private String getDatatableSorting(Map<String, Object> param) {
		int i = 0;
		String sorting = "";
		try {
			do {
				sorting += getDatatableColName(Integer.parseInt((String) param.get("order[" + i + "][column]"))) + " "
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

	private String getDatatableColName(int colNo) {
		List<String> colList = Arrays.asList("", "USER_DIV_CODE", "USER_NAME", "USER_EMAIL", "INVT_DATE", "LAST_RMDR_DATE", "SBMT_DATE", "SBMT_STS");
		return colList.get(colNo);
	}
}