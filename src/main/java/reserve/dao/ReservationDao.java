package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.ReservationBean;

/**
 * 予約表アクセス用DAO
 * @version 1.0.0
 * @author 板津　征隆
 */
public class ReservationDao {
	private ReservationDao() {
	}

	/**
	 * 利用日の予約を検索します。
	 * @param date - String 利用日 
	 * @return List 予約のリスト（見つからない場合は、nullを返却）
	 * @throws Exception
	 */
	public static List<ReservationBean> findByDate(String date) throws Exception {
		List<ReservationBean> list = new ArrayList<>();
		try {
			// データベース接続
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/meetingRoom");
			// SQL文設定
			String sql = "SELECT * FROM reservation WHERE date = ?";
			try (Connection connection = ds.getConnection();
					PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setString(1, date);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					// パラメータ設定
					int id = rs.getInt("id");
					String rid = rs.getString("roomId");
					String date2 = rs.getString("date");
					String stime = rs.getString("start");
					String etime = rs.getString("end");
					String uid = rs.getString("userId");
					// 取得したデータを元にReservationBean生成
					ReservationBean rese = new ReservationBean(id, rid, date2, stime, etime, uid);
					// リストへデータを格納
					list.add(rese);
				}
				return list;
			}
		} catch (SQLException | NamingException e) {
			// detailMessageフィールドの定義を取得
			Field fieldDefinition = Throwable.class.getDeclaredField("");

			// フィールドをアクセス可能に設定
			fieldDefinition.setAccessible(true);

			// 例外オブジェクトのメッセージを変更する
			fieldDefinition.set(e, "SQLエラーが発生しました");
			System.out.println(e);
			return null;
		}
	}

	/**
	 * 予約を追加します。
	 * @param reservation - ReservationBean 予約情報
	 * @return int 予約できた場合は1，それ以外の場合は-1
	 * @throws Exception
	 */
	public static int insert(ReservationBean reservation) throws Exception {
		// ID初期値設定
		int resid = 0;
		try {
			// データベース接続
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/meetingRoom");
			// SQL文設定
			String sqlsele = "SELECT * FROM reservation WHERE  date = ? AND roomId = ? AND start = ?";
			String sql = "INSERT INTO reservation (id, roomId, date, start, end, userId) VALUES (NULL, ?, ?, ?, ?, ?)";
			try (
					Connection con = ds.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
					PreparedStatement pstsele = con.prepareStatement(sqlsele)) {
				con.setAutoCommit(false); //自動コミットオフ
				// パラメータ設定
				pstmt.setString(1, reservation.getRoomId());
				pstmt.setString(2, reservation.getDate());
				pstmt.setString(3, reservation.getStart());
				pstmt.setString(4, reservation.getEnd());
				pstmt.setString(5, reservation.getUserId());
				pstsele.setString(1, reservation.getDate());
				pstsele.setString(2, reservation.getRoomId());
				pstsele.setString(3, reservation.getStart());
				// 予約の有無の検索
				ResultSet rs = pstsele.executeQuery();
				if (rs.next()) {
					throw new NullPointerException();
				} else {
					// 登録実行
					int ret = pstmt.executeUpdate();
					if (ret <= 0) {
						con.rollback();
						throw new NoSuchFieldException();
					} else {
						con.commit();
					}
					// インクリメントIDを取得
					ResultSet rsid = pstmt.getGeneratedKeys();
					if (rsid.next()) {
						resid = rsid.getInt(1);
						reservation.setId(resid);
					}
				}
			}
		} catch (SQLException | NamingException e) {
			System.out.println(e);
			resid = -1;
			throw e;
		} catch (Exception e) {
			System.out.println(e);
			resid = -1;
			throw e;
		}
		return resid;
	}

	/**
	 * 予約を削除します。
	 * @param reservation - ReservationBean 予約情報
	 * @return boolean 予約をキャンセルできた場合はtrue，それ以外の場合はfalse
	 * @throws Exception
	 */
	public static boolean delete(ReservationBean reservation) throws Exception {
		// SQL文設定
		String sqlsele = "SELECT * FROM reservation WHERE  date = ? AND roomId = ? AND start = ?";
		String sql = "DELETE FROM reservation WHERE date = ? AND roomId = ? AND start = ? AND userId = ?";
		try {
			// データベース接続
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/meetingRoom");
			try (Connection con = ds.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					PreparedStatement pstsele = con.prepareStatement(sqlsele)) {
				// パラメータ設定
				pstmt.setString(1, reservation.getDate());
				pstmt.setString(2, reservation.getRoomId());
				pstmt.setString(3, reservation.getStart());
				pstmt.setString(4, reservation.getUserId());
				pstsele.setString(1, reservation.getDate());
				pstsele.setString(2, reservation.getRoomId());
				pstsele.setString(3, reservation.getStart());
				//				pstsele.setString(4, reservation.getUserId());
				// 予約の有無の検索
				ResultSet rs = pstsele.executeQuery();
				if (!(rs.next())) {
					throw new NullPointerException();
				}
				// 削除実行
				int ret = pstmt.executeUpdate();
				if (ret >= 1) {
					return true;
				} else {
					throw new NoSuchFieldException();
				}
			}
		} catch (SQLException | NamingException e) {
			System.out.println(e);
			throw e;
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
	}
}