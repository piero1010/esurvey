package hk.gov.housingauthority.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hk.gov.housingauthority.dao.UserDao;
import hk.gov.housingauthority.model.SysCnfg;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.service.SysCnfgService;
import hk.gov.housingauthority.util.Constant;

public class CollectUserBatch extends QuartzJobBean{
	private static final Logger logger = LoggerFactory.getLogger(CollectUserBatch.class);
	
    @Override
    protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException {
    	logger.info("Start CollectUserBatch");
		try {	
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
			SysCnfgService sysCnfgService = (SysCnfgService) context.getBean("sysCnfgService");
			UserDao userDao = (UserDao) context.getBean("userDao");
			
			HashMap<String, SysCnfg> sysCnfgHashMap = sysCnfgService.getHashMapByGroup("LDAP");
			LdapContext ctx = this.getLdapContext(sysCnfgHashMap);

			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			NamingEnumeration<SearchResult> results = ctx.search(sysCnfgHashMap.get(Constant.SYS_CNFG_LDAP_BASE).getSysCnfgVal(),"(&(objectclass=person))", sc);
			ArrayList<User> userArrayList = new ArrayList<User>();
			while (results.hasMoreElements()) {
				SearchResult searchResult = (SearchResult) results.nextElement();
				userArrayList.add(convertSearchResultToUser(searchResult));
			}
			if(userArrayList.size()>0){
				userDao.insertUser(userArrayList);
				logger.info("Total LDAP users:"+userArrayList.size());
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}
		logger.info("End CollectUserBatch");
    }


	private User convertSearchResultToUser(SearchResult searchResult) {
		User user = new User();
		try {
			if (searchResult.getAttributes().get("givenname") != null) {
				String name = searchResult.getAttributes().get("title").toString().replaceAll("title: ", "");
				name += " " + searchResult.getAttributes().get("givenname").toString().replaceAll("givenName: ", "").replaceAll("givenname: ", "");
				name += " " + searchResult.getAttributes().get("sn").toString().replaceAll("sn: ", "");
				user.setUserName(name);
			}
		} catch (Exception ex) {
		}

		try {
			if (searchResult.getAttributes().get("uid") != null) {
				user.setUserId(searchResult.getAttributes().get("uid").toString().replaceAll("uid:", "").replaceAll(" ", ""));
			}
		} catch (Exception ex) {
		}

		try {
			if (searchResult.getAttributes().get("mail") != null) {
				user.setUserEmail(searchResult.getAttributes().get("mail").toString().replaceAll("mail:", "").replaceAll(" ", ""));
			}
		} catch (Exception ex) {
		}

		try {
			if (searchResult.getAttributes().get("division") != null) {
				user.setUserDivCode(searchResult.getAttributes().get("division").toString().replaceAll("division:", "").replaceAll(" ", ""));
			}
		} catch (Exception ex) {
		}

		try {
			if (searchResult.getAttributes().get("currentpost") != null) {
				user.setUserDsgn(searchResult.getAttributes().get("currentpost").toString().replaceAll("currentpost:", "").replaceAll(" ", ""));
			}
		} catch (Exception ex) {
		}

		try {
			if (searchResult.getAttributes().get("substantiverank") != null) {
				user.setUserRankCode(searchResult.getAttributes().get("substantiverank").toString().replaceAll("substantiveRank:","").replaceAll("substantiverank:", "").replaceAll(" ", ""));
			}
		} catch (Exception ex) {
		}
		try {
			if (searchResult.getAttributes().get("useracctstatus") != null) {
				user.setUserStsInd(searchResult.getAttributes().get("useracctstatus").toString().replaceAll("useracctstatus:", "").replaceAll(" ", ""));
			}
		} catch (Exception ex) {
		}

		try {
			user.setLastRecTxnUserId("batchjob");
			user.setLastRecTxnTypeCode("A");
		} catch (Exception ex) {			
		}
		return user;
	}

	private LdapContext getLdapContext(HashMap<String, SysCnfg> sysCnfgHashMap) throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, sysCnfgHashMap.get(Constant.SYS_CNFG_LDAP_PRVD_URL).getSysCnfgVal());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, sysCnfgHashMap.get(Constant.SYS_CNFG_LDAP_SCR_PRPL).getSysCnfgVal());
		env.put(Context.SECURITY_CREDENTIALS, sysCnfgHashMap.get(Constant.SYS_CNFG_LDAP_SCR_CRDTL).getSysCnfgVal());
		LdapContext ctx = new InitialLdapContext(env, null);
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		return ctx;
	}
}