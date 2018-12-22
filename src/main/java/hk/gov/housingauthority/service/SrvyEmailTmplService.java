package hk.gov.housingauthority.service;


import java.util.ArrayList;

import hk.gov.housingauthority.model.SrvyEmailTmpl;

public interface SrvyEmailTmplService {

	public void insertEmailTmpl(SrvyEmailTmpl srvyEmailTmpl);
	
	public int updateEmailTmpl(SrvyEmailTmpl srvyEmailTmpl);
	
	public SrvyEmailTmpl getEmailTmplByID(int srvyEmailTmplId);
	
	public ArrayList<SrvyEmailTmpl> getEmailTmplBySrvyRecId(int srvyRecId);
	
	public int getTotal();
	
	public SrvyEmailTmpl getEmailTmpl(int srvyRecId, String emailType);
}
