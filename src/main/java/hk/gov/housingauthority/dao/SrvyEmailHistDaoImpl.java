package hk.gov.housingauthority.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import hk.gov.housingauthority.model.SrvyEmailHist;
import hk.gov.housingauthority.model.SrvyPpt;
import hk.gov.housingauthority.model.SrvyTmpl;
import hk.gov.housingauthority.util.Constant;

public class SrvyEmailHistDaoImpl implements SrvyEmailHistDao {

	private DataSource dataSource;

	public void insertEmailHist(SrvyEmailHist srvyEmailHist) {
		PreparedStatement preparedStatement = null;
		Connection conn;
		preparedStatement = null;

		String insertSql = "INSERT [dbo].[ESV_SRVY_EMAIL_HIST] ([SRVY_REC_ID] ,[EMAIL_TYPE] "
				+ ",[EMAIL_TO] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] ,[EMAIL_SEND_DATE] " + ",[EMAIL_STS] ,[TRY_CNT] ,"
				+ "[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE],[SRVY_PPT_ID]) "
				+ "VALUES (?,?,?,?,? ,?,?,?,?,? ,? ,?)";

		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(insertSql);
			try {
				preparedStatement.setInt(1, srvyEmailHist.getSrvyRecId());
				preparedStatement.setNString(2, srvyEmailHist.getSrvyEmailType().getSrvyEmailTypeId());
				preparedStatement.setNString(3, srvyEmailHist.getEmailTo());
				preparedStatement.setNString(4, srvyEmailHist.getEmailSubj());
				preparedStatement.setNString(5, srvyEmailHist.getEmailCntn());
				preparedStatement.setTimestamp(6, srvyEmailHist.getEmailSendDate());
				preparedStatement.setNString(7, srvyEmailHist.getSrvyEmailSts().getSrvyEmailStsId());
				if (srvyEmailHist.getTryCnt() == null) {
					preparedStatement.setNString(8, null);
				} else {
					preparedStatement.setInt(8, srvyEmailHist.getTryCnt());
				}
				// todo
				preparedStatement.setNString(9, srvyEmailHist.getLastRecTxnUserId());
				preparedStatement.setNString(10, Constant.LAST_REC_TXN_TYPE_CODE_ACTIVE);
				preparedStatement.setObject(11, new Date());
				preparedStatement.setInt(12, srvyEmailHist.getSrvyPptId());
				preparedStatement.execute();
			} catch (Exception ex) {
				System.out.println("[SrvyEmailHistDao] Cannot insert record : " + srvyEmailHist.getEmailTo());
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int updateEmailHist(SrvyEmailHist srvyEmailHist) {
		// fail return = 0
		PreparedStatement preparedStatement = null;
		Connection conn;
		String updateSql = "UPDATE [dbo].[ESV_SRVY_EMAIL_HIST] SET [SRVY_REC_ID] = ? ,[EMAIL_TYPE] = ? "
				+ ",[EMAIL_TO] = ? ,[EMAIL_SUBJ] = ? ,[EMAIL_CNTN] = ? ,[EMAIL_SEND_DATE] = ?  "
				+ ",[EMAIL_STS] = ? ,[TRY_CNT] = ? "
				+ ",[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE [SRVY_EMAIL_HIST_ID] = ? AND [LAST_REC_TXN_DATE] = ?";

		int intResults = 0;
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(updateSql);
			preparedStatement.setInt(1, srvyEmailHist.getSrvyRecId());
			preparedStatement.setNString(2, srvyEmailHist.getSrvyEmailType().getSrvyEmailTypeId());
			preparedStatement.setNString(3, srvyEmailHist.getEmailTo());
			preparedStatement.setNString(4, srvyEmailHist.getEmailSubj());
			preparedStatement.setNString(5, srvyEmailHist.getEmailCntn());
			preparedStatement.setTimestamp(6, srvyEmailHist.getEmailSendDate());
			preparedStatement.setNString(7, srvyEmailHist.getSrvyEmailSts().getSrvyEmailStsId());
			preparedStatement.setInt(8, srvyEmailHist.getTryCnt());
			preparedStatement.setNString(9, srvyEmailHist.getLastRecTxnUserId());
			preparedStatement.setNString(10, Constant.LAST_REC_TXN_TYPE_CODE_UPDATE);
			preparedStatement.setObject(11, new Date());
			preparedStatement.setInt(12, srvyEmailHist.getSrvyEmailHistId());
			preparedStatement.setNString(13, "" + srvyEmailHist.getLastRecTxnDate());
			intResults = preparedStatement.executeUpdate();
			conn.close();
			return intResults;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return intResults;
	}

	public SrvyEmailHist getEmailHistByID(int srvyEmailHistId) {
		// PreparedStatement preparedStatement = null;

		SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
		Connection conn;
		ResultSet rs = null;

		String sql = "select [SRVY_EMAIL_HIST_ID] ,[SRVY_REC_ID] ,[EMAIL_TYPE] "
				+ ",[EMAIL_TO] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] ,[EMAIL_SEND_DATE] "
				+ ",[EMAIL_SEND_DATE] ,[EMAIL_STS] ,[TRY_CNT] "
				+ ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
				+ "from [dbo].[ESV_SRVY_EMAIL_HIST] where [SRVY_EMAIL_HIST_ID] = ?";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyEmailHistId);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				srvyEmailHist = this.getSrvyEmailHist(rs);
			}
			conn.close();

			return srvyEmailHist;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyEmailHistDao] Cannot get Additional Info List ! ");
			return null;
		}
	}

	public ArrayList<SrvyEmailHist> getEmailHistListBySrvyRecId(int srvyRecId) {
		// PreparedStatement preparedStatement = null;

		ArrayList<SrvyEmailHist> srvyEmailHistList = new ArrayList<SrvyEmailHist>();
		SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
		Connection conn;
		ResultSet rs = null;

		String sql = "select [SRVY_EMAIL_HIST_ID] ,[SRVY_REC_ID] ,[EMAIL_TYPE] "
				+ ",[EMAIL_TO] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] ,[EMAIL_SEND_DATE] "
				+ ",[EMAIL_SEND_DATE] ,[EMAIL_STS] ,[TRY_CNT] "
				+ ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE] "
				+ "from [dbo].[ESV_SRVY_EMAIL_HIST] where [SRVY_REC_ID] = ?";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, srvyRecId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyEmailHistList.add(this.getSrvyEmailHist(rs));
			}

			conn.close();

			return srvyEmailHistList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[SrvyEmailHistDao] Cannot get email history List ! ");
			return null;
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public ArrayList<SrvyEmailHist> getEmailHistList(SrvyEmailHist srvyEmailHist, String colOrder, int start, int length) {

		Connection conn = null;
		ResultSet rs = null;
		String filter = " 1=1 ";

		if (srvyEmailHist.getSrvyRecId() != null) {
			filter += " AND SRVY_REC_ID = " + srvyEmailHist.getSrvyRecId();
		}
		if (srvyEmailHist.getEmailSendDateFrom() != null) {
			filter += " AND EMAIL_SEND_DATE >= '" + srvyEmailHist.getEmailSendDateFrom() + "'";
		}
		if (srvyEmailHist.getEmailSendDateTo() != null) {
			filter += " AND EMAIL_SEND_DATE <= '" + srvyEmailHist.getEmailSendDateTo() + "'";
		}
		if (srvyEmailHist.getSrvyEmailType().getSrvyEmailTypeId() != null) {
			filter += " AND EMAIL_TYPE = '" + srvyEmailHist.getSrvyEmailType().getSrvyEmailTypeId() + "'";
		}
		if (srvyEmailHist.getEmailTo() != null) {
			filter += " AND EMAIL_TO LIKE '" + srvyEmailHist.getEmailTo() + "'";
		}
		if (srvyEmailHist.getSrvyEmailSts().getSrvyEmailStsId() != null) {
			filter += " AND EMAIL_STS = '" + srvyEmailHist.getSrvyEmailSts().getSrvyEmailStsId() + "'";
		}

		if ("".equals(colOrder)) {
			colOrder = " ORDER BY EMAIL_SEND_DATE DESC ";
		} else {
			colOrder = " ORDER BY " + colOrder;
		}

		ArrayList<SrvyEmailHist> srvyEmailHistList = new ArrayList<SrvyEmailHist>();
		String condition = "SELECT ROW_NUMBER() OVER(" + colOrder + ") AS _ROW_, COUNT(*) OVER() AS _TOTAL_, ESEH.* "
				+ "FROM [dbo].[ESV_SRVY_EMAIL_HIST] AS ESEH " + "WHERE " + filter;
		String sql = "SELECT * FROM (" + condition + ") AS PAGE WHERE _ROW_ BETWEEN ? AND ? " + colOrder;

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, start + 1);
			preparedStatement.setInt(2, start + length);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyEmailHistList.add(this.getSrvyEmailHist(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyEmailHistList;
	}

	public SrvyEmailHist getSrvyEmailHist(ResultSet rs) throws SQLException {
		SrvyEmailHist srvyEmailHist = new SrvyEmailHist();
		try {
			srvyEmailHist.setTotRec(rs.getInt("_TOTAL_"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setSrvyEmailHistId(rs.getInt("SRVY_EMAIL_HIST_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setSrvyRecId(rs.getInt("SRVY_REC_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.getSrvyEmailType().setSrvyEmailTypeId(rs.getString("EMAIL_TYPE"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setEmailTo(rs.getString("EMAIL_TO"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setEmailSubj(rs.getString("EMAIL_SUBJ"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setEmailCntn(rs.getString("EMAIL_CNTN"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setEmailSendDate(rs.getTimestamp("EMAIL_SEND_DATE"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.getSrvyEmailSts().setSrvyEmailStsId(rs.getString("EMAIL_STS"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setTryCnt(rs.getInt("TRY_CNT"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setLastRecTxnUserId(rs.getString("LAST_REC_TXN_USER_ID"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setLastRecTxnTypeCode(rs.getString("LAST_REC_TXN_TYPE_CODE"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setLastRecTxnDate(rs.getTimestamp("LAST_REC_TXN_DATE"));
		} catch (Exception ex) {
		}
		try {
			srvyEmailHist.setSrvyPptId(rs.getInt("SRVY_PPT_ID"));
		} catch (Exception ex) {
		}
		return srvyEmailHist;
	}

	@Override
	public int deleteEmailHist(SrvyEmailHist srvyEmailHist) {
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		preparedStatement = null;
		int intResults = 0;
		String Sql = "UPDATE ESV_SRVY_EMAIL_HIST SET  "
				+ "[LAST_REC_TXN_USER_ID] = ?, [LAST_REC_TXN_TYPE_CODE] = ? , [LAST_REC_TXN_DATE] = ? "
				+ "WHERE SRVY_REC_ID=?";
		try {
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(Sql);
			preparedStatement.setNString(1, srvyEmailHist.getLastRecTxnUserId());
			preparedStatement.setString(2, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setObject(3, new Date());
			preparedStatement.setInt(4, srvyEmailHist.getSrvyRecId());
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

	@Override
	public ArrayList<SrvyEmailHist> getPendingEmailHistList(int limit) {
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<SrvyEmailHist> srvyEmailHistList = new ArrayList<SrvyEmailHist>();
		String sql = "SELECT TOP " + limit + " [SRVY_EMAIL_HIST_ID] ,[SRVY_REC_ID] ,[EMAIL_TYPE] "
				+ ",[EMAIL_TO] ,[EMAIL_SUBJ] ,[EMAIL_CNTN] ,[EMAIL_SEND_DATE] "
				+ ",[EMAIL_SEND_DATE] ,[EMAIL_STS] ,[TRY_CNT] "
				+ ",[LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE],[SRVY_PPT_ID] "
				+ "FROM [dbo].[ESV_SRVY_EMAIL_HIST] WHERE [LAST_REC_TXN_TYPE_CODE]!=? AND [EMAIL_STS]=?";

		try {
			conn = dataSource.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, Constant.LAST_REC_TXN_TYPE_CODE_DELETED);
			preparedStatement.setString(2, Constant.SEND_STATUS_WAITING_FOR_SEND);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				srvyEmailHistList.add(this.getSrvyEmailHist(rs));
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return srvyEmailHistList;
	}
}
