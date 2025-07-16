package bean;

import java.io.Serializable;

/**
 * 
 * 会議室の情報
 * @version 1.0.0
 * @author 板津　征隆
 */
public class RoomBean implements Serializable {
	/**会議室ID*/
	private String id;
	/**会議室名*/
	private String name;
	/**直列化用バージョン番号
	 * @see
	 * */
	private static final long serialVersionUID = 1L;

	
	/**直列化復元時に使用します。*/
	public RoomBean() {
	}

	/**会議室情報で初期化します。*/
	/**
	 * @param id - String 会議室ID
	 * @param name - String 会議室名
	 */
	public RoomBean(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**会議室IDを返します。*/
	/**
	 * @return String 会議室ID
	 */
	public String getId() {
		return id;
	}
	
	/**会議室名を返します。*/
	/**
	 * @return String 会議室名
	 */
	public String getName() {
		return name;
	}
	
	/**このオブジェクトの文字列表現を返します。*/
	/**
	 * @return String 会議室の文字列表現
	 */
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + "]";
	}

}