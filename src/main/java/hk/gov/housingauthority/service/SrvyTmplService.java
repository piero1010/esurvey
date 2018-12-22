package hk.gov.housingauthority.service;

import java.util.List;

import hk.gov.housingauthority.model.SrvyTmpl;

public interface SrvyTmplService {
	public int insertSrvyTmpl(SrvyTmpl srvyTmpl);
	public int updateSrvyTmpl(SrvyTmpl srvyTmpl);
	public int cloneSrvyTmpl(SrvyTmpl srvyTmpl);
	public SrvyTmpl getSrvyTmplByID(int srvyTmplID);
	public List<SrvyTmpl> getSrvyTmplList(SrvyTmpl srvyTmpl,String colOrder, int start, int length);
	public String srvyTmplListToJsonStr(List<SrvyTmpl> srvyTmplList);
	public List<SrvyTmpl> getSrvyTmplListByCoorGrpList(List<String> list);
	
}
