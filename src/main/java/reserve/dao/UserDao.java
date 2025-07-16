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

import bean.UserBean;
/**
 * 利用者表アクセス用DAO
 * @version 1.0.0
 * @author 板津　征隆 
 */
public class UserDao {
	/**
	 * 利用者IDとパスワードで利用者認証を行い，認証した利用者情報を返します。
	 * @param id - String 利用者ID
	 * @param password - String パスワード
	 * @return UserBean 認証に成功した場合は利用者，それ以外の場合null
	 * @throws Exception
	 */
	public static UserBean certificate​(String id, String password)
			throws Exception {
		try {
			// データベース接続
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/meetingRoom");
			// SQL文設定
			String sql = "SELECT * FROM user WHERE id = ? AND password = ?";
			UserBean ub;
			try (
					Connection con = ds.getConnection();
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, id);
				pstmt.setString(2, password);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						if (rs.getString("id") == null) {
							throw new Exception("IDが登録されていません");
						} else if (rs.getString("password") == null) {
							throw new Exception("パスワードが間違っています");
						} else {
							ub = new UserBean(id, password, rs.getString("name"), rs.getString("address"));
							return ub;
						}
					}
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
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
