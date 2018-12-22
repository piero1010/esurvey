package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.ExcelTemplate;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.util.Constant;

public class SrvyPptDaoImpl implements SrvyPptDao {

	private DataSource dataSource;

	public void insertSrvyPpt(int srvyRecId, String users) {
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		preparedStatement = null;
		String insertSql = "INSERT INTO ESV_SRVY_PPT (PPT_USER_ID,SRVY_REC_ID,LAST_REC_TXN_TYPE_CODE) "
				+ "SELECT USER_ID,?,'" + Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE + "' from ESV_USER where USER_ID in (" + users + ") "
				+ "EXCEPT "
				+ "SELECT PPT_USER_ID,SRVY_REC_ID,LAST_REC_TXN_TYPE_CODE from ESV_SRVY_PPT ";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public boolean insertSrvyPptByExcel(int srvyRecId, ExcelTemplate excelRecord) {
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		preparedStatement = null;

		String insertSql = "INSERT INTO ESV_SRVY_PPT (SRVY_REC_ID,PPT_USER_ID,IT_SRVC_NAME,IT_SRVC_CODE, CNTRCTR_NAME,LAST_REC_TXN_USER_ID,LAST_REC_TXN_TYPE_CODE,LAST_REC_TXN_DATE) "
				+ "VALUES (?,?,?,?,?,?,?,?)";
		try {
			conn = dataSource.getConnection();
			
			preparedStatement = conn.prepareStatement(insertSql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.setNString(2, excelRecord.getUserId());
			preparedStatement.setNString(3, excelRecord.getIT_SERVICE_NAME());
			preparedStatement.setNString(4, excelRecord.getIT_SERVICE_CODE());
			preparedStatement.setNString(5, excelRecord.getNAME_OF_CONTRACTOR());
			preparedStatement.setNString(6, "SYSTEM");
			preparedStatement.setNString(7, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
			preparedStatement.setObject(8, new Date());
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				return false;
			}
		}

		return true;
	}

	public void insertSrvyPpt(SrvyPpt srvyPpt) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		preparedStatement = null;

		String insertSql = "INSERT [dbo].[ESV_SRVY_PPT]  ([SRVY_REC_ID] ,[PPT_USER_ID] ,[IT_SRVC_CODE] "
				+ ",[IT_SRVC_NAME] ,[INVT_DATE] ,[LAST_RMDR_DATE] ,[SBMT_DATE] ,[SBMT_STS] "
				+ ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) "
				+ "VALUES (?,?,?,?,? ,?,?,?,?,? ,?)";

		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql);
			try {

				preparedStatement.setInt(1, srvyPpt.getSrvyRecId());
				preparedStatement.setNString(2, srvyPpt.getUser().getUserId());
				preparedStatement.setNString(3, srvyPpt.getItSrvcCode());
				preparedStatement.setNString(4, srvyPpt.getItSrvcName());
				preparedStatement.setObject(5, srvyPpt.getInvtDate());
				preparedStatement.setObject(6, srvyPpt.getLastRmdrDate());
				preparedStatement.setTimestamp(7, srvyPpt.getSbmtDate());
				preparedStatement.setNString(8, srvyPpt.getSbmtSts());

				// todo
				preparedStatement.setNString(9, srvyPpt.getLastRecTxnUserId());
				preparedStatement.setNString(10, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
				preparedStatement.setObject(11, new Date());
				preparedStatement.execute();
			} catch (Exception ex) {
				System.out.println("[SrvyPptDao] Cannot insert record : ");
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int updateSrvyPpt(SrvyPpt srvyPpt) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		preparedStatement = null;
		int intResults = 0;

		String updateSql = "UPDATE [dbo].[ESV_SRVY_PPT] SET [SRVY_REC_ID] = ? ,[PPT_USER_ID] = ? ,[IT_SRVC_CODE] = ? "
				+ ",[IT_SRVC_NAME] = ? "
				+ ",[INVT_DATE] = ? ,[LAST_RMDR_DATE] = ? ,[SBMT_DATE] = ?  "
				+ ",[SBMT_STS] = ? "
				+ ",[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE [SRVY_PPT_ID] = ? AND [LAST_REC_TXN_DATE] = ?";

		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(updateSql);
			try {

				preparedStatement.setInt(1, srvyPpt.getSrvyRecId());

				preparedStatement.setNString(2, srvyPpt.getUser().getUserId());
				preparedStatement.setNString(3, srvyPpt.getItSrvcCode());
				preparedStatement.setNString(4, srvyPpt.getItSrvcName());

				preparedStatement.setObject(5, srvyPpt.getInvtDate());
				preparedStatement.setObject(6, srvyPpt.getLastRmdrDate());
				preparedStatement.setObject(7, srvyPpt.getSbmtDate());

				preparedStatement.setNString(8, srvyPpt.getSbmtSts());
				preparedStatement.setNString(9, srvyPpt.getLastRecTxnUserId());
				preparedStatement.setNString(10, srvyPpt.getLastRecTxnTypeCode());
				preparedStatement.setObject(11, new Date());

				preparedStatement.setInt(12, srvyPpt.getSrvyPptId());
				preparedStatement.setTimestamp(13, srvyPpt.getLastRecTxnDate());

				intResults = preparedStatement.executeUpdate();

			} catch (Exception ex) {
				System.out.println("[SrvyPptDao] Cannot update : " + srvyPpt.getUser().getUserId());
			}
			conn.close();
			return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int updateResp(SrvyPpt srvyPpt) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		preparedStatement = null;
		int intResults = 0;

		String updateSql = "UPDATE [dbo].[ESV_SRVY_PPT] SET [SRVY_CNTN] = ?,[SBMT_STS] = ?, "
				+ "[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ?,[SBMT_DATE]=? "
				+ "WHERE [SRVY_PPT_ID] = ? ";

		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(updateSql);
			try {
				preparedStatement.setNString(1, srvyPpt.getSrvyCntn());
				preparedStatement.setNString(2, srvyPpt.getSbmtSts());
				preparedStatement.setNString(3, srvyPpt.getLastRecTxnUserId());
				preparedStatement.setNString(4, Constant.LAST_REC_TXN_TYPE_CODE_UPDATE);
				preparedStatement.setObject(5, new Date());
				preparedStatement.setObject(6, srvyPpt.getSbmtDate());
				preparedStatement.setInt(7, srvyPpt.getSrvyPptId());
				intResults = preparedStatement.executeUpdate();

			} catch (Exception ex) {
				System.out.println("[SrvyPptDao] Cannot update : " + srvyPpt.getUser().getUserId());
			}
			conn.close();
			return intResults;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void deleteSrvyPptById(int srvyPptId) {
		Connection conn;

		String sql = "delete from [dbo].[ESV_SRVY_PPT] where [SRVY_PPT_ID] = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyPptId);
			preparedStatement.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyPptDao] Cannot delete  : " + srvyPptId);
		}
	}

	public SrvyPpt getSrvyPptById(int srvyPptId) {
		Connection conn;
		ResultSet rs = null;
		SrvyPpt srvyPpt = null;
		String sql = "select [SRVY_PPT_ID] ,[SRVY_REC_ID] ,[PPT_USER_ID] ,[IT_SRVC_CODE] "
				+ ",[IT_SRVC_NAME] ,[INVT_DATE] ,[LAST_RMDR_DATE] ,[SBMT_DATE] ,[SBMT_STS] "
				+ ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
				+ "from [dbo].[ESV_SRVY_PPT] where [SRVY_PPT_ID] = ?";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyPptId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyPpt = this.getSrvyPpt(rs);
			}
			conn.close();

			return srvyPpt;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[CoordinatorDao] Cannot get : " + srvyPptId);
			return null;
		}
	}

	public ArrayList<SrvyPpt> getSrvyPptListBySrvyRecId(int srvyRecId) {
		// PreparedStatement preparedStatement = null;

		ArrayList<SrvyPpt> srvyPptList = new ArrayList<SrvyPpt>();
		Connection conn;
		ResultSet rs = null;

		String sql = "SELECT P.[SRVY_PPT_ID] ,P.[SRVY_REC_ID] ,P.[PPT_USER_ID] ,P.[IT_SRVC_CODE] "
				+ ",P.[IT_SRVC_NAME] ,P.[INVT_DATE] ,P.[LAST_RMDR_DATE] ,P.[SBMT_DATE] ,P.[SBMT_STS] "
				+ ",P.[LAST_REC_TXN_USER_ID], P.[LAST_REC_TXN_TYPE_CODE], P.[LAST_REC_TXN_DATE] ,"
				+ "ISNULL(U.USER_DIV_CODE,'') AS USER_DIV_CODE, "
				+ "ISNULL(U.USER_DSGN,'') AS USER_DSGN, "
				+ "ISNULL(U.USER_NAME,'') AS USER_NAME, "
				+ "ISNULL(U.USER_EMAIL,'') AS USER_EMAIL, "
				+ "P.[SRVY_CNTN] "
				+ "FROM [DBO].[ESV_SRVY_REC] R "
				+ "LEFT JOIN [DBO].[ESV_SRVY_PPT] P ON R.[SRVY_REC_ID] = P.[SRVY_REC_ID] "
				+ "LEFT JOIN [DBO].[ESV_USER] U ON P.[PPT_USER_ID] = U.[USER_ID] "
				+ "WHERE 1=1 "
				+ "AND p.SRVY_REC_ID = ? "
				+ "AND P.LAST_REC_TXN_TYPE_CODE!='"+Constant.LAST_REC_TXN_TYPE_CODE_DELETED+"'";
		
//		String sql = "SELECT [SRVY_CNTN],[SRVY_PPT_ID] ,[SRVY_REC_ID] ,[PPT_USER_ID] ,[IT_SRVC_CODE] "
//				+ ",[IT_SRVC_NAME] ,[INVT_DATE] ,[LAST_RMDR_DATE] ,[SBMT_DATE] ,[SBMT_STS] "
//				+ ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
//				+ "from [dbo].[ESV_SRVY_PPT] where [SRVY_REC_ID] = ? AND LAST_REC_TXN_TYPE_CODE != '" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED + "'";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				srvyPptList.add(this.getSrvyPpt(rs));
			}
			conn.close();

			return srvyPptList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyPptDao] Cannot get participant List ! ");
			return null;
		}
	}

	public void deleteSrvyPptBySrvyRecId(int srvyRecId) {
		Connection conn;

		String sql = "delete from [dbo].[ESV_SRVY_PPT] where [SRVY_REC_ID] = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyPptDao] Cannot delete  : " + srvyRecId);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public ArrayList<SrvyPpt> getSrvyPptList(SrvyPpt srvyPpt, String colOrder, int start, int length) {
		Connection conn = null;
		ResultSet rs = null;
		String filter = " 1=1 ";
		if (srvyPpt.getSrvyRecId() != null) {
			filter += " AND SRVY_REC_ID = " + srvyPpt.getSrvyRecId();
		}
		if (srvyPpt.getFilterList().get("notDeleted") != null) {
			filter += " AND ESP.LAST_REC_TXN_TYPE_CODE != '" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED + "'";
		}
		ArrayList<SrvyPpt> srvyPptList = new ArrayList<SrvyPpt>();
		if ("".equals(colOrder)) {
			colOrder = " ORDER BY USER_DIV_CODE,USER_NAME ";
		} else {
			colOrder = " ORDER BY " + colOrder;
		}
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, "
				+ "ESP.SRVY_PPT_ID,"
				+ "ESP.SRVY_REC_ID,"
				+ "ESP.PPT_USER_ID,"
				+ "ESP.SBMT_STS,"
				+ "ESP.INVT_DATE AS INVT_DATE, "
				+ "ESP.SBMT_DATE AS SBMT_DATE, "
				+ "ESP.LAST_RMDR_DATE AS LAST_RMDR_DATE, "
				+ "ISNULL(EU.USER_DIV_CODE,'') AS USER_DIV_CODE, "
				+ "ISNULL(EU.USER_DSGN,'') AS USER_DSGN,"
				+ "ISNULL(EU.USER_NAME,'') AS USER_NAME,"
				+ "ISNULL(EU.USER_EMAIL,'') AS USER_EMAIL, "
				+ "ISNULL(ESP.CNTRCTR_NAME,'') AS CNTRCTR_NAME, "
				+ "ISNULL(ESP.IT_SRVC_CODE,'') AS IT_SRVC_CODE, "
				+ "ISNULL(ESP.IT_SRVC_NAME,'') AS IT_SRVC_NAME "
				+ "FROM [dbo].[ESV_SRVY_PPT] AS ESP "
				+ "INNER JOIN [dbo].[ESV_USER] AS EU ON EU.USER_ID=ESP.PPT_USER_ID "
				+ "WHERE " + filter;
		String sql = "SELECT * FROM (" + condition + ") AS PAGE WHERE _ROW_ BETWEEN ? AND ? " + colOrder;

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyPptList.add(this.getSrvyPpt(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyPptList;
	}

	public ArrayList<SrvyPpt> getRmdrSrvyPpt() {

		Connection conn;
		ResultSet rs = null;

		ArrayList<SrvyPpt> rmdrSrvyPptList = new ArrayList<SrvyPpt>();

		String sql = "SELECT P.[SRVY_PPT_ID] ,P.[SRVY_REC_ID] ,P.[PPT_USER_ID] ,P.[IT_SRVC_CODE] "
				+ ",P.[IT_SRVC_NAME] ,P.[INVT_DATE] ,P.[LAST_RMDR_DATE] ,P.[SBMT_DATE] ,P.[SBMT_STS] "
				+ ",P.[LAST_REC_TXN_USER_ID], P.[LAST_REC_TXN_TYPE_CODE], P.[LAST_REC_TXN_DATE] ,"
				+ "ISNULL(U.USER_DIV_CODE,'') AS USER_DIV_CODE, "
				+ "ISNULL(U.USER_DSGN,'') AS USER_DSGN, "
				+ "ISNULL(U.USER_NAME,'') AS USER_NAME, "
				+ "ISNULL(U.USER_EMAIL,'') AS USER_EMAIL "
				+ "FROM [DBO].[ESV_SRVY_REC] R "
				+ "LEFT JOIN [DBO].[ESV_SRVY_PPT] P ON R.[SRVY_REC_ID] = P.[SRVY_REC_ID] "
				+ "LEFT JOIN [DBO].[ESV_USER] U ON P.[PPT_USER_ID] = U.[USER_ID] "
				+ "WHERE R.[AUTO_RMDR_DATE] = CAST(GETDATE() AS DATE) "
				+ "AND (P.[SBMT_STS] IS NULL OR P.[SBMT_STS] = 'N') AND R.[SRVY_STS] = " + Constant.SURVEY_STATUS_PUBLISHED
				+ "AND P.LAST_REC_TXN_TYPE_CODE!='D' "
				+ " ORDER BY P.[SRVY_REC_ID]";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				rmdrSrvyPptList.add(getSrvyPpt(rs));
			}
			conn.close();

			return rmdrSrvyPptList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyPptDao] Cannot get Survey Record : ");
			return null;
		}
	}

	public static SrvyPpt getSrvyPpt(ResultSet rs) {
		return getSrvyPpt(rs, "");
	}

	@Override
	public void deleteSrvyPpt(int srvyRecId, String srvyPptIds) {
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		preparedStatement = null;

		String Sql = "UPDATE ESV_SRVY_PPT SET  "
				+ "LAST_REC_TXN_TYPE_CODE = '" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED + "' "
				+ "WHERE SRVY_PPT_ID IN (" + srvyPptIds + ") AND SRVY_REC_ID=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(Sql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public int deleteSrvyPpt(SrvyPpt srvyPpt) {
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		preparedStatement = null;
		int intResults = 0;
		String Sql = "UPDATE ESV_SRVY_PPT SET  "
				+ "[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE SRVY_REC_ID=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(Sql);
			preparedStatement.setNString(1, srvyPpt.getLastRecTxnUserId());
			preparedStatement.setString(2, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setObject(3, new Date());
			preparedStatement.setInt(4, srvyPpt.getSrvyRecId());
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

	public static SrvyPpt getSrvyPpt(ResultSet rs, String prefix) {
		SrvyPpt srvyPpt = new SrvyPpt();
		if (!prefix.isEmpty()) {
			prefix = prefix + "_";
		}
		try {
			srvyPpt.setTotRec(rs.getInt("_TOTAL_"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setSrvyPptId(rs.getInt(prefix + "SRVY_PPT_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setSrvyRecId(rs.getInt(prefix + "SRVY_REC_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setSrvyCntn(rs.getNString(prefix + "SRVY_CNTN"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setItSrvcCode(rs.getNString(prefix + "IT_SRVC_CODE"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setItSrvcName(rs.getNString(prefix + "IT_SRVC_NAME"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setCntrctrName(rs.getNString(prefix + "CNTRCTR_NAME"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.getUser().setUserId(rs.getNString(prefix + "PPT_USER_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.getUser().setUserDivCode(rs.getNString(prefix + "USER_DIV_CODE"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.getUser().setUserDsgn(rs.getNString(prefix + "USER_DSGN"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.getUser().setUserName(rs.getNString(prefix + "USER_NAME"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.getUser().setUserEmail(rs.getNString(prefix + "USER_EMAIL"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setInvtDate(rs.getTimestamp(prefix + "INVT_DATE"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setLastRmdrDate(rs.getTimestamp(prefix + "LAST_RMDR_DATE"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setSbmtDate(rs.getTimestamp(prefix + "SBMT_DATE"));
		} catch (Exception ex) {
		}
		try {
			srvyPpt.setSbmtSts(rs.getNString(prefix + "SBMT_STS"));
		} catch (Exception ex) {
		}
		return srvyPpt;
	}

	@Override
	public int cloneSrvyPpt(SrvyPpt srvyPpt, Integer newSrvyRecId) {
		int last_inserted_id = 0;
		PreparedStatement preparedStatement = null;
		Connection conn = null;

		String sql = "INSERT INTO [ESV_SRVY_PPT]([SRVY_REC_ID] ,[PPT_USER_ID], "
				+ " [SRVY_CNTN] ,[IT_SRVC_CODE] ,[IT_SRVC_NAME], "
				+ " [INVT_DATE] ,[LAST_RMDR_DATE] ,[SBMT_DATE], "
				+ " [SBMT_STS] ,[LAST_REC_TXN_USER_ID] ,[LAST_REC_TXN_TYPE_CODE], "
				+ " [LAST_REC_TXN_DATE]) SELECT "
				+ newSrvyRecId + ",[PPT_USER_ID],"
				+ "'',[IT_SRVC_CODE],[IT_SRVC_NAME],"
				+ " null ,null ,null, "
				+ " null ,'" + srvyPpt.getLastRecTxnUserId() + "' ,'" + Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE + "', "
				+ "GETDATE() FROM [dbo].[ESV_SRVY_PPT] WHERE "
				+ "[LAST_REC_TXN_TYPE_CODE]!='" + Constant.LAST_REC_TXN_TYPE_CODE_DELETED + "' AND [SRVY_REC_ID]=?";

		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, srvyPpt.getSrvyRecId());
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

	public int getSrvyTmplIdBySrvyPptID(int srvyPptId) {
		Connection conn;
		ResultSet rs = null;
		int srvyTmplId = 0;
		String sql = "SELECT [SRVY_TMPL_ID] FROM ESV_SRVY_REC r , ESV_SRVY_PPT p "
				+ "where p.SRVY_REC_ID= r.SRVY_REC_ID AND p.SRVY_PPT_ID = ? ";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyPptId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyTmplId = rs.getInt("SRVY_TMPL_ID");
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyPptDao] Cannot get Survey Template Id from PPT : " + srvyPptId);
		}
		return srvyTmplId;
	}

	public int checkExistPptRecord(int srvyRecId, String userID, String srvcCode) {
		Connection conn;
		ResultSet rs = null;
		int countResult = 0;
		String sql = "SELECT count(1) COUNT_RESULT FROM ESV_SRVY_PPT "
				+ "WHERE SRVY_REC_ID = ? AND LAST_REC_TXN_TYPE_CODE <> 'D' AND PPT_USER_ID = ?";
		if (srvcCode==null) {
			sql += " AND IT_SRVC_CODE is null";
		} else {
			sql += " AND IT_SRVC_CODE = ?";
		}
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.setNString(2, userID);
			if (srvcCode!=null) {
				preparedStatement.setNString(3, srvcCode);
			}
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				countResult = rs.getInt("COUNT_RESULT");
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countResult;
	}

	@Override
	public void insertSrvyPptByDivision(int srvyRecId, String divCode) {
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		preparedStatement = null;
		String insertSql = "INSERT INTO ESV_SRVY_PPT (PPT_USER_ID,SRVY_REC_ID,LAST_REC_TXN_TYPE_CODE) "
				+ "SELECT USER_ID,?,'" + Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE + "' from ESV_USER where USER_DIV_CODE = ? "
				+ "EXCEPT "
				+ "SELECT PPT_USER_ID,SRVY_REC_ID,LAST_REC_TXN_TYPE_CODE from ESV_SRVY_PPT ";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql);
			preparedStatement.setInt(1, srvyRecId);
			preparedStatement.setString(2, divCode);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public ArrayList<SrvyPpt> getSrvyPpt(String srvyPptIds) {
		Connection conn;
		ResultSet rs = null;
		ArrayList<SrvyPpt> srvyPptList = new ArrayList<SrvyPpt>();
		String sql = "SELECT P.[SRVY_PPT_ID] ,P.[SRVY_REC_ID] ,P.[PPT_USER_ID] ,P.[IT_SRVC_CODE] "
				+ ",P.[IT_SRVC_NAME] ,P.[INVT_DATE] ,P.[LAST_RMDR_DATE] ,P.[SBMT_DATE] ,P.[SBMT_STS] "
				+ ",P.[LAST_REC_TXN_USER_ID], P.[LAST_REC_TXN_TYPE_CODE], P.[LAST_REC_TXN_DATE] ,"
				+ "ISNULL(U.USER_DIV_CODE,'') AS USER_DIV_CODE, "
				+ "ISNULL(U.USER_DSGN,'') AS USER_DSGN, "
				+ "ISNULL(U.USER_NAME,'') AS USER_NAME, "
				+ "ISNULL(U.USER_EMAIL,'') AS USER_EMAIL "
				+ "FROM [DBO].[ESV_SRVY_REC] R "
				+ "LEFT JOIN [DBO].[ESV_SRVY_PPT] P ON R.[SRVY_REC_ID] = P.[SRVY_REC_ID] "
				+ "LEFT JOIN [DBO].[ESV_USER] U ON P.[PPT_USER_ID] = U.[USER_ID] "
				+ "WHERE 1=1 "
				+ "AND SRVY_PPT_ID in ("+srvyPptIds+") "
				+ "AND P.LAST_REC_TXN_TYPE_CODE!='"+Constant.LAST_REC_TXN_TYPE_CODE_DELETED+"'";
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				SrvyPpt srvyPpt = getSrvyPpt(rs);
				srvyPpt.setUser(UserDaoImpl.getUser(rs));
				srvyPptList.add(getSrvyPpt(rs));
			}
			conn.close();
			return srvyPptList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyPptDao] Cannot get Survey Record : ");
			return null;
		}
	}
	
	@Override
	public void purgeOldData() {
		Connection conn = null;
		
		String sql = "DELETE ESV_SRVY_PPT WHERE SRVY_REC_ID IN (SELECT SRVY_REC_ID FROM ESV_SRVY_REC ESR \r\n" + 
				"WHERE DATEADD(YEAR, (SELECT RNTNTN_YR FROM ESV_SRVY_TMPL EST WHERE EST.SRVY_TMPL_ID = ESR.SRVY_TMPL_ID AND RNTNTN_YR IS NOT NULL), END_DATE) < GETDATE())";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public void updateSrvyPptEmailTime(int id, String type) {
		Connection conn = null;
		ResultSet rs = null;
		String sql = "UPDATE ESV_SRVY_PPT ";
		if(Constant.EMAIL_TYPE_INVITATION.equals(type)){
			sql +=" SET INVT_DATE = GETDATE() ";
		}else{
			sql +=" SET LAST_RMDR_DATE = GETDATE() ";
		}
		sql +=" WHERE SRVY_PPT_ID='"+id+"' "; 
		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
}
