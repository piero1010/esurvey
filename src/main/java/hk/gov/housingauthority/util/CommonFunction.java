package hk.gov.housingauthority.util;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonFunction {
	
	public static Boolean getSsnISSysAdmin(){
	    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    Boolean result = (Boolean) httpSession.getAttribute(Constant.SSN_IS_SYS_ADMIN);
	    if (result == null) {
	        return false;
	    }
	    return result;
	}
	public static Boolean getSsnISSrvyCoor(){
	    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    Boolean result = (Boolean) httpSession.getAttribute(Constant.SSN_IS_SRVY_COOR);
	    if (result == null) {
	        return false;
	    }
	    return result;
	}
	
	public static String getSsnUserId() {
	    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    String userId = (String) httpSession.getAttribute(Constant.SSN_USER_ID);
	    if (userId == null) {
	        return null;
	    }
	    return userId;
	}
	
	public static String getSsnUserEmail() {
	    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    String userEmail = (String) httpSession.getAttribute(Constant.SSN_USER_EMAIL);
	    if (userEmail == null) {
	        return null;
	    }
	    return userEmail;
	}
	
	public static String getSsnUserSrvyGrpStr() {
	    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    String value = (String) httpSession.getAttribute(Constant.SSN_USER_SRVY_GRP_STR);
	    if (value == null) {
	        return null;
	    }
	    return value;
	}
	
	public static ArrayList<String> getSsnUserSrvyGrpArr() {
	    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
	    ArrayList<String> result = new ArrayList<String>();
	    if ( httpSession.getAttribute(Constant.SSN_USER_SRVY_GRP_ARR) != null) {
		    result = (ArrayList<String>) httpSession.getAttribute(Constant.SSN_USER_SRVY_GRP_ARR);
	    }
	    return result;
	}
}
