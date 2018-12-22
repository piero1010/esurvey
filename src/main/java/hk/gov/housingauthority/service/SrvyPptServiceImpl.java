package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.dao.SrvyPptDao;
import hk.gov.housingauthority.model.SrvyPpt;

public class SrvyPptServiceImpl implements SrvyPptService {

	private SrvyPptDao srvyPptDao;

	public void setSrvyPptDao(SrvyPptDao srvyPptDao) {
		this.srvyPptDao = srvyPptDao;
	}
	
	public void insertSrvyPpt(SrvyPpt srvyPpt){
		srvyPptDao.insertSrvyPpt( srvyPpt);
	}
	
	public int updateSrvyPpt(SrvyPpt srvyPpt){
		return srvyPptDao.updateSrvyPpt( srvyPpt);
	}
	
	public void deleteSrvyPptById(int srvyPptId){
		srvyPptDao.deleteSrvyPptById(srvyPptId);
	}
	
	public SrvyPpt getSrvyPptById(int srvypPTId){
		return srvyPptDao.getSrvyPptById(srvypPTId);
	}
	
	public int insertUpdateSrvyPptList(ArrayList<SrvyPpt> srvyPptList) {
		// 1 success, 0 fail
		int i=0;
		SrvyPpt srvyPpt;
		
		for (i = 0; i < srvyPptList.size(); i++) {
			srvyPpt = srvyPptList.get(i);
			if (srvyPpt.getSrvyPptId() == null){ 
				srvyPptDao.insertSrvyPpt(srvyPpt);
			}else{
				int intResult = srvyPptDao.updateSrvyPpt(srvyPpt);
				if (intResult!=1) return 0;
			}
		}
		return 1;
	}

	public ArrayList<SrvyPpt> getSrvyPptListBySrvyRecId(int srvyRecId) {
		return srvyPptDao.getSrvyPptListBySrvyRecId(srvyRecId);
	}

	public void deleteSrvyPptBySrvyRecId(int srvyRecId){
		srvyPptDao.deleteSrvyPptBySrvyRecId(srvyRecId);
	}
	
	@Override
	public ArrayList<SrvyPpt> getSrvyPptList(SrvyPpt srvyPpt, String colOrder, int start, int length) {
		return srvyPptDao.getSrvyPptList(srvyPpt, colOrder, start, length);
	}
	
	@Override
	public String listToJsonStr(List<SrvyPpt> srvyPptList) {
		String result = new String("[");
		for (int i = 0; i < srvyPptList.size(); i++) {
			result += srvyPptList.get(i).toJsonString();
			if (i + 1 != srvyPptList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}
	
	@Override
	public void insertSrvyPpt(int srvyRecId, String users) {
		srvyPptDao.insertSrvyPpt(srvyRecId, users);
	}

	@Override
	public void deleteSrvyPpt(int srvyRecId, String users) {
		srvyPptDao.deleteSrvyPpt(srvyRecId, users);
	}

	@Override
	public int updateResp(SrvyPpt srvyPpt) {
		return srvyPptDao.updateResp(srvyPpt);
	}

	@Override
	public int deleteSrvyPpt(SrvyPpt srvyPpt) {
		return srvyPptDao.deleteSrvyPpt(srvyPpt);
	}

	@Override
	public int cloneSrvyPpt(SrvyPpt srvyPpt, Integer newSrvyRecId) {
		return srvyPptDao.cloneSrvyPpt(srvyPpt, newSrvyRecId);
	}
	
	public int getSrvyTmplIdBySrvyPptID(int srvyPptId) {
		return srvyPptDao.getSrvyTmplIdBySrvyPptID( srvyPptId) ;
	}

	@Override
	public void insertSrvyPptByDivision(int srvyRecId, String divCode) {
		srvyPptDao.insertSrvyPptByDivision(srvyRecId,divCode);
	}

	@Override
	public ArrayList<SrvyPpt> getSrvyPpt(String srvyPptIds) {
		return srvyPptDao.getSrvyPpt(srvyPptIds);
	}	
}
