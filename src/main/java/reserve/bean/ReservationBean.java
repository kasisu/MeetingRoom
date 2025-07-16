package bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 会議室予約情報
 * @version 1.0.0
 * @author 板津　征隆
 */
public class ReservationBean implements Serializable {
	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;
	/**予約番号*/
	private int id;
	/**会議室ID*/
	private String roomId;
	/**利用日*/
	private String date;
	/**利用開始時刻*/
	private String start;
	/**利用終了時刻*/
	private String end;
	/**利用者ID*/
	private String userId;

	/**直列化復元時に使用します。*/
	public ReservationBean() {
	}

	/**会議室予約情報を基に初期化します。*/
	/**
	 * @param id - int 予約番号
	 * @param roomId - String 会議室ID
	 * @param date - String 利用日
	 * @param start - String 利用開始時刻
	 * @param end - String 利用終了時刻
	 * @param userId - String 利用者ID
	 */
	public ReservationBean(int id, String roomId, String date, String start, String end, String userId) {
		this.id = id;
		this.roomId = roomId;
		this.date = date;
		this.start = start;
		this.end = end;
		this.userId = userId;
	}

	/**予約番号以外の会議室予約情報を基に初期化します。*/
	/**
	 * @param roomId - String 会議室ID
	 * @param date - String 利用日
	 * @param start - String 利用開始時刻
	 * @param end - String 利用終了時刻
	 * @param userId - String 利用者ID
	 */
	public ReservationBean(String roomId, String date, String start, String end, String userId) {
		this(0, roomId, date, start, end, userId);
	}

	/**予約番号を返します。*/
	/**
	 * @return int 予約番号
	 */
	public int getId() {
		return id;
	}

	/**予約番号を設定します。*/
	/**
	 * @param id - int 予約番号
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**会議室IDを返します。*/
	/**
	 * @return String 会議室ID
	 */
	public String getRoomId() {
		return roomId;
	}

	/**利用日取得<br>
	*利用日を返します。*/
	/**
	 * @return String 利用日
	 */
	public String getDate() {
		// 日付時間のフォーマットを設定
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy/MM/dd");
		// 初期値設定
		Date date = null;
		try {
			date = sdFormat.parse(this.date);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return dateformatter.format(date);
	}

	/**利用開始時刻取得<br>
	*利用開始時刻を返します。*/
	/**
	 * @return String 利用開始時刻
	 */
	public String getStart() {
		return start;
	}

	/**利用終了時刻を返します。*/
	/**
	 * @return String 利用終了時刻
	 */
	public String getEnd() {
		return end;
	}

	/**利用者IDを返します。*/
	/**
	 * @return String 利用者ID
	 */
	public String getUserId() {
		return userId;
	}

	/**このオブジェクトの文字列表現を返します。*/
	/**
	 * @return String 会議室予約の文字列表現
	 */
	@Override
	public String toString() {
		return "ReservationBean [id=" + id + ", roomId=" + roomId + ", date=" + date + ", start=" + start + ", end="
				+ end + ", userId=" + userId + "]";
	}

}