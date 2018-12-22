package hk.gov.housingauthority.model;

import java.util.LinkedHashMap;

import hk.gov.housingauthority.util.Constant;

public class SrvyEmailType {

	private String srvyEmailTypeId;
	private String srvyEmailTypeDesp;
	
	public String getSrvyEmailTypeDesp() {
		return srvyEmailTypeDesp;
	}
	public void setSrvyEmailTypeDesp(String srvyEmailTypeDesp) {
		this.srvyEmailTypeDesp = srvyEmailTypeDesp;
	}
	public String getSrvyEmailTypeId() {
		return srvyEmailTypeId;
	}
	public void setSrvyEmailTypeId(String srvyEmailTypeId) {
		this.srvyEmailTypeId = srvyEmailTypeId;
	}
	public String toString(){		
		return SrvyEmailType.toLinkedHashMap().get(srvyEmailTypeId);
	}
	public static LinkedHashMap<String, String> toLinkedHashMap(){
		LinkedHashMap<String, String> srvyEmailStsMap = new LinkedHashMap<String, String>();		
		srvyEmailStsMap.put(Constant.EMAIL_TYPE_INVITATION, "Invitation");
		srvyEmailStsMap.put(Constant.EMAIL_TYPE_REMINDER, "Reminder");
		return srvyEmailStsMap;
	}
}
