package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.ExcelTemplate;
import hk.gov.housingauthority.model.SrvyPpt;

public interface SrvyPptDao {
	
	public void insertSrvyPpt(SrvyPpt srvyPpt);
	public void insertSrvyPpt(int srvyRecId, String users);
	public boolean insertSrvyPptByExcel(int srvyRecId, ExcelTemplate excelRecord);
	public void insertSrvyPptByDivision(int srvyRecId, String divCode);
	public void deleteSrvyPpt(int srvyRecId, String users);
	public int updateSrvyPpt(SrvyPpt srvyPpt);
	public int updateResp(SrvyPpt srvyPpt);
	public int cloneSrvyPpt(SrvyPpt srvyPpt,Integer newSrvyRecId);
	public void deleteSrvyPptById(int srvyPptId);
	public SrvyPpt getSrvyPptById(int srvyPptId);
	public ArrayList<SrvyPpt> getSrvyPptList(SrvyPpt srvyPpt, String colOrder, int start, int length);
	public ArrayList<SrvyPpt> getSrvyPptListBySrvyRecId(int srvyRecId);
	public void deleteSrvyPptBySrvyRecId(int srvyRecId);
	public ArrayList<SrvyPpt> getRmdrSrvyPpt();
	public ArrayList<SrvyPpt> getSrvyPpt(String srvyPptIds);
	public int deleteSrvyPpt(SrvyPpt srvyPpt);
	public int getSrvyTmplIdBySrvyPptID(int srvyPptId) ;
	public int checkExistPptRecord(int srvyRecId, String userID, String srvcCode);
	public void updateSrvyPptEmailTime(int id, String type);
	
	// batch job
	public void purgeOldData();
	

}
