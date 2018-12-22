package hk.gov.housingauthority.model;

public class SrvyCode extends Record{
	
	private Integer srvyCodeId;
	private Integer srvyRecId;
	private String codeName;
	private String codeDesc;

	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public Integer getSrvyCodeId() {
		return srvyCodeId;
	}
	public void setSrvyCodeId(Integer srvyCodeId) {
		this.srvyCodeId = srvyCodeId;
	}
	public String toJsonString() {
		return "[\"" + srvyCodeId + "\",\"" + codeName + "\",\"" + codeDesc + "\"]";
	}
}
