package hk.gov.housingauthority.model;

import java.util.Date;

public class QueryMySrvyRec {

	private String pptUserId;
    private Date qryDate;
	
	private String coorId;	
	private Integer srvyYear;
	private String srvyTtl;
	private Integer srvySts;
	private String respSts;
	
	public String getPptUserId() {
		return pptUserId;
	}
	public void setPptUserId(String pptUserId) {
		this.pptUserId = pptUserId;
	}
	
	public Date getQryDate() {
		return qryDate;
	}
	public void setQryDate(Date qryDate) {
		this.qryDate = qryDate;
	}
	public String getCoorId() {
		return coorId;
	}
	public void setCoorId(String coorId) {
		this.coorId = coorId;
	}
	public Integer getSrvyYear() {
		return srvyYear;
	}
	public void setSrvyYear(Integer srvyYear) {
		this.srvyYear = srvyYear;
	}
	public String getSrvyTtl() {
		return srvyTtl;
	}
	public void setSrvyTtl(String srvyTtl) {
		this.srvyTtl = srvyTtl;
	}
	public Integer getSrvySts() {
		return srvySts;
	}
	public void setSrvySts(Integer srvySts) {
		this.srvySts = srvySts;
	}
	public String getRespSts() {
		return respSts;
	}
	public void setRespSts(String respSts) {
		this.respSts = respSts;
	}
	@Override
	public String toString() {
		return "QueryMySrvyRec [pptUserId=" + pptUserId + ", qryDate=" + qryDate + ", coorId=" + coorId + ", srvyYear="
				+ srvyYear + ", srvyTtl=" + srvyTtl + ", srvySts=" + srvySts + ", respSts=" + respSts + "]";
	}

	

	



}
