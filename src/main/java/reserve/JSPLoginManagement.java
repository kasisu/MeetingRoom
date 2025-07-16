

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
public class JSPLoginManagement extends SessionManagement {
	/**ログインの可否を格納*/
	private boolean login;

	/**初期値設定*/
	public JSPLoginManagement(){
		this.login = false;
	}
	
	/**セッションに保存されている情報を元にログイン情報を取得*/
	public JSPLoginManagement(HttpServletRequest request) {
		try {
			this.login = SessionManagement.loginManagement(request);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return boolean ログインの有無
	 */
	public boolean isLogin() {
		return login;
	}
}
