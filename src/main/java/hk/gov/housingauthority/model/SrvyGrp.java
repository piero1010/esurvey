package hk.gov.housingauthority.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class SrvyGrp {
	
	@NotNull(message = "The \"Survey Group\" is mandatory.")
	private Integer srvyGrpId;
	private String srvyGrpName;
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	
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
	public Integer getSrvyGrpId() {
		return srvyGrpId;
	}
	public void setSrvyGrpId(Integer srvyGrpId) {
		this.srvyGrpId = srvyGrpId;
	}
	public String getSrvyGrpName() {
		return srvyGrpName;
	}
	public void setSrvyGrpName(String srvyGrpName) {
		this.srvyGrpName = srvyGrpName;
	}
	@Override
	public String toString() {
		return "SrvyGrp [srvyGrpId=" + srvyGrpId + ", srvyGrpName=" + srvyGrpName + ", lastRecTxnUserId="
				+ lastRecTxnUserId + ", lastRecTxnTypeCode=" + lastRecTxnTypeCode + ", lastRecTxnDate=" + lastRecTxnDate
				+ "]";
	}



}
