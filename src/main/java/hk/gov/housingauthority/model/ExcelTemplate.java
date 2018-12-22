package hk.gov.housingauthority.model;

public class ExcelTemplate {
	
	private String TO_EMAIL;
	private String IT_SERVICE_NAME;
	private String IT_SERVICE_CODE;
	private String NAME_OF_CONTRACTOR;
	
	private String userId;		//Save into ESV_SRVY_PPT
	
	public String getTO_EMAIL() {
		return TO_EMAIL;
	}
	public void setTO_EMAIL(String tO_EMAIL) {
		TO_EMAIL = tO_EMAIL;
	}
	public String getIT_SERVICE_NAME() {
		return IT_SERVICE_NAME;
	}
	public void setIT_SERVICE_NAME(String iT_SERVICE_NAME) {
		IT_SERVICE_NAME = iT_SERVICE_NAME;
	}
	public String getIT_SERVICE_CODE() {
		return IT_SERVICE_CODE;
	}
	public void setIT_SERVICE_CODE(String iT_SERVICE_CODE) {
		IT_SERVICE_CODE = iT_SERVICE_CODE;
	}
	public String getNAME_OF_CONTRACTOR() {
		return NAME_OF_CONTRACTOR;
	}
	public void setNAME_OF_CONTRACTOR(String nAME_OF_CONTRACTOR) {
		NAME_OF_CONTRACTOR = nAME_OF_CONTRACTOR;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
