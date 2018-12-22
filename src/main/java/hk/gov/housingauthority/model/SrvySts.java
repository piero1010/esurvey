package hk.gov.housingauthority.model;

import java.util.LinkedHashMap;

import hk.gov.housingauthority.util.Constant;

public class SrvySts {

	private Integer srvyStsId;
	private String srvyStsDesp;
	
	public String getSrvyStsDesp() {
		return srvyStsDesp;
	}
	public void setSrvyStsDesp(String srvyStsDesp) {
		this.srvyStsDesp = srvyStsDesp;
	}
	public Integer getSrvyStsId() {
		return srvyStsId;
	}
	public void setSrvyStsId(Integer srvyStsId) {
		this.srvyStsId = srvyStsId;
	}
	public String toString(){		
		return SrvySts.toLinkedHashMap().get(Integer.toString(srvyStsId));
	}
	public static LinkedHashMap<String, String> toLinkedHashMap(){
		LinkedHashMap<String, String> srvyStsMap = new LinkedHashMap<String, String>();	
		srvyStsMap.put(Integer.toString(Constant.SURVEY_STATUS_PREPARE), "Prepare");
		srvyStsMap.put(Integer.toString(Constant.SURVEY_STATUS_TRIAL_RUN), "Trial-Run");
		srvyStsMap.put(Integer.toString(Constant.SURVEY_STATUS_PUBLISHED), "Published");
		srvyStsMap.put(Integer.toString(Constant.SURVEY_STATUS_COMPLETED), "Completed");
		return srvyStsMap;
	}
}
