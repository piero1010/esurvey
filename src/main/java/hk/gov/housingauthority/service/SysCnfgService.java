package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.model.SysCnfg;

public interface SysCnfgService {
	public List<SysCnfg> getArrayListByGroup(String group);
	public String getVaueByCode(String code);
	public HashMap<String, SysCnfg> getHashMapByGroup(String group);
	public ArrayList<SysCnfg> getSysCnfgList(SysCnfg SysCnfg, String colOrder, int start, int length);
	public String listToJsonStr(List<SysCnfg> sysCnfgList);
	public int insertSysCnfg(SysCnfg sysCnfg);
	public int updateSysCnfg(SysCnfg sysCnfg);
	public int deleteSysCnfg(SysCnfg sysCnfg);
}
