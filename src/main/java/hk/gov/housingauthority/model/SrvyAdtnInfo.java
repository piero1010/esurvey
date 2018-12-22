package hk.gov.housingauthority.model;

public class SrvyAdtnInfo extends Record{
	
	private Integer srvyAdtnInfoId;
	private Integer srvyRecId;
	private String adtnInfoName;
	private String adtnInfoDesc;

	public String getAdtnInfoName() {
		return adtnInfoName;
	}
	public void setAdtnInfoName(String adtnInfoName) {
		this.adtnInfoName = adtnInfoName;
	}
	public String getAdtnInfoDesc() {
		return adtnInfoDesc;
	}
	public void setAdtnInfoDesc(String adtnInfoDesc) {
		this.adtnInfoDesc = adtnInfoDesc;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public Integer getSrvyAdtnInfoId() {
		return srvyAdtnInfoId;
	}
	public void setSrvyAdtnInfoId(Integer srvyAdtnInfoId) {
		this.srvyAdtnInfoId = srvyAdtnInfoId;
	}
	public String toJsonString() {
		return "[\"" + srvyAdtnInfoId + "\",\"" + adtnInfoName + "\",\"" + adtnInfoDesc + "\"]";
	}
}
