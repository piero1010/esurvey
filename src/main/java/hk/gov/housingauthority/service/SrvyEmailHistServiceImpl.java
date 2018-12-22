package hk.gov.housingauthority.service;

import java.util.ArrayList;

import hk.gov.housingauthority.dao.SrvyEmailHistDao;
import hk.gov.housingauthority.model.SrvyEmailHist;

public class SrvyEmailHistServiceImpl implements SrvyEmailHistService {

	private SrvyEmailHistDao srvyEmailHistDao;

	public void setSrvyEmailHistDao(SrvyEmailHistDao srvyEmailHistDao) {
		this.srvyEmailHistDao = srvyEmailHistDao;
	}
	
	public void insertEmailHist(SrvyEmailHist srvyEmailHist){
		srvyEmailHistDao.insertEmailHist( srvyEmailHist);
	}
	
	public int updateEmailHist(SrvyEmailHist srvyEmailHist){
		return srvyEmailHistDao.updateEmailHist( srvyEmailHist);
	}
	
	public SrvyEmailHist getEmailHistByID(int srvyEmailHistId){
		return srvyEmailHistDao.getEmailHistByID( srvyEmailHistId);
	}
	 
	public ArrayList<SrvyEmailHist> getEmailHistListBySrvyRecId(int srvyRecId){
		return srvyEmailHistDao.getEmailHistListBySrvyRecId( srvyRecId);
	}

	@Override
	public ArrayList<SrvyEmailHist> getEmailHistList(SrvyEmailHist srvyEmailHist, String colOrder, int start, int length) {
		return srvyEmailHistDao.getEmailHistList(srvyEmailHist,colOrder, start, length);
	}

	@Override
	public String listToJsonStr(ArrayList<SrvyEmailHist> srvyEmailHistList) {
		String result = new String("[");
		for (int i = 0; i < srvyEmailHistList.size(); i++) {
			result += srvyEmailHistList.get(i).toJsonString();
			if (i + 1 != srvyEmailHistList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	public int deleteEmailHist(SrvyEmailHist srvyEmailHist) {
		return srvyEmailHistDao.deleteEmailHist(srvyEmailHist);
	}

	@Override
	public ArrayList<SrvyEmailHist> getPendingEmailHistList(int limit) {
		return srvyEmailHistDao.getPendingEmailHistList(limit);
	}
}
