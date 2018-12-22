package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.SrvyGrp;

public class SrvyGrpDaoImpl implements SrvyGrpDao {

	private DataSource dataSource;

	public ArrayList<SrvyGrp> getSrvyGrpList(){

		ArrayList<SrvyGrp> srvyGrpList  = new ArrayList<SrvyGrp>();
		Connection conn;
		ResultSet rs = null;
		String sql = "select [SRVY_GRP_ID], [SRVY_GRP_NAME], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] from [dbo].[ESV_SRVY_GRP]";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				SrvyGrp srvyGrp = new SrvyGrp();
				srvyGrp.setSrvyGrpId(rs.getInt("SRVY_GRP_ID"));
				srvyGrp.setSrvyGrpName(rs.getString("SRVY_GRP_NAME"));
				srvyGrp.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));
				srvyGrp.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));
				srvyGrp.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));
				srvyGrpList.add(srvyGrp);
			}
			conn.close();
			
			return srvyGrpList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("[SrvyGrpDao] Cannot get Survey Group List ! ");
			return null;
		}
	}

	
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
