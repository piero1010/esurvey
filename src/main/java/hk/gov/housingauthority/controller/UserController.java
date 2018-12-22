package hk.gov.housingauthority.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import hk.gov.housingauthority.model.Datatable;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.UserService;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		UserService userService = (UserService) context.getBean("userService");
		int i = userService.getTotal();

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "user/coordinator_maintenance";
	}

	@ResponseBody
	@RequestMapping(value = "/user/listUser/", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String listUser(@RequestParam Map<String, Object> param) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		UserService userService = (UserService) context.getBean("userService");
		ArrayList<User> userList = new ArrayList<User>();
		if ("false".equals((String) param.get("clearUserTable"))) {
			int start = (param.get("start") == null) ? 0 : Integer.parseInt((String) param.get("start"));
			int length = (param.get("length") == null) ? 500 : Integer.parseInt((String) param.get("length"));
			User userFilter = this.getDatatableFilter(param);
			userList = userService.getUserList(userFilter, this.getDatatableSorting(param), start, length);
		}
		Datatable datatable = new Datatable();
		datatable.setDraw((String) param.get("draw"));
		datatable.setData(userService.toJsonStr(userList));
		if (userList.size() > 0) {
			datatable.setRecordsTotal(Integer.toString(userList.get(0).getTotRec()));
		} else {
			datatable.setRecordsTotal("0");
		}
		return datatable.toJsonStr(datatable);
	}

	private String getDatatablColName(int colNo) {
		List<String> colList = Arrays.asList("USER_ID", "USER_DIV_CODE", "USER_RANK_CODE", "USER_DSGN", "USER_NAME");
		return colList.get(colNo);
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

	private User getDatatableFilter(Map<String, Object> param) {
		int i = 0;
		User user = new User();
		try {
			if (param.get("searchUserDivision") != null && !"".equals(param.get("searchUserDivision"))) {
				user.setUserDivCode((String) param.get("searchUserDivision"));
			}
			if (param.get("searchUserRank") != null && !"".equals(param.get("searchUserRank"))) {
				user.setUserRankCode((String) param.get("searchUserRank"));
			}
			if (param.get("searchUserDesignation") != null && !"".equals(param.get("searchUserDesignation"))) {
				user.setUserDsgn((String) param.get("searchUserDesignation"));
			}
			if (param.get("searchUserName") != null && !"".equals(param.get("searchUserName"))) {
				user.setUserName((String) param.get("searchUserName"));
			}
			if (param.get("srvyRecId") != null && !"".equals(param.get("srvyRecId"))) {
				HashMap<String, String> filter = new HashMap<String, String>();
				filter.put("srvyRecId", (String) param.get("srvyRecId"));
				user.setFilterList(filter);
			}
		} catch (Exception ex) {
		}
		return user;
	}
}