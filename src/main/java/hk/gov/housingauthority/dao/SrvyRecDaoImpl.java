package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.QuerySrvyRec;
import hk.gov.housingauthority.model.ResultDashboard;
import hk.gov.housingauthority.model.ResultSrvyRec;
import hk.gov.housingauthority.model.SrvyRec;
import hk.gov.housingauthority.util.Constant;

public class SrvyRecDaoImpl implements SrvyRecDao {

	private DataSource dataSource;

	public int insertSrvyRec(SrvyRec srvyRec) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;

		String insertSql = "INSERT dbo.ESV_SRVY_REC ( CRE_DATE ,COOR_USER_ID ,SRVY_YEAR ,SRVY_TMPL_ID ,SRVY_TTL "
				+ ",SRVY_STS ,BGN_DATE ,END_DATE ,PPT_CATG ,PPT_DIV_CODE ,AUTO_RMDR_DATE ,SRVY_RMK "
				+ ", LAST_REC_TXN_USER_ID, LAST_REC_TXN_TYPE_CODE, LAST_REC_TXN_DATE) "

				+ "VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?)";

		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setObject(1, srvyRec.getCreDate());
			preparedStatement.setNString(2, srvyRec.getCoor().getUserId());
			preparedStatement.setInt(3, srvyRec.getSrvyYear());
			preparedStatement.setInt(4, srvyRec.getSrvyTmpl().getSrvyTmplId());
			preparedStatement.setNString(5, srvyRec.getSrvyTtl());

			preparedStatement.setInt(6, srvyRec.getSrvySts().getSrvyStsId());
			preparedStatement.setObject(7, srvyRec.getBgnDate());
			preparedStatement.setObject(8, srvyRec.getEndDate());
			preparedStatement.setInt(9, srvyRec.getSrvyPptCatg().getSrvyPptCatgId());
			preparedStatement.setNString(10, srvyRec.getPptDivCode());

			preparedStatement.setObject(11, srvyRec.getAutoRmdrDate());
			preparedStatement.setNString(12, srvyRec.getSrvyRmk());

			// todo
			preparedStatement.setNString(13, srvyRec.getLastRecTxnUserId());
			preparedStatement.setNString(14, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE); /* A - Add, U - Update, D - Deleted */
			preparedStatement.setObject(15, new Date());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return last_inserted_id;
	}

	public int updateSrvyRec(SrvyRec srvyRec) {

		// fail return = 0
		PreparedStatement preparedStatement = null;
		Connection conn;

		String updateSql = "UPDATE dbo.ESV_SRVY_REC SET "
				+ "SRVY_YEAR = ? ,SRVY_TMPL_ID = ? ,SRVY_TTL = ? ,"
				+ "SRVY_STS = ? ,BGN_DATE = ? ,END_DATE = ? ,"
				+ "PPT_CATG = ? ,PPT_DIV_CODE = ? ,AUTO_RMDR_DATE = ? ,"
				+ "SRVY_RMK = ? ,LAST_REC_TXN_USER_ID = ?, LAST_REC_TXN_TYPE_CODE = ? , "
				+ "LAST_REC_TXN_DATE = ? "
				+ "WHERE SRVY_REC_ID = ? ";
		int intResults = 0;
		try {
			conn = dataSource.getConnection();

			preparedStatement = conn.prepareStatement(updateSql);
			try {
				preparedStatement.setInt(1, srvyRec.getSrvyYear());
				preparedStatement.setInt(2, srvyRec.getSrvyTmpl().getSrvyTmplId());
				preparedStatement.setNString(3, srvyRec.getSrvyTtl());
				preparedStatement.setInt(4, srvyRec.getSrvySts().getSrvyStsId());
				preparedStatement.setObject(5, srvyRec.getBgnDate());
				preparedStatement.setObject(6, srvyRec.getEndDate());
				preparedStatement.setInt(7, srvyRec.getSrvyPptCatg().getSrvyPptCatgId());
				preparedStatement.setNString(8, srvyRec.getPptDivCode());
				preparedStatement.setObject(9, srvyRec.getAutoRmdrDate());
				preparedStatement.setNString(10, srvyRec.getSrvyRmk());
				preparedStatement.setNString(11, srvyRec.getLastRecTxnUserId());
				preparedStatement.setNString(12, Constant.LAST_REC_TXN_TYPE_CODE_UPDATE);
				preparedStatement.setObject(13, new Date());
				preparedStatement.setInt(14, srvyRec.getSrvyRecId());

				intResults = preparedStatement.executeUpdate();

			} catch (Exception ex) {
				System.out.println("SrvyRecDao Cannot Update : " + srvyRec.getSrvyRecId());
			}
			conn.close();
			return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList<SrvyRec> getTotPpt(ArrayList<SrvyRec> srvyRecList){
		Connection conn = null;
		ResultSet rs = null;
		String list="";
		for(SrvyRec srvyRec:srvyRecList){
			list += srvyRec.getSrvyRecId()+",";
		}
		 if (!"".equals(list)) {
			 list = list.substring(0, list.length() - 1);
	    }
		String sql = "SELECT ESP.SRVY_REC_ID, "
				+ "ISNULL(SUM(CASE WHEN ESP.SBMT_STS='Y' THEN 1 END),0) AS NUM_OF_RESPD, "
				+ "ISNULL(COUNT(*),0) AS NUM_OF_PTCL FROM ESV_SRVY_PPT ESP "
				+ "WHERE ESP.SRVY_REC_ID IN ("+list+") AND ESP.LAST_REC_TXN_TYPE_CODE!=? "
				+ "GROUP BY ESP.SRVY_REC_ID";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setNString(1, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			rs = preparedStatement.executeQuery();
			if (rs.isBeforeFirst()){
				while (rs.next()){
					for(SrvyRec srvyRec:srvyRecList){
						if(srvyRec.getSrvyRecId().equals(rs.getInt("SRVY_REC_ID"))){
							srvyRec.setNumOfPtcl(rs.getInt("NUM_OF_PTCL"));
							srvyRec.setNumOfRespd(rs.getInt("NUM_OF_RESPD"));
						}
					}
				}
			}
			for(SrvyRec srvyRec:srvyRecList){
				if(srvyRec.getNumOfPtcl()==null){
					srvyRec.setNumOfPtcl(0);
				}
				if(srvyRec.getNumOfRespd()==null){
					srvyRec.setNumOfRespd(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyRecList;
	}
	
	public SrvyRec getSrvyRecByID(int srvyRecId) {

		Connection conn;
		ResultSet rs = null;
		SrvyRec srvyRec = null;
		String sql = "SELECT ESR.SRVY_REC_ID ,ESR.CRE_DATE ,ESR.COOR_USER_ID ,ESR.SRVY_YEAR ,ESR.SRVY_TMPL_ID ,ESR.SRVY_TTL "
				+ ",ESR.SRVY_STS ,ESR.BGN_DATE ,ESR.END_DATE ,ESR.AUTO_RMDR_DATE, ESR.PPT_CATG ,ESR.PPT_DIV_CODE ,ESR.AUTO_RMDR_DATE ,ESR.SRVY_RMK "
				+ ",ESR.LAST_REC_TXN_USER_ID, ESR.LAST_REC_TXN_TYPE_CODE, ESR.LAST_REC_TXN_DATE, EST.SRVY_TMPL_NAME "
				+ "FROM ESV_SRVY_REC ESR "
				+ "LEFT JOIN ESV_SRVY_TMPL EST ON EST.SRVY_TMPL_ID=ESR.SRVY_TMPL_ID "
				+ "WHERE ESR.SRVY_REC_ID = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyRec = getSrvyRec(rs);
			}
			conn.close();

			return srvyRec;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SrvyRecDao Cannot get Survey Record : " + srvyRecId);
			return null;
		}
	}

	public ArrayList<ResultSrvyRec> searchSrvyRec(QuerySrvyRec querySrvyRec) {
		Connection conn;
		ResultSrvyRec resultSrvyRec = null;
		ResultSet rs = null;
		ArrayList<ResultSrvyRec> resultList = new ArrayList<ResultSrvyRec>();

		// may change to add DCD logic , * no participant list for Division
		String SelectSql = "SELECT r.SRVY_REC_ID ,r.CRE_DATE ,r.COOR_USER_ID ,r.SRVY_YEAR ,r.SRVY_TMPL_ID "
				+ ",t.SRVY_TMPL_NAME ,r.SRVY_STS ,r.BGN_DATE ,r.END_DATE ,r.PPT_CATG "
				+ "FROM dbo.ESV_SRVY_REC r LEFT JOIN dbo.ESV_SRVY_TMPL t ON r.SRVY_TMPL_ID = t.SRVY_TMPL_ID "
				+ "where 1=1 ";

		if (querySrvyRec.getBgnFmDate() != null) {
			if (querySrvyRec.getBgnToDate() != null) {
				SelectSql += " and (r.BGN_DATE between ? and ?)";
			} else {
				SelectSql += " and r.BGN_DATE >= ?";
			}
		} else {
			if (querySrvyRec.getBgnToDate() != null) {
				SelectSql += " and r.BGN_DATE <= ?";
			}
		}
		if (querySrvyRec.getSrvyYear() != null) {
			SelectSql += " and r.SRVY_YEAR = ?";
		}
		if (querySrvyRec.getCoorId() != null) {
			SelectSql += " and r.COOR_USER_ID like ?";
		}

		if (querySrvyRec.getSrvyTmplName() != null) {
			SelectSql += " and t.SRVY_TMPL_NAME like ?";
		}
		if (querySrvyRec.getSrvySts() != null) {
			SelectSql += " and r.SRVY_STS = ?";
		}

		System.out.println("sql " + SelectSql);

		try {
			conn = dataSource.getConnection();
			int i = 0;

			PreparedStatement preparedStatement = conn.prepareStatement(SelectSql);

			if (querySrvyRec.getBgnFmDate() != null) {
				if (querySrvyRec.getBgnToDate() != null) {
					i++;
					preparedStatement.setObject(i, querySrvyRec.getBgnFmDate());
					i++;
					preparedStatement.setObject(i, querySrvyRec.getBgnToDate());
				} else {
					i++;
					preparedStatement.setObject(i, querySrvyRec.getBgnFmDate());
				}
			} else {
				i++;
				preparedStatement.setObject(i, querySrvyRec.getBgnToDate());
			}
			if (querySrvyRec.getSrvyYear() != null) {
				i++;
				preparedStatement.setInt(i, querySrvyRec.getSrvyYear());
			}
			if (querySrvyRec.getCoorId() != null) {
				i++;
				preparedStatement.setString(i, querySrvyRec.getCoorId());
			}

			if (querySrvyRec.getSrvyTmplName() != null) {
				i++;
				preparedStatement.setString(i, querySrvyRec.getSrvyTmplName());
			}
			if (querySrvyRec.getSrvySts() != null) {
				i++;
				preparedStatement.setInt(i, querySrvyRec.getSrvySts());
			}

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				resultSrvyRec = new ResultSrvyRec();
				resultSrvyRec.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
				resultSrvyRec.setCreDate(rs.getDate("CRE_DATE"));
				resultSrvyRec.setCoorId(rs.getNString("COOR_USER_ID"));
				resultSrvyRec.setSrvyYear(rs.getInt("SRVY_YEAR"));
				resultSrvyRec.setSrvyTmplId(rs.getInt("SRVY_TMPL_ID"));
				resultSrvyRec.setSrvyTmplName(rs.getNString("SRVY_TMPL_NAME"));
				resultSrvyRec.setSrvySts(rs.getInt("SRVY_STS"));
				resultSrvyRec.setBgnDate(rs.getDate("BGN_DATE"));
				resultSrvyRec.setEndDate(rs.getDate("END_DATE"));
				resultSrvyRec.setPptCatg(rs.getInt("PPT_CATG"));
				resultList.add(resultSrvyRec);
			}
			conn.close();

			return resultList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SrvyRecDao Search Survey Error  " + querySrvyRec.toString());
			return null;
		}
	}


	public int getTotal() {
		int total = 0;
		Connection conn;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM ESV_SRVY_REC";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
			conn.close();
		} catch (Exception e) {
			return total;
		}
		return total;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public ArrayList<SrvyRec> getSrvyRecList(SrvyRec srvyRec, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter = " 1=1 ";

		if (srvyRec.getStartDateFrom() != null) {
			filter += " AND BGN_DATE >= '" + srvyRec.getStartDateFrom() + "'";
		}
		if (srvyRec.getStartDateTo() != null) {
			filter += " AND BGN_DATE <= '" + srvyRec.getStartDateTo() + "'";
		}
		if (srvyRec.getSrvyTtl() != null) {
			filter += " AND SRVY_TTL LIKE '" + srvyRec.getSrvyTtl() + "'";
		}
		if (srvyRec.getCoor().getUserDsgn() != null) {
			filter += " AND USER_DSGN LIKE '" + srvyRec.getCoor().getUserDsgn() + "'";
		}
		if (srvyRec.getSrvyYear() != null) {
			filter += " AND SRVY_YEAR = '" + srvyRec.getSrvyYear() + "'";
		}
		if (srvyRec.getSrvyTmpl().getSrvyTmplId() != null) {
			filter += " AND ESR.SRVY_TMPL_ID = " + srvyRec.getSrvyTmpl().getSrvyTmplId();
		}
		if (srvyRec.getSrvySts().getSrvyStsId() != null) {
			filter += " AND SRVY_STS = " + srvyRec.getSrvySts().getSrvyStsId();
		}
		if(srvyRec.getFilterList().get("SSN_USER_SRVY_GRP")!=null){ /* list surveys in survey group only */
			filter+=" AND EST.SRVY_GRP_ID in ("+srvyRec.getFilterList().get("SSN_USER_SRVY_GRP")+")";
		}
		if ("".equals(colOrder)) {
			colOrder = " ORDER BY SRVY_REC_ID DESC ";
		} else {
			colOrder = " ORDER BY " + colOrder;
		}

		ArrayList<SrvyRec> srvyRecList = new ArrayList<SrvyRec>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder
				+ ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, ESR.*,EU.USER_DSGN,EST.SRVY_TMPL_NAME "
				+ "FROM dbo.ESV_SRVY_REC AS ESR " + "INNER JOIN ESV_USER EU ON EU.USER_ID=ESR.COOR_USER_ID "
				+ "INNER JOIN ESV_SRVY_TMPL EST ON EST.SRVY_TMPL_ID=ESR.SRVY_TMPL_ID " + "WHERE " + filter;
		String sql = "SELECT * FROM (" + condition + ") AS PAGE WHERE _ROW_ BETWEEN ? AND ? " + colOrder;

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyRecList.add(this.getSrvyRec(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyRecList;
	}

	public int UpdateCmpltSts() {
		int intResults=0;
		Connection conn = null;

		// may change to add DCD logic , * no participant list for Division
		String sql = "UPDATE ESV_SRVY_REC SET SRVY_STS = " + Constant.SURVEY_STATUS_COMPLETED
				+ " ,LAST_REC_TXN_USER_ID = 'batchjob'" + " ,LAST_REC_TXN_TYPE_CODE = "
				+ Constant.LAST_REC_TXN_TYPE_CODE_UPDATE + " ,LAST_REC_TXN_DATE = GETDATE()" + " WHERE SRVY_STS = "
				+ Constant.SURVEY_STATUS_PUBLISHED + " AND END_DATE <= GETDATE() ";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
			intResults = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return intResults;
	}

	// Search all "Published" survey, and details from startdate to (today/enddate)
	public ArrayList<ResultDashboard> getDashboardData(String srvyGrp) {
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultDashboard resultDashboard;
		Locale locale = Locale.US;

		//TODO filter survey group
		ArrayList<ResultDashboard> resultDashboardList = new ArrayList<ResultDashboard>();
		String sql = "SELECT  r.SRVY_REC_ID, r.SRVY_TTL, r.BGN_DATE, r.END_DATE "
				+ ", (select count(*) from dbo.ESV_SRVY_PPT where SRVY_REC_ID = r.SRVY_REC_ID and last_rec_txn_type_code <> 'D' and sbmt_sts = 'Y') as SBMT_CNT "
				+ ", (select count(*) from dbo.ESV_SRVY_PPT where SRVY_REC_ID = r.SRVY_REC_ID and last_rec_txn_type_code <> 'D') as PPT_CNT "
				+ "from dbo.ESV_SRVY_REC r, ESV_SRVY_TMPL t "
				+ "where r.SRVY_TMPL_ID = t.SRVY_TMPL_ID "
				+ "and dateadd(month, -1, bgn_date) < getdate() and dateadd(month, 1, end_date) > getdate() "
				+ "and t.SRVY_GRP_ID in("+srvyGrp+") ";

		String sql2;

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			PreparedStatement preparedStatement2;


			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int sbmtCnt = rs.getInt("SBMT_CNT");
				int pptCnt = rs.getInt("PPT_CNT");
				Date bgnDate = rs.getDate("BGN_DATE");
				Date endDate = rs.getDate("END_DATE");
 
				resultDashboard = new ResultDashboard();
				resultDashboard.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
				resultDashboard.setSrvyTtl(rs.getNString("SRVY_TTL"));
				resultDashboard.setBgnDate(bgnDate);
				resultDashboard.setEndDate(endDate);

				ArrayList<String> dateList = new ArrayList<String>();
				ArrayList<Integer> cntList = new ArrayList<Integer>();
				if (pptCnt > 0) {
					resultDashboard.setSbmtPct(sbmtCnt * 100 / pptCnt);
					resultDashboard.setNotSbmtPct(100 - (sbmtCnt * 100 / pptCnt));

					int notSubCnt = pptCnt;
					Date currDate = bgnDate;

					// show from survey start to end/today
					Date toDay = new Date();
					if (endDate.after(toDay)) {
						endDate = toDay;
					}

					long diff = endDate.getTime() - bgnDate.getTime();
					int dayCnt = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

					// one day before bgn_date
					currDate = new Date(bgnDate.getTime() - 24 * 60 * 60 * 1000);
					for (int i = -1; i <= dayCnt; i++) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",locale);
						dateList.add("\""+sdf.format(currDate)+"\"");

						//  sbmt_date <= ( bgn_date 00:00:00)         -> 0
						//  sbmt_date <= ( bgn_date 00:00:00 + 1 day) -> Cnt
						currDate = new Date(currDate.getTime() + 24 * 60 * 60 * 1000);
						
						// bug ??
						// currDate = new Date(bgnDate.getTime() + (i+1) * 24 * 60 * 60 * 1000);
						
						sql2 = "select count(*) as cnt from ESV_SRVY_PPT "
								+ "where last_rec_txn_type_code <> 'D' and SRVY_REC_ID = ? and SBMT_DATE <=  ? ";

						preparedStatement2 = conn.prepareStatement(sql2);
						preparedStatement2.setInt(1, resultDashboard.getSrvyRecId());
						preparedStatement2.setObject(2, currDate);
						rs2 = preparedStatement2.executeQuery();
						if (rs2.next()) {
							notSubCnt = pptCnt - rs2.getInt("cnt");
						}
						cntList.add(notSubCnt);
					}

				} else {
					resultDashboard.setSbmtPct(0);
					resultDashboard.setNotSbmtPct(100);
				}

				resultDashboard.setSrvyDateList(dateList);
				resultDashboard.setSrvyCntList(cntList);

				resultDashboardList.add(resultDashboard);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return resultDashboardList;
	}
	
	public SrvyRec getSrvyRec(ResultSet rs){
		return getSrvyRec(rs,"");
	}
	
	public SrvyRec getSrvyRec(ResultSet rs,String prefix){
		SrvyRec srvyRec = new SrvyRec();
		if(!prefix.isEmpty()){
			prefix = prefix+"_";
		}
		try {srvyRec.setTotRec(rs.getInt("_TOTAL_"));} catch (Exception ex) {}
		try {srvyRec.setSrvyRecId(rs.getInt(prefix+"SRVY_REC_ID"));} catch (Exception ex) {}
		try {srvyRec.setSrvyTtl(rs.getNString(prefix+"SRVY_TTL"));} catch (Exception ex) {}
		try {srvyRec.setCreDate(rs.getTimestamp(prefix+"CRE_DATE"));} catch (Exception ex) {}
		try {srvyRec.setAutoRmdrDate(rs.getTimestamp(prefix+"AUTO_RMDR_DATE"));} catch (Exception ex) {}
		try {srvyRec.setPptDivCode(rs.getNString(prefix+"PPT_DIV_CODE"));} catch (Exception ex) {}
		try {srvyRec.getCoor().setUserDsgn(rs.getNString(prefix+"USER_DSGN"));} catch (Exception ex) {}
		try {srvyRec.getCoor().setUserId(rs.getNString(prefix+"COOR_USER_ID"));} catch (Exception ex) {}
		try {srvyRec.setSrvyYear(rs.getInt(prefix+"SRVY_YEAR"));} catch (Exception ex) {}
		try {srvyRec.getSrvyTmpl().setSrvyTmplId(rs.getInt(prefix+"SRVY_TMPL_ID"));} catch (Exception ex) {}
		try {srvyRec.getSrvyTmpl().setSrvyTmplName(rs.getNString(prefix+"SRVY_TMPL_NAME"));} catch (Exception ex) {}
		try {srvyRec.getSrvySts().setSrvyStsId(rs.getInt(prefix+"SRVY_STS"));} catch (Exception ex) {}
		try {srvyRec.setSrvyRmk(rs.getNString(prefix+"SRVY_RMK"));} catch (Exception ex) {}
		try {srvyRec.setBgnDate(rs.getTimestamp(prefix+"BGN_DATE"));} catch (Exception ex) {}
		try {srvyRec.setEndDate(rs.getTimestamp(prefix+"END_DATE"));} catch (Exception ex) {}
		try {srvyRec.getSrvyPptCatg().setSrvyPptCatgId(rs.getInt(prefix+"PPT_CATG"));} catch (Exception ex) {}
		return srvyRec;
	}

	@Override
	public SrvyRec getSrvyRecBySrvyPptId(int srvyPptId) {
		Connection conn;
		ResultSet rs = null;
		SrvyRec srvyRec = null;
		String sql = "SELECT ESR.SRVY_REC_ID ,ESR.CRE_DATE ,ESR.COOR_USER_ID ,ESR.SRVY_YEAR ,ESR.SRVY_TMPL_ID ,ESR.SRVY_TTL, "
				+ "ESR.SRVY_STS ,ESR.BGN_DATE ,ESR.END_DATE ,ESR.PPT_CATG ,ESR.PPT_DIV_CODE ,ESR.AUTO_RMDR_DATE ,ESR.SRVY_RMK, "
				+ "ESR.LAST_REC_TXN_USER_ID, ESR.LAST_REC_TXN_TYPE_CODE, ESR.LAST_REC_TXN_DATE, "
				+ "EST.SRVY_TMPL_NAME,EST.SRVY_TMPL_CNFG, "
				+ "ESP.SRVY_PPT_ID,ESP.SRVY_CNTN,ESP.IT_SRVC_CODE,ESP.IT_SRVC_NAME,ESP.CNTRCTR_NAME,ESP.SBMT_STS, "
				+ "EU.USER_NAME,EU.USER_DSGN,EU.USER_ID,EST.SRVY_TMPL_CSS "
				+ "FROM ESV_SRVY_REC ESR "
				+ "INNER JOIN ESV_SRVY_PPT ESP ON ESP.SRVY_REC_ID=ESR.SRVY_REC_ID "
				+ "INNER JOIN ESV_USER EU ON EU.USER_ID=ESP.PPT_USER_ID "
				+ "INNER JOIN ESV_SRVY_TMPL EST ON EST.SRVY_TMPL_ID=ESR.SRVY_TMPL_ID "
				+ "WHERE ESP.SRVY_PPT_ID = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyPptId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyRec = getSrvyRec(rs);
				srvyRec.setSrvyTmpl(SrvyTmplDaoImpl.getSrvyTmpl(rs));
				srvyRec.getSrvyPpt().add(SrvyPptDaoImpl.getSrvyPpt(rs));
				srvyRec.getSrvyPpt().get(0).setUser(UserDaoImpl.getUser(rs));
			}
			conn.close();
			return srvyRec;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SrvyRecDao Cannot get Survey Record : " + srvyPptId);
			return null;
		}
	}

	@Override
	public int cloneSrvyRec(SrvyRec srvyRec) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		
		String sql = "INSERT INTO [dbo].[ESV_SRVY_REC]("
				+ "[CRE_DATE],[COOR_USER_ID],"
				+ "[SRVY_YEAR],[SRVY_TMPL_ID],[SRVY_TTL],"
				+ "[SRVY_STS],[BGN_DATE],[END_DATE],"
				+ "[PPT_CATG],[PPT_DIV_CODE],[AUTO_RMDR_DATE],"
				+ "[SRVY_RMK],[LAST_REC_TXN_USER_ID],[LAST_REC_TXN_TYPE_CODE],"
				+ "[LAST_REC_TXN_DATE]) SELECT "
				+ "GETDATE(),'"+srvyRec.getLastRecTxnUserId()+"',"
				+ "[SRVY_YEAR],[SRVY_TMPL_ID],[SRVY_TTL],"
				+ "'"+Constant.SURVEY_STATUS_PREPARE+"',null,null,"
				+ "[PPT_CATG],[PPT_DIV_CODE],null,"
				+ "[SRVY_RMK],'"+srvyRec.getLastRecTxnUserId()+"','"+Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE+"',"
				+ "GETDATE() FROM [dbo].[ESV_SRVY_REC] WHERE [SRVY_REC_ID]=?";
		
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, srvyRec.getSrvyRecId());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return last_inserted_id;
	}

	/*

	public ArrayList<ResultMySrvyRec> searchMySrvyRec(QueryMySrvyRec queryMySrvyRec) {
		Connection conn;
		ResultMySrvyRec resultMySrvyRec = null;
		ResultSet rs = null;
		ArrayList<ResultMySrvyRec> mySrvyRecList = new ArrayList<ResultMySrvyRec>();

		// may change to add DCD logic , * no participant list for Division
		String SelectSql = "SELECT r.SRVY_REC_ID, p.SRVY_PPT_ID, r.COOR_USER_ID, r.SRVY_YEAR, r.SRVY_TMPL_ID, "
				+ "r.SRVY_TTL, p.IT_SRVC_CODE, r.SRVY_STS, r.BGN_DATE, r.END_DATE, p.SBMT_DATE, "
				+ "CASE WHEN p.SBMT_DATE is NULL THEN 'N' ELSE'Y' END as RESP_STS "
				+ "from dbo.ESV_SRVY_REC r LEFT OUTER JOIN dbo.ESV_SRVY_PPT p ON r.SRVY_REC_ID = p.SRVY_REC_ID "
				+ "where (getdate() between r.BGN_DATE and r.END_DATE) and p.PPT_USER_ID = ?";

		if (queryMySrvyRec.getCoorId() != null) {
			SelectSql += " and r.COOR_USER_ID like ?";
		}
		if (queryMySrvyRec.getSrvyYear() != null) {
			SelectSql += " and r.SRVY_YEAR = ?";
		}
		if (queryMySrvyRec.getSrvyTtl() != null) {
			SelectSql += " and r.SRVY_TTL like ?";
		}
		if (queryMySrvyRec.getSrvySts() != null) {
			SelectSql += " and r.SRVY_STS = ?";
		}

		if (queryMySrvyRec.getRespSts() != null) {
			if (queryMySrvyRec.getRespSts() == "N") {
				SelectSql += " and r.SBMT_DATE is null";
			} else {
				SelectSql += " and r.SBMT_DATE is not null";
			}
		}

		try {
			conn = dataSource.getConnection();
			int i = 0;

			PreparedStatement preparedStatement = conn.prepareStatement(SelectSql);
			preparedStatement.setString(++i, queryMySrvyRec.getPptUserId());
			if (queryMySrvyRec.getCoorId() != null) {
				i++;
				preparedStatement.setString(i, queryMySrvyRec.getCoorId());
			}
			if (queryMySrvyRec.getSrvyYear() != null) {
				i++;
				preparedStatement.setInt(i, queryMySrvyRec.getSrvyYear().intValue());
			}
			if (queryMySrvyRec.getSrvyTtl() != null) {
				i++;
				preparedStatement.setString(i, queryMySrvyRec.getSrvyTtl());
			}
			if (queryMySrvyRec.getSrvySts() != null) {
				i++;
				preparedStatement.setInt(i, queryMySrvyRec.getSrvySts().intValue());
			}

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				resultMySrvyRec = new ResultMySrvyRec();

				resultMySrvyRec.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
				resultMySrvyRec.setSrvyPptId(rs.getInt("SRVY_PPT_ID"));
				resultMySrvyRec.setCoorId(rs.getNString("COOR_USER_ID"));
				resultMySrvyRec.setSrvyYear(rs.getInt("SRVY_YEAR"));
				resultMySrvyRec.setSrvyTmplId(rs.getInt("SRVY_TMPL_ID"));
				resultMySrvyRec.setSrvyTtl(rs.getNString("SRVY_TTL"));
				resultMySrvyRec.setItSrvcCode(rs.getNString("IT_SRVC_CODE"));
				resultMySrvyRec.setSrvySts(rs.getInt("SRVY_STS"));
				resultMySrvyRec.setBgnDate(rs.getDate("BGN_DATE"));
				resultMySrvyRec.setEndDate(rs.getDate("END_DATE"));
				resultMySrvyRec.setSbmtDate(rs.getTimestamp("SBMT_DATE"));
				resultMySrvyRec.setRespSts(rs.getNString("RESP_STS"));

				mySrvyRecList.add(resultMySrvyRec);
			}
			conn.close();

			return mySrvyRecList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SrvyRecDao Search Survey Error  " + queryMySrvyRec.toString());
			return null;
		}
	}
			*/

}
