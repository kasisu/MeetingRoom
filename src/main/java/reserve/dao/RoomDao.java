package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.RoomBean;
/**
 * 会議室表アクセス用DAO
 * @version 1.0.0
 * @author 板津　征隆 
 */
public class RoomDao {
	/**
	 * すべての会議室を検索する
	 * @return RoomBean[] 会議室の配列（見つからない場合は、nullを返却）
	 * @throws Exception
	 */
	public static RoomBean[] findAll​() throws Exception {
		try {
			// データベース接続
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/meetingRoom");
			// SQL文設定
			String sql = "SELECT * FROM room";
			String sql2 = "SELECT count(*) AS count FROM room";
			RoomBean[] rb;
			try (
					Connection con = ds.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql);
					PreparedStatement cntPS = con.prepareStatement(sql2);) {
				// Room数初期値設定
				int cnt = 0;
				try (ResultSet rs = cntPS.executeQuery()) {
					while (rs.next()) {
						// Room数取得
						cnt = Integer.parseInt(rs.getString("count"));
					}
				}
				rb = new RoomBean[cnt];
				// Room情報取得
				try (ResultSet rs = pstmt.executeQuery()) {
					int i = 0;
					while (rs.next()) {
						if (rs.getString("id") != null) {
							// 取得した情報を元にRoomBeanを生成
							rb[i] = new RoomBean(rs.getString("id"), rs.getString("name"));
							i++;
						}
					}
					return rb;
				}

			}

		} catch (SQLException | NamingException e) {
			// detailMessageフィールドの定義を取得
			Field fieldDefinition = Throwable.class.getDeclaredField("");

			// フィールドをアクセス可能に設定
			fieldDefinition.setAccessible(true);

			// 例外オブジェクトのメッセージを変更する
			fieldDefinition.set(e, "SQLエラーが発生しました");
			System.out.println(e);
		}
		return null;
	}
}
