package hk.gov.housingauthority.service;


import java.util.ArrayList;

import hk.gov.housingauthority.dao.SrvyEmailTmplDao;
import hk.gov.housingauthority.model.SrvyEmailTmpl;

public class SrvyEmailTmplServiceImpl implements SrvyEmailTmplService {
 
	private SrvyEmailTmplDao srvyEmailTmplDao;

	public void setSrvyEmailTmplDao(SrvyEmailTmplDao srvyEmailTmplDao) {
		this.srvyEmailTmplDao = srvyEmailTmplDao;
	}
	
	public void insertEmailTmpl(SrvyEmailTmpl srvyEmailTmpl){
		srvyEmailTmplDao.insertEmailTmpl( srvyEmailTmpl);
	}
	
	public int updateEmailTmpl(SrvyEmailTmpl srvyEmailTmpl){
	    return srvyEmailTmplDao.updateEmailTmpl(srvyEmailTmpl);
	}
	
	public SrvyEmailTmpl getEmailTmplByID(int srvyEmailTmplId){
		return srvyEmailTmplDao.getEmailTmplByID(srvyEmailTmplId);
	}
	
	public ArrayList<SrvyEmailTmpl> getEmailTmplBySrvyRecId(int srvyRecId){
		return srvyEmailTmplDao.getEmailTmplBySrvyRecId(srvyRecId);
	}
	
	public int getTotal(){
		return srvyEmailTmplDao.getTotal();
	}

	@Override
	public SrvyEmailTmpl getEmailTmpl(int srvyRecId, String emailType) {
		return srvyEmailTmplDao.getEmailTmpl(srvyRecId, emailType);
	}

	@Override
	public int cloneEmailTmpl(int oldId,SrvyEmailTmpl srvyEmailTmpl) {
		return srvyEmailTmplDao.cloneEmailTmpl(oldId,srvyEmailTmpl);
	}
	

}
