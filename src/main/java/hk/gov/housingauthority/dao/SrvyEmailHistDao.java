package hk.gov.housingauthority.dao;

import java.util.ArrayList;

import hk.gov.housingauthority.model.SrvyEmailHist;

public interface SrvyEmailHistDao {
	public void insertEmailHist(SrvyEmailHist srvyEmailHist);
	public int updateEmailHist(SrvyEmailHist srvyEmailHist);
	public int deleteEmailHist(SrvyEmailHist srvyEmailHist);
	public SrvyEmailHist getEmailHistByID(int srvyEmailHistId);
	public ArrayList<SrvyEmailHist> getEmailHistListBySrvyRecId(int srvyRecId);
	public ArrayList<SrvyEmailHist> getEmailHistList(SrvyEmailHist srvyEmailHist,String colOrder, int start, int length);
	public ArrayList<SrvyEmailHist> getPendingEmailHistList(int limit);
}
