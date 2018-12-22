package hk.gov.housingauthority.dao;

import java.util.List;

import hk.gov.housingauthority.model.SrvyTmpl;

public interface SrvyTmplDao {
	public int insertSrvyTmpl(SrvyTmpl srvyTmpl);
	public int updateSrvyTmpl(SrvyTmpl srvyTmpl);
	public int cloneSrvyTmpl(SrvyTmpl srvyTmpl);
	public SrvyTmpl getSrvyTmplByID(int srvyTmplID);
	public List<SrvyTmpl> getSrvyTmplList(SrvyTmpl srvyTmpl, String colOrder, int start, int length);
	public List<SrvyTmpl> getSrvyTmplListByCoorGrpList(List<String> coorGrpList);
	public int getRptCatBySrvyID(int srvyRecId);
}