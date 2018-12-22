package hk.gov.housingauthority.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hk.gov.housingauthority.model.Coordinator;
import hk.gov.housingauthority.model.Datatable;
import hk.gov.housingauthority.model.SrvyGrp;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.CoordinatorService;
import hk.gov.housingauthority.service.UserService;
import hk.gov.housingauthority.util.CommonFunction;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CoorController extends ViewController {
	private static final Logger logger = Logger.getLogger(CoorController.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	private User user;
	private Coordinator coordinator;
	private Coordinator oCoordinator;
	private ArrayList<User> userResult;

	private String jsonData = "";
	private String jsonReply = "";

	@RequestMapping(value = "/coordinator", method = RequestMethod.GET)
	public ModelAndView coorList(Locale locale, Model model) {
		if (CommonFunction.getSsnISSysAdmin() == false) {
			ModelAndView modelAndView = new ModelAndView("systemMessage");
			modelAndView.addObject("message", "You do not have permission to use this function. Please contact system administrator if necessary.");
			logger.warn(CommonFunction.getSsnUserId()+" not authorized to use the function");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("coordinator/coorList", "coordinator", new Coordinator());
		return modelAndView;
	}

	@RequestMapping(value = "/coordinator/getCoordinator", method = RequestMethod.POST)
	public void getCoordinator(HttpServletRequest request, HttpServletResponse response) {
	
		JSONObject jsonResult = new JSONObject();
		ArrayList<Coordinator> coordinatorList;
		CoordinatorService coordinatorService = (CoordinatorService) context.getBean("coordinatorService");

		try {
			coordinatorList = coordinatorService.getCoordinatorList();

			jsonReply = "";

			jsonData = "[";
			for (int i = 0; i < coordinatorList.size(); i++) {
				Coordinator coordinator = coordinatorList.get(i);
				jsonData += "[";
				jsonData += "\"" + coordinator.getUser().getUserId() + "\",";
				jsonData += "\"" + coordinator.getUser().getUserName() + "\",";
				jsonData += "\"[" + coordinator.getAllGrpId() + "]\",";
				jsonData += "\"" + coordinator.getAllGrpName() + "\",";
				jsonData += "\"" + coordinator.getUser().getUserDivCode() + "\",";
				jsonData += "\"" + coordinator.getUser().getUserDsgn() + "\",";
				jsonData += "\"" + coordinator.getUser().getUserRankCode() + "\"";
				jsonData += "]";
				if (i + 1 != coordinatorList.size()) {
					jsonData += ",";
				}
			}
			jsonData += "]";

			jsonReply += "{ \"data\": "
					+ jsonData + "}";

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();
			out.print(jsonReply);
			out.flush();

		} catch (Exception e) {
			logger.error(CommonFunction.getSsnUserId()+ " " +e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/coordinator/updateCoordinator", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String UpdateCoordinator(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if(CommonFunction.getSsnISSysAdmin()==false){
			map.put("success", "false");
			map.put("message", "You are not authorized to use the function.");
			logger.warn(CommonFunction.getSsnUserId()+" not authorized to use the function");
		}else{
			CoordinatorService coordinatorService = (CoordinatorService) context.getBean("coordinatorService");
			
			try {
				String paramUserId = (String) request.getParameter("details_id").trim();
				String paramSrvyGrpList = (String) request.getParameter("details_group").trim();
				ArrayList<SrvyGrp> srvyGrpList = new ArrayList<SrvyGrp>();
	
				coordinator = new Coordinator();
				coordinator.getUser().setUserId(paramUserId);
				String[] grpIdList = paramSrvyGrpList.split(",");
	
				for (int i = 0; i < grpIdList.length; i++) {
					SrvyGrp srvyGrp = new SrvyGrp();
					srvyGrp.setSrvyGrpId(Integer.parseInt(grpIdList[i]));
					srvyGrpList.add(srvyGrp);
				}
				coordinator.setSrvyGrpList(srvyGrpList);
				coordinatorService.updateCoordinator(coordinator);
			} catch (Exception e) {
				logger.error(CommonFunction.getSsnUserId()+ " " +e);
			}
			logger.info(CommonFunction.getSsnUserId()+" updated coordinator "+request.getParameter("details_id").trim()+" with " + request.getParameter("details_group").trim());
			map.put("success", "true");
			map.put("message", "Coordinator has been successfully updated.");
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/coordinator/addCoordinator", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String addCoordinator(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if(CommonFunction.getSsnISSysAdmin()==false){
			map.put("success", "false");
			map.put("message", "You are not authorized to use the function.");
			logger.warn(CommonFunction.getSsnUserId()+" not authorized to use the function");
		}else{
			CoordinatorService coordinatorService = (CoordinatorService) context.getBean("coordinatorService");
			try {
				String paramUserId = (String) request.getParameter("details_id").trim();
				String paramSrvyGrpList = (String) request.getParameter("details_group").trim();
				ArrayList<SrvyGrp> srvyGrpList = new ArrayList<SrvyGrp>();
				coordinator = new Coordinator();
				coordinator.getUser().setUserId(paramUserId);
				String[] grpIdList = paramSrvyGrpList.split(",");
				for (int i = 0; i < grpIdList.length; i++) {
					SrvyGrp srvyGrp = new SrvyGrp();
					srvyGrp.setSrvyGrpId(Integer.parseInt(grpIdList[i]));
					srvyGrpList.add(srvyGrp);
				}
				coordinator.setSrvyGrpList(srvyGrpList);
				coordinatorService.updateCoordinator(coordinator);
			} catch (Exception e) {
				logger.error(CommonFunction.getSsnUserId()+ " " +e);
			}
			logger.info(CommonFunction.getSsnUserId()+" added coordinator "+request.getParameter("details_id").trim()+" with " + request.getParameter("details_group").trim());
			map.put("success", "true");
			map.put("message", "Coordinator has been successfully added.");
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/coordinator/deleteCoordinator", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String deleteCoordinator(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if(CommonFunction.getSsnISSysAdmin()==false){
			map.put("success", "false");
			map.put("message", "You are not authorized to use the function.");
			logger.warn(CommonFunction.getSsnUserId()+" not authorized to use the function");
		}else{
			CoordinatorService coordinatorService = (CoordinatorService) context.getBean("coordinatorService");
			try {
				String paramUserId = (String) request.getParameter("details_id").trim();
				coordinator = new Coordinator();
				coordinator.getUser().setUserId(paramUserId);
				coordinatorService.inactiveCoordinator(coordinator);
			} catch (Exception e) {
				logger.error(CommonFunction.getSsnUserId()+ " " +e);
			}
			logger.info(CommonFunction.getSsnUserId()+" deleted coordinator "+request.getParameter("details_id").trim());
			map.put("success", "true");
			map.put("message", "Coordinator has been successfully removed.");
		}
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/coordinator/searchUserCoordinator", method = RequestMethod.POST)
	public String searchUserCoordinator(@RequestParam Map<String, Object> param) {
		int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
		int length = (param.get("length") == null) ? 20 : Integer.parseInt((String) param.get("length"));

		userResult = new ArrayList<User>();
		User userFilter = new User();
		if(!param.get("search_designation").equals("")) 
			userFilter.setUserDsgn((String)param.get("search_designation"));
		if(!param.get("search_division").equals("")) 
			userFilter.setUserDivCode((String)param.get("search_division"));
		if(!param.get("search_name").equals(""))
			userFilter.setUserName((String)param.get("search_name"));
		if(!param.get("search_rank").equals(""))
			userFilter.setUserRankCode((String)param.get("search_rank") );
		UserService userService = (UserService) context.getBean("userService");
		ArrayList<User> userList = userService.getCoorUserList(userFilter, this.getDatatableSorting(param), start, length);
		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(userService.listToCoorJsonStr(userList));
		if (userList.size() > 0) {
			datatable.setRecordsTotal(Integer.toString(userList.get(0).getTotRec()));
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
	
	private User getDatatableFilter(Map<String, Object> param) {
		int i = 0;
		User user = new User();
		try {
			do {
				if ("search_designation".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					user.setUserDsgn((String) param.get("filter[" + i + "][value]"));
				}
				if ("search_division".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					user.setUserDivCode((String) param.get("filter[" + i + "][value]"));
				}
				if ("search_name".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					user.setUserName((String) param.get("filter[" + i + "][value]"));
				}
				if ("search_rank".equals(param.get("filter[" + i + "][name]"))
						&& !"".equals(param.get("filter[" + i + "][value]"))) {
					user.setUserRankCode((String) param.get("filter[" + i + "][value]"));
				}
				if (param.get("filter[" + ++i + "][name]") == null) {
					break;
				}
			} while (true);
		} catch (Exception ex) {
		}
		return user;
	}

	private String convertUserListToJson(ArrayList<User> user) {
		String jsonInString = "[";
		for (int i = 0; i < user.size(); i++) {
			jsonInString += "[";
			jsonInString += "\"" + user.get(i).getUserId() + "\",";
			jsonInString += "\"" + user.get(i).getUserDivCode() + "\",";
			jsonInString += "\"" + user.get(i).getUserRankCode() + "\",";
			jsonInString += "\"" + user.get(i).getUserDsgn() + "\",";
			jsonInString += "\"" + user.get(i).getUserName() + "\"";

			jsonInString += "]";
			if (i + 1 != user.size()) {
				jsonInString += ",";
			}
		}
		jsonInString += "]";
		return jsonInString;
	}

	@Override
	public String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("USER_ID","USER_DIV_CODE", "USER_RANK_CODE", "USER_DSGN", "USER_NAME");
		return colList.get(colNo);
	}

}