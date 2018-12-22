package hk.gov.housingauthority.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hk.gov.housingauthority.model.Record;
import hk.gov.housingauthority.util.Constant;

@Controller
public class SsnController {

	/* AJAX call - start */
	@ResponseBody
	@RequestMapping(value = "/isActive", method = RequestMethod.POST)
	public String isActive(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession(true);
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "true");
		try {
			if (httpSession.getAttribute(Constant.SSN_LAST_TXN_DATETIME) != null) {
				Date lastTxnDt = Record.getDatetimeFormat().parse((String) httpSession.getAttribute(Constant.SSN_LAST_TXN_DATETIME));
				Date now = new Date();
				long ideaTime = now.getTime() - lastTxnDt.getTime();
				if (ideaTime > Constant.SSN_WARNING_INTERVAL * 1000 && ideaTime < Constant.SSN_EXPIRY_INTERVAL * 1000) {
					map.put("message", "warning");
				} else if (ideaTime >= Constant.SSN_EXPIRY_INTERVAL * 1000) {
					map.put("message", "expiry");
				}
				map.put("timeLeft", Long.toString((Constant.SSN_EXPIRY_INTERVAL * 1000 - ideaTime) / 1000));
			} else {
				map.put("message", "");
			}
		} catch (Exception ex) {
			map.put("success", "false");
		}

		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/refreshSession", method = RequestMethod.POST)
	public String refreshSession(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject jsonObject = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "true");
		jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	/* AJAX call - end */
}
