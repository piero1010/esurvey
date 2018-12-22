package hk.gov.housingauthority.model;

import java.util.HashMap;
import java.util.Map;

import hk.gov.housingauthority.util.Constant;

public class SrvyTmplFrzInd {

	private String srvyTmplFrzIndId;
	private String srvyTmplFrzIndDesp;
	
	public String getSrvyTmplFrzIndDesp() {
		return srvyTmplFrzIndDesp;
	}
	public void setSrvyTmplFrzIndDesp(String srvyTmplFrzIndDesp) {
		this.srvyTmplFrzIndDesp = srvyTmplFrzIndDesp;
	}
	public String getSrvyTmplFrzIndId() {
		return srvyTmplFrzIndId;
	}
	public void setSrvyTmplFrzIndId(String srvyTmplFrzIndId) {
		this.srvyTmplFrzIndId = srvyTmplFrzIndId;
	}
	
	public String toString(){
		Map<String, String> srvyTmplFrzIndMap = new HashMap<String, String>();
		srvyTmplFrzIndMap.put("", "");
		srvyTmplFrzIndMap.put(Constant.TEMPLATE_STATUS_ACTIVE, "Active");
		srvyTmplFrzIndMap.put(Constant.TEMPLATE_STATUS_FROZEN, "Frozen");
		return srvyTmplFrzIndMap.get(srvyTmplFrzIndId);
	}
}
