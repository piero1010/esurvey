package hk.gov.housingauthority.service;

import java.util.ArrayList;
import java.util.Map;

import hk.gov.housingauthority.model.SrvyGrp;

public interface SrvyGrpService {
	public ArrayList<SrvyGrp> getSrvyGrpList();
	public Map<String, String> getSrvyGrpHashMap();
}
