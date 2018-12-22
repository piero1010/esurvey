package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.List;

import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyRec;

public interface SrvyPptService {

	public SrvyPpt getSrvyPptById(int srvypPTId);
	public void insertSrvyPpt(SrvyPpt srvyPpt);
	public void insertSrvyPpt(int srvyRecId, String users);
	public void insertSrvyPptByDivision(int srvyRecId, String divCode);
	public void deleteSrvyPpt(int srvyRecId, String users);
	public int updateSrvyPpt(SrvyPpt srvyPpt);
	public int cloneSrvyPpt(SrvyPpt srvyPpt,Integer newSrvyRecId);
	public int updateResp(SrvyPpt srvyPpt);
	public void deleteSrvyPptById(int srvyPptId);
	public ArrayList<SrvyPpt> getSrvyPptListBySrvyRecId(int srvyRecId);
	public ArrayList<SrvyPpt> getSrvyPptList(SrvyPpt srvyPpt,String colOrder, int start, int length);
	public int insertUpdateSrvyPptList(ArrayList<SrvyPpt> srvyPptList);
	public void deleteSrvyPptBySrvyRecId(int srvyRecId);
	public String listToJsonStr(List<SrvyPpt> srvyPptList);
	public ArrayList<SrvyPpt> getSrvyPpt(String srvyPptIds);
	public int deleteSrvyPpt(SrvyPpt srvyPpt);
	public int getSrvyTmplIdBySrvyPptID(int srvyPptId) ;

}
