package hk.gov.housingauthority.util;


public class Constant {
	public static final String RESERVED_CODE_PARTICIPANT_NAME = "PARTICIPANT_NAME";
	public static final String RESERVED_CODE_PARTICIPANT_DESIGNATION = "PARTICIPANT_DESIGNATION";
	public static final String RESERVED_CODE_PARTICIPANT_DIVISION = "PARTICIPANT_DIVISION";
	public static final String RESERVED_CODE_SURVEY_URL = "SURVEY_URL";
	
	public static final int SURVEY_STATUS_PREPARE = 1;
	public static final int SURVEY_STATUS_TRIAL_RUN = 2;
	public static final int SURVEY_STATUS_PUBLISHED = 3;
	public static final int SURVEY_STATUS_COMPLETED = 4;

	public static final String TEMPLATE_STATUS_ACTIVE = "A";
	public static final String TEMPLATE_STATUS_FROZEN = "F";
	
	public static final int SURVEY_PARTICIPANT_CATEGORY_WHOLE_DIVISION = 1;
	public static final int SURVEY_PARTICIPANT_CATEGORY_PARTICIPANT_LIST = 2;
	public static final int SURVEY_PARTICIPANT_APP_SUPPORT_PARTICIPANT_LIST = 3;
	
	public static final int REPORT_CATEGORY_BAROMETER = 1;				//DCD
	public static final int REPORT_CATEGORY_TRAINING_OUTSIDE = 2;		// T&DC
	public static final int REPORT_CATEGORY_PROJECT_EVALUATION = 3;
	public static final int REPORT_CATEGORY_MANAGEMENT_BRIEFING = 4;	//Team19
	public static final int REPORT_CATEGORY_TRAINING_EVALUATION= 5;
	public static final int REPORT_CATEGORY_APP_SUPPORT = 6;			//Team38
	public static final int REPORT_CATEGORY_IT_SUPPORT = 7;

	public static final int RETENTION_PERIOD_BAROMETER = 2;				//DCD
	public static final int RETENTION_PERIOD_TRAINING_OUTSIDE = 1;		// T&DC
	public static final int RETENTION_PERIOD_PROJECT_EVALUATION = 1;
	public static final int RETENTION_PERIOD_MANAGEMENT_BRIEFING = 1;	//Team19
	public static final int RETENTION_PERIOD_TRAINING_EVALUATION= 1;
	public static final int RETENTION_PERIOD_APP_SUPPORT = 3;			//Team38
	public static final int RETENTION_PERIOD_IT_SUPPORT = 3;
		
	public static final String LAST_REC_TXN_TYPE_CODE_ACTIVE = "A";
	public static final String LAST_REC_TXN_TYPE_CODE_UPDATE = "U";
	public static final String LAST_REC_TXN_TYPE_CODE_DELETED = "D";

	public static final String EMAIL_TYPE_INVITATION = "I";
	public static final String EMAIL_TYPE_REMINDER = "R";
	public static final String EMAIL_INVITATION_TEMPLATE = "EIT";
	public static final String EMAIL_REMINDER_TEMPLATE  = "ERT";

	public static final String SEND_STATUS_WAITING_FOR_SEND = "W";
	public static final String SEND_STATUS_SUCCESS = "S";
	public static final String SEND_STATUS_FAIL = "F";

	public static final String SYS_CNFG_LDAP_BASE = "LDAP_BASE";
	public static final String SYS_CNFG_LDAP_PORT = "LDAP_PORT ";
	public static final String SYS_CNFG_LDAP_PRVD_URL = "LDAP_PRVD_URL";
	public static final String SYS_CNFG_LDAP_SCR_CRDTL = "LDAP_SCR_CRDTL";
	public static final String SYS_CNFG_LDAP_SCR_PRPL = "LDAP_SCR_PRPL";
	public static final String SYS_CNFG_SMTP_HSTNM = "SMTP_HSTNM";
	public static final String SYS_CNFG_SMTP_EMAIL = "SMTP_EMAIL";
	public static final String SYS_CNFG_SMTP_MAX_EMAIL_SEND = "SMTP_MAX_EMAIL_SEND";

	public static final int SSN_WARNING_INTERVAL = 1500;//second
	public static final int SSN_EXPIRY_INTERVAL = 1800;//second
	
	public static final String SSN_USER_ID = "SSN_USER_ID";
	public static final String SSN_USER_EMAIL = "SSN_USER_EMAIL";
	public static final String SSN_USER_SRVY_GRP_STR = "SSN_USER_SRVY_GRP_STR";
	public static final String SSN_USER_SRVY_GRP_ARR = "SSN_USER_SRVY_GRP_ARR";
	public static final String SSN_IS_SRVY_COOR = "SSN_IS_SRVY_COOR";
	public static final String SSN_IS_SYS_ADMIN = "SSN_IS_SYS_ADMIN";
	public static final String SSN_LAST_TXN_DATETIME = "SSN_LAST_TXN_DATETIME";
}
