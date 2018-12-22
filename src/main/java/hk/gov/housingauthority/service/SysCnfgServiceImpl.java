package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hk.gov.housingauthority.dao.SysCnfgDao;
import hk.gov.housingauthority.model.SysCnfg;

public class SysCnfgServiceImpl implements SysCnfgService{

	private SysCnfgDao sysCnfgDao;
	
	@Override
	public List<SysCnfg> getArrayListByGroup(String group) {
		return sysCnfgDao.getArrayListByGroup(group);
	}

	@Override
	public HashMap<String, SysCnfg> getHashMapByGroup(String group) {
		return sysCnfgDao.getHashMapByGroup(group);
	}

	public SysCnfgDao getSysCnfgDao() {
		return sysCnfgDao;
	}

	public void setSysCnfgDao(SysCnfgDao sysCnfgDao) {
		this.sysCnfgDao = sysCnfgDao;
	}

	@Override
	public ArrayList<SysCnfg> getSysCnfgList(SysCnfg SysCnfg, String colOrder, int start, int length) {
		return sysCnfgDao.getSysCnfgList(SysCnfg, colOrder, start, length);
	}

	@Override
	public String listToJsonStr(List<SysCnfg> sysCnfgList) {
		String result = new String("[");
		for (int i = 0; i < sysCnfgList.size(); i++) {
			result += sysCnfgList.get(i).toJsonString();
			if (i + 1 != sysCnfgList.size()) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}

	@Override
	public int insertSysCnfg(SysCnfg sysCnfg) {
		return sysCnfgDao.insertSysCnfg(sysCnfg);
	}

	@Override
	public int updateSysCnfg(SysCnfg sysCnfg) {
		return sysCnfgDao.updateSysCnfg(sysCnfg);
	}
	
	@Override
	public int deleteSysCnfg(SysCnfg sysCnfg) {
		return sysCnfgDao.deleteSysCnfg(sysCnfg);
	}

	@Override
	public String getVaueByCode(String code) {
		return sysCnfgDao.getVaueByCode(code);
	}
}
