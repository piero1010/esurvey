package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.MySrvy;
import hk.gov.housingauthority.model.SrvySts;
import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.model.User;
import hk.gov.housingauthority.util.CommonFunction;
import hk.gov.housingauthority.util.Constant;

public class MySrvyDaoImpl implements MySrvyDao {
	
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<MySrvy> getMySrvyList(MySrvy mySrvy, String colOrder, int start, int length) {
		
		Connection conn = null;
		ResultSet rs = null;
		String filter = "";
		/* list template that user has right to access only */
		filter += " AND ESP.PPT_USER_ID = '" + CommonFunction.getSsnUserId()  + "'";
		if (mySrvy.getCoor().getUserId() != null) {
			filter += " AND ESR.COOR_USER_ID LIKE '" + mySrvy.getCoor().getUserId() + "'";
		}
		if (mySrvy.getSrvyTtl() != null) {
			filter += " AND ESR.SRVY_TTL LIKE '" + mySrvy.getSrvyTtl() + "'";
		}
		if (mySrvy.getSbmtSts() != null) {
			if ("N".equals(mySrvy.getSbmtSts())) {
				filter += " AND ESP.SBMT_STS is null";
			}else{
				filter += " AND ESP.SBMT_STS = 'Y'";
			}
		}
		if("".equals(colOrder)){
			colOrder = " ORDER BY SRVY_PPT_ID DESC ";
		}else{
			colOrder = " ORDER BY "+colOrder;
		}
		
		List<MySrvy> mySrvyList = new ArrayList<MySrvy>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_,ESR.SRVY_STS "
				+ ", ESP.SRVY_PPT_ID, ESP.SRVY_REC_ID, ESR.COOR_USER_ID, ESR.SRVY_TTL, ESP.IT_SRVC_CODE "
				+ ", ESR.BGN_DATE, ESR.END_DATE, ESP.SBMT_DATE, ESP.SBMT_STS, ESP.LAST_REC_TXN_USER_ID "
				+ ", ESP.LAST_REC_TXN_TYPE_CODE, ESP.LAST_REC_TXN_DATE"
				+ " FROM [dbo].[ESV_SRVY_PPT] AS ESP"
				+ " LEFT JOIN [dbo].[ESV_SRVY_REC] AS ESR on ESP.SRVY_REC_ID = ESR.SRVY_REC_ID"
				+ " WHERE ESR.SRVY_STS in(" + Constant.SURVEY_STATUS_PUBLISHED+"," + Constant.SURVEY_STATUS_TRIAL_RUN+") AND ESP.LAST_REC_TXN_TYPE_CODE!='D'"
				+ " AND (getdate() between ESR.BGN_DATE and ESR.END_DATE) "
				+ filter;
		String sql = "SELECT * FROM ("+condition+") AS PAGE WHERE _ROW_ BETWEEN ? AND ? "+colOrder;
		
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				mySrvyList.add(this.getMySrvyFromResultSet(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return mySrvyList;
	}
	
	public MySrvy getMySrvyFromResultSet(ResultSet rs) throws SQLException{
		MySrvy mySrvy = new MySrvy();
		try {
			mySrvy.setTotRec(rs.getInt("_TOTAL_"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setSrvyPptId(rs.getInt("SRVY_PPT_ID"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.getSrvySts().setSrvyStsId(rs.getInt("SRVY_STS"));
		} catch (Exception ex) {
		}
		
		try {
			mySrvy.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.getCoor().setUserId(rs.getNString("COOR_USER_ID"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setSrvyTtl(rs.getNString("SRVY_TTL"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setItSrvcCode(rs.getNString("IT_SRVC_CODE"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setBgnDate(rs.getDate("BGN_DATE"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setEndDate(rs.getDate("END_DATE"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setSbmtDate(rs.getTimestamp("SBMT_DATE"));
		} catch (Exception ex) {
		}
		try {
			mySrvy.setSbmtSts(rs.getNString("SBMT_STS"));
		} catch (Exception ex) {
		}
		
		return mySrvy;
	}

}
