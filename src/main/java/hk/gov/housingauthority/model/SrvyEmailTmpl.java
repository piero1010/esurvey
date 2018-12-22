package hk.gov.housingauthority.model;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class SrvyEmailTmpl {
	private Integer srvyEmailTmplId;
	
	@NotNull
	private Integer srvyRecId;
	
	@NotEmpty(message = "The \"Template Type\" is mandatory.")
	private String emailType;
	
	@NotEmpty(message = "The \"Subject\" is mandatory.")  
	private String emailSubj;
	
	@NotEmpty(message = "The \"Content\" is mandatory.")
	private String emailCntn;
	
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	
	public Integer getSrvyEmailTmplId() {
		return srvyEmailTmplId;
	}
	public void setSrvyEmailTmplId(Integer srvyEmailTmplId) {
		this.srvyEmailTmplId = srvyEmailTmplId;
	}
	public Integer getSrvyRecId() {
		return srvyRecId;
	}
	public void setSrvyRecId(Integer srvyRecId) {
		this.srvyRecId = srvyRecId;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
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
	@Override
	public String toString() {
		return "SrvyEmailTmpl [srvyEmailTmplId=" + srvyEmailTmplId + ", srvyRecId=" + srvyRecId + ", emailType="
				+ emailType + ", emailSubj=" + emailSubj + ", emailCntn=" + emailCntn + ", lastRecTxnUserId="
				+ lastRecTxnUserId + ", lastRecTxnTypeCode=" + lastRecTxnTypeCode + ", lastRecTxnDate=" + lastRecTxnDate
				+ "]";
	}

	


}
