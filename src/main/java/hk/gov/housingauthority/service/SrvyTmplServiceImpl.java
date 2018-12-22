package hk.gov.housingauthority.service;

import java.util.List;

import hk.gov.housingauthority.dao.SrvyTmplDao;
import hk.gov.housingauthority.model.SrvyTmpl;

public class SrvyTmplServiceImpl implements SrvyTmplService {

	private SrvyTmplDao srvyTmplDao;

	public void setSrvyTmplDao(SrvyTmplDao srvyTmplDao) {
		this.srvyTmplDao = srvyTmplDao;
	}

	@Override
	public int insertSrvyTmpl(SrvyTmpl srvyTmpl) {
		return srvyTmplDao.insertSrvyTmpl(srvyTmpl);
	}

	@Override
	public SrvyTmpl getSrvyTmplByID(int srvyTmplID) {
		return srvyTmplDao.getSrvyTmplByID(srvyTmplID);
	}

	@Override
	public int updateSrvyTmpl(SrvyTmpl srvyTmpl) {
		return srvyTmplDao.updateSrvyTmpl(srvyTmpl);
	}

	@Override
	public List<SrvyTmpl> getSrvyTmplList(SrvyTmpl srvyTmpl, String colOrder, int start, int length) {
		return srvyTmplDao.getSrvyTmplList(srvyTmpl, colOrder, start, length);
	}

	@Override
	public String srvyTmplListToJsonStr(List<SrvyTmpl> srvyTmplList) {
		String result = new String("[");
		for (int i = 0; i < srvyTmplList.size(); i++) {
			result += srvyTmplList.get(i).toJsonString();
			if (i + 1 != srvyTmplList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	public List<SrvyTmpl> getSrvyTmplListByCoorGrpList(List<String> list) {
		return srvyTmplDao.getSrvyTmplListByCoorGrpList(list);
	}

	@Override
	public int cloneSrvyTmpl(SrvyTmpl srvyTmpl) {
		return srvyTmplDao.cloneSrvyTmpl(srvyTmpl);
	}
}
