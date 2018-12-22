package hk.gov.housingauthority.model;

import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import hk.gov.housingauthority.util.Constant;

public class SrvyPptCatg {

	@NotNull(message = "The \"Participant Category\" is mandatory.")
	private Integer srvyPptCatgId;
	
	private String srvyPptCatgDesp;
	
	public String getSrvyPptCatgDesp() {
		return srvyPptCatgDesp;
	}
	public void setSrvyPptCatgDesp(String srvyPptCatgDesp) {
		this.srvyPptCatgDesp = srvyPptCatgDesp;
	}
	public Integer getSrvyPptCatgId() {
		return srvyPptCatgId;
	}
	public void setSrvyPptCatgId(Integer srvyPptCatgId) {
		this.srvyPptCatgId = srvyPptCatgId;
	}
	public String toString(){		
		return SrvyPptCatg.toLinkedHashMap().get(Integer.toString(srvyPptCatgId));
	}
	public static LinkedHashMap<String, String> toLinkedHashMap(){
		LinkedHashMap<String, String> pptCatgHashMap = new LinkedHashMap<String, String>();
		pptCatgHashMap.put(Integer.toString(Constant.SURVEY_PARTICIPANT_CATEGORY_WHOLE_DIVISION), "Whole Division");
		pptCatgHashMap.put(Integer.toString(Constant.SURVEY_PARTICIPANT_CATEGORY_PARTICIPANT_LIST), "Participant List");
		pptCatgHashMap.put(Integer.toString(Constant.SURVEY_PARTICIPANT_APP_SUPPORT_PARTICIPANT_LIST), "App. Support Participant List");
		return pptCatgHashMap;
	}
}
