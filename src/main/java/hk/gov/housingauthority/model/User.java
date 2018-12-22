package hk.gov.housingauthority.model;

import java.sql.Timestamp;

public class User extends Record{
	private String userId;
	private String userDsgn;
	private String userName;
	private String userEmail;
	private String userDivCode;
	private String userRankCode;
	private String userStsInd;
	private String lastRecTxnUserId;
	private String lastRecTxnTypeCode;
	private Timestamp lastRecTxnDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserDsgn() {
		return userDsgn;
	}
	public void setUserDsgn(String userDsgn) {
		this.userDsgn = userDsgn;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserDivCode() {
		return userDivCode;
	}
	public void setUserDivCode(String userDivCode) {
		this.userDivCode = userDivCode;
	}
	public String getUserRankCode() {
		return userRankCode;
	}
	public void setUserRankCode(String userRankCode) {
		this.userRankCode = userRankCode;
	}
	public String getUserStsInd() {
		return userStsInd;
	}
	public void setUserStsInd(String userStsInd) {
		this.userStsInd = userStsInd;
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
	public String toString() { 
	    return    "USER_ID: " + this.userId + "'\t, USER_DSGN: '" + this.userDsgn + "'\t, "
	    		+ "USER_NAME: '" + this.userName + "'\t, USER_EMAIL: '" + this.userEmail + "'\t, "
	    		+ "USER_DIV_CODE: '" + this.userDivCode + "'\t, USER_RANK_CODE: '" + this.userRankCode + "'\t, "
				+ "USER_STS_IND: '" + this.userStsInd + "'";
	}
	public String toCoorJsonString() {
		return "[\"" + this.userId + "\",\"" + this.userDivCode + "\",\"" + this.userRankCode + "\",\"" + this.userDsgn + "\",\"" + this.userName + "\",\"" + this.userId + "\"]";
	}
	public String toJsonString() {
		return "[\"\",\"" + this.userDivCode + "\",\"" + this.userRankCode + "\",\"" + this.userDsgn + "\",\"" + this.userName + "\",\"" + this.userId + "\"]";
	}
}
