

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MeetingRoom;
import bean.ReservationBean;
import bean.RoomBean;

/**
 * 
 * 会議室管理システムのモデル
 * @version 1.0.0
 * @author 板津　征隆
 */
@WebServlet("/ReserveCreate")
public class ReserveCreateServlet extends SessionManagement {

	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;

	//メソッド一覧
	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//文字コード受信
		request.setCharacterEncoding("UTF-8");

		//セッションの有無の確認
		//		boolean sessionAvailability = false;
		HttpSession session = request.getSession();

		//インスタンス生成
		MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
		// 親クラスにてIDチェックしログイン情報を管理
		if (meetingRoom == null || !(loginManagement(request))) {
			String nextpage = request.getContextPath() + "/login.jsp";
			response.sendRedirect(nextpage);
			return;
		}

		//入力パラメーター
		String roomId = request.getParameter("roomId");
		String time = request.getParameter("time");

		//MeetingRoom からの　beenの生成これ？
		ReservationBean rvb = null;
		RoomBean rmb = null;
		String errorReason = null;
		session.removeAttribute("reservation");
		session.removeAttribute("room");
		try {
			rvb = meetingRoom.createResevation(roomId, time);
			rmb = meetingRoom.getRoom(roomId);
			session.setAttribute("reservation", rvb);//予約
			session.setAttribute("room", rmb);//会議室セット
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			errorReason = "システムエラー、管理者に確認してください";
			System.out.println(e);
			session.setAttribute("errorReason", errorReason);
		}

		String nextPage = "/reserveConfirm.jsp";//reserveConfirm予約確認画面にここでキャンセルはしない

		//フォワード
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

	//Getで送ってきたらログイン画面にリダイレクト
	/**直接アクセスは、ログインページにリダイレクトする*/
	/**
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextpage = request.getContextPath() + "/login.jsp";
		response.sendRedirect(nextpage);
		return; //リダイレクト
	}
}