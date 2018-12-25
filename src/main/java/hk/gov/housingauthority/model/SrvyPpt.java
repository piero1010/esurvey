package hk.gov.housingauthority.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SrvyPpt extends Record{
	
	public SrvyPpt(){
		this.setUser(new User());
	}
	
	private Integer srvyPptId;
	private Integer srvyRecId;
	private User user;
	private String itSrvcCode;
	private String itSrvcName;
	private String srvyCntn;
	private Timestamp invtDate;
	private Timestamp lastRmdrDate;
	private Timestamp sbmtDate;
	private String sbmtSts;
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	private String cntrctrName;
	
	public Integer getSrvyPptId() {
		return srvyPptId;
	}
	public void setSrvyPptId(Integer srvyPptId) {
		this.srvyPptId = srvyPptId;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}	
	public String getItSrvcCode() {
		return itSrvcCode;
	}
	public void setItSrvcCode(String itSrvcCode) {
		this.itSrvcCode = itSrvcCode;
	}
	public String getItSrvcName() {
		return itSrvcName;
	}
	public void setItSrvcName(String itSrvcName) {
		this.itSrvcName = itSrvcName;
	}
	public String getSrvyCntn() {
		return srvyCntn;
	}
	public void setSrvyCntn(String srvyCntn) {
		this.srvyCntn = srvyCntn;
	}
	public Date getInvtDate() {
		return invtDate;
	}
	public void setInvtDate(Timestamp invtDate) {
		this.invtDate = invtDate;
	}
	public Date getLastRmdrDate() {
		return lastRmdrDate;
	}
	public void setLastRmdrDate(Timestamp lastRmdrDate) {
		this.lastRmdrDate = lastRmdrDate;
	}
	public Timestamp getSbmtDate() {
		return sbmtDate;
	}
	public void setSbmtDate(Timestamp sbmtDate) {
		this.sbmtDate = sbmtDate;
	}
	public String getSbmtSts() {
		return sbmtSts;
	}
	public void setSbmtSts(String sbmtSts) {
		this.sbmtSts = sbmtSts;
	}
	public String getLastRecTxnUserId() {
		return lastRecTxnUserId;
	}
	public void setLastRecTxnUserId(String lastRecTxnUserId) {
		this.lastRecTxnUserId = lastRecTxnUserId;
	}
	public String getLastRecTxnTypeCode() {
		return lastRecTxnTypeCode;
	}
	public void setLastRecTxnTypeCode(String lastRecTxnTypeCode) {
		this.lastRecTxnTypeCode = lastRecTxnTypeCode;
	}
	public Timestamp getLastRecTxnDate() {
		return lastRecTxnDate;
	}
	public void setLastRecTxnDate(Timestamp lastRecTxnDate) {
		this.lastRecTxnDate = lastRecTxnDate;
	}
	public String getCntrctrName() {
		return cntrctrName;
	}
	public void setCntrctrName(String cntrctrName) {
		this.cntrctrName = cntrctrName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String toJsonString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return "[\"\",\"" + user.getUserDivCode() + "\",\"" + user.getUserName() +"\",\"" + user.getUserDsgn() + "\",\"" + user.getUserEmail() + "\",\"" + 
				(invtDate != null ?datetimeFormat.format(invtDate):"") + "\",\"" + (lastRmdrDate != null ?datetimeFormat.format(lastRmdrDate):"") + "\",\"" + (sbmtDate != null ?datetimeFormat.format(sbmtDate):"") + "\",\"" + (sbmtSts==null?"N":"Y") + "\",\"" + user.getUserId() + "\",\"" + srvyPptId + "\","
						+ "\"" + itSrvcName + "\",\"" + itSrvcCode + "\",\"" + cntrctrName + "\"]";
	}	
}
