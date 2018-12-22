package hk.gov.housingauthority.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import hk.gov.housingauthority.model.Coordinator;
import hk.gov.housingauthority.model.Properties;
import hk.gov.housingauthority.model.Record;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.CoordinatorService;
import hk.gov.housingauthority.service.UserService;
import hk.gov.housingauthority.util.Constant;

public class LoginValidation extends OncePerRequestFilter {
	private static Logger logger = Logger.getLogger(LoginValidation.class);
	
	private HttpSession httpSession;
	ArrayList<String> bypassList = new ArrayList<String>();
	
	public LoginValidation(){
		bypassList.add("notAuthorized");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		Properties sysProp = (Properties)context.getBean("sysProp");
		HashMap<String, String> sysPropHash = sysProp.getHashMap();
		String userId = "";
		String userEmail = "";
		try{
			request.setAttribute("basePath", sysPropHash.get("URL_LOGIN") + request.getContextPath());
			httpSession = request.getSession(true);
			
			if (!this.isBypass(request, "isActive")){ /* set last access time */
				httpSession.setAttribute(Constant.SSN_LAST_TXN_DATETIME,Record.getDatetimeFormat().format(new Date()));
			}
			if (request.getServletPath().toLowerCase().endsWith("favicon.ico")) { 
				response.sendRedirect(sysPropHash.get("URL_LOGIN")); /* redirect to eHousing login page */
				return;
			}
			if("TRUE".equalsIgnoreCase(sysPropHash.get("IS_LOCAL_TEST"))){
				if (isLoginEHousing(request.getHeader("iv-user"))) {	
					userId = request.getHeader("iv-user");
				}else{
					userId = "diamondchan";
					userEmail = "nixon.yeung@housingauthority.gov.hk";
				}
			}else{
				/* check if request is came from Junction */
				if (!isLoginEHousing(request.getHeader("iv-user"))) {/* not from Junction. Redirect to eHousing login page */
					response.sendRedirect(sysPropHash.get("URL_LOGIN")); /* redirect to eHousing login page */
					return;
				}
				userId = request.getHeader("iv-user");
			}
			if ("TRUE".equalsIgnoreCase(sysPropHash.get("IS_LOCAL_TEST")) || httpSession.getAttribute(Constant.SSN_USER_ID) == null) { /* if not login before, store user information to session */			
				
				CoordinatorService coordinatorService = (CoordinatorService) context.getBean("coordinatorService");
				Coordinator coordinator = coordinatorService.getCoordinatorByID(userId);			
				UserService userService = (UserService) context.getBean("userService");
				User user = userService.getUserById(userId);		
				if(user!=null && user.getUserEmail()!=null){
					userEmail = user.getUserEmail();
				}
				httpSession.setAttribute(Constant.SSN_USER_ID,userId);
				httpSession.setAttribute(Constant.SSN_USER_EMAIL,userEmail);
				
				if (coordinator != null){
					coordinatorService.updateCoorLastLoginDate(userId);
					httpSession.setAttribute(Constant.SSN_USER_SRVY_GRP_STR,coordinator.getAllGrpId());
					httpSession.setAttribute(Constant.SSN_USER_SRVY_GRP_ARR,coordinatorService.getSrvyGrpArr(coordinator.getSrvyGrpList()));
					httpSession.setAttribute(Constant.SSN_IS_SRVY_COOR,coordinatorService.isSuryCoor(coordinator.getSrvyGrpList()));
					httpSession.setAttribute(Constant.SSN_IS_SYS_ADMIN,coordinatorService.isSysAdmin(coordinator.getSrvyGrpList()));					
				}				
				httpSession.setAttribute("URL_LOGIN",sysPropHash.get("URL_LOGIN"));
				httpSession.setAttribute("URL_LOGOUT",sysPropHash.get("URL_LOGOUT"));
				logger.info(userId +" login successful with groups "+((coordinator!= null)?coordinator.getAllGrpId():""));
			}
		}catch(Exception ex){
			logger.info(userId+" login failed.");
			logger.info(ex.toString());
		}
		filterChain.doFilter(request, response);
		return;
	}

	private boolean isBypass(HttpServletRequest request, String extension) {
		try {
			if (request.getServletPath().toLowerCase().endsWith(extension.toLowerCase())) {/* Bypass the validation if the URL in bypass list */
				return true;
			}
			if (request.getServletPath().indexOf(".")!=-1) {/* Bypass the validation if requesting resource */
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}
	
	private boolean isLoginEHousing(String input) {
		if (input == null)
			return false;
		else
			return true;
	}
}
