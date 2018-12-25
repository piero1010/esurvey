package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.SrvyEmailTmpl;

public interface SrvyEmailTmplDao {
	
	public void insertEmailTmpl(SrvyEmailTmpl srvyEmailTmpl);
	
	public int updateEmailTmpl(SrvyEmailTmpl srvyEmailTmpl);
	
	public int cloneEmailTmpl(int oldId,SrvyEmailTmpl srvyEmailTmpl);
	
	public SrvyEmailTmpl getEmailTmplByID(int srvyEmailTmplId);

	public SrvyEmailTmpl getEmailTmpl(int srvyRecId, String emailType);
	
	public ArrayList<SrvyEmailTmpl> getEmailTmplBySrvyRecId(int srvyRecId);
	
	public int getTotal();

}
