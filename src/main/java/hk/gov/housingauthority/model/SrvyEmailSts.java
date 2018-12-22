package hk.gov.housingauthority.model;

import java.util.LinkedHashMap;

import hk.gov.housingauthority.util.Constant;

public class SrvyEmailSts {

	private String srvyEmailStsId;
	private String srvyEmailStsDesp;
	
	public String getSrvyEmailStsDesp() {
		return srvyEmailStsDesp;
	}
	public void setSrvyEmailStsDesp(String srvyEmailStsDesp) {
		this.srvyEmailStsDesp = srvyEmailStsDesp;
	}
	public String getSrvyEmailStsId() {
		return srvyEmailStsId;
	}
	public void setSrvyEmailStsId(String srvyEmailStsId) {
		this.srvyEmailStsId = srvyEmailStsId;
	}
	public String toString(){
		return SrvyEmailSts.toLinkedHashMap().get(srvyEmailStsId);
	}
	public static LinkedHashMap<String, String> toLinkedHashMap(){
		LinkedHashMap<String, String> srvyEmailStsMap = new LinkedHashMap<String, String>();		
		srvyEmailStsMap.put(Constant.SEND_STATUS_WAITING_FOR_SEND, "Waiting for send");
		srvyEmailStsMap.put(Constant.SEND_STATUS_SUCCESS, "Success");
		srvyEmailStsMap.put(Constant.SEND_STATUS_FAIL, "Fail");
		return srvyEmailStsMap;
	}
}
