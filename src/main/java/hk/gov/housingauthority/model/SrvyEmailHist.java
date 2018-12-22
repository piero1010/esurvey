package hk.gov.housingauthority.model;

import java.sql.Timestamp;

public class SrvyEmailHist extends Record{
	
	public SrvyEmailHist(){
		this.srvyEmailSts = new SrvyEmailSts();
		this.srvyEmailType = new SrvyEmailType();
	}
	
	private Integer srvyEmailHistId;
	private Integer srvyRecId;
	private Integer srvyPptId;
	
	private SrvyEmailType srvyEmailType;
	private String emailTo;
	private String emailSubj;
	private String emailCntn;
	private Timestamp emailSendDate;
	private SrvyEmailSts srvyEmailSts;
	private Integer tryCnt;
	
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	
	private Timestamp emailSendDateFrom;
	private Timestamp emailSendDateTo;
	
	public Integer getSrvyEmailHistId() {
		return srvyEmailHistId;
	}
	public void setSrvyEmailHistId(Integer srvyEmailHistId) {
		this.srvyEmailHistId = srvyEmailHistId;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getEmailSubj() {
		return emailSubj;
	}
	public void setEmailSubj(String emailSubj) {
		this.emailSubj = emailSubj;
	}
	public String getEmailCntn() {
		return emailCntn;
	}
	public void setEmailCntn(String emailCntn) {
		this.emailCntn = emailCntn;
	}
	public Timestamp getEmailSendDate() {
		return emailSendDate;
	}
	public Integer getTryCnt() {
		return tryCnt;
	}
	public void setTryCnt(Integer tryCnt) {
		this.tryCnt = tryCnt;
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
	
	public Timestamp getEmailSendDateFrom() {
		return emailSendDateFrom;
	}
	public void setEmailSendDate(Timestamp emailSendDate) {
		this.emailSendDate = emailSendDate;
	}
	public void setEmailSendDateFrom(Timestamp emailSendDateFrom) {
		this.emailSendDateFrom = emailSendDateFrom;
	}
	public Timestamp getEmailSendDateTo() {
		return emailSendDateTo;
	}
	public void setEmailSendDateTo(Timestamp emailSendDateTo) {
		this.emailSendDateTo = emailSendDateTo;
	}

	public String toJsonString() {
		return "[\"" + (emailSendDate != null ?Record.getDatetimeFormat().format(emailSendDate):"") + "\",\"" + this.getSrvyEmailType().toString() + "\",\"" + emailTo + "\",\"" + 
		this.getSrvyEmailSts().toString() + "\",\"" + tryCnt + "\"]";
	}
	public SrvyEmailSts getSrvyEmailSts() {
		return srvyEmailSts;
	}
	public void setSrvyEmailSts(SrvyEmailSts srvyEmailSts) {
		this.srvyEmailSts = srvyEmailSts;
	}
	public SrvyEmailType getSrvyEmailType() {
		return srvyEmailType;
	}
	public void setSrvyEmailType(SrvyEmailType srvyEmailType) {
		this.srvyEmailType = srvyEmailType;
	}
	public Integer getSrvyPptId() {
		return srvyPptId;
	}
	public void setSrvyPptId(Integer srvyPptId) {
		this.srvyPptId = srvyPptId;
	}
}
