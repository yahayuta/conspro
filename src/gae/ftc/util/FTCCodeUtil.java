package gae.ftc.util;

/**
 * FTCコード値部品
 * @author yahayuta
 */
public class FTCCodeUtil {

	/**
	 * 英語の型名を返す
	 * @param type
	 * @return
	 */
	public static String getTypeEng(String type) {
		if ("1".equals(type)) {
			return "Mini Excavator";
		} else if ("2".equals(type)) {
			return "Excavator";
		} else if ("3".equals(type)) {
			return "Crawler Dozer";
		} else if ("4".equals(type)) {
			return "Crawler Loader";
		} else if ("5".equals(type)) {
			return "Wheel Loader";
		} else if ("6".equals(type)) {
			return "Road Equipment";
		} else if ("7".equals(type)) {
			return "Crane";
		} else if ("8".equals(type)) {
			return "Crawler Carrier";
		} else if ("9".equals(type)) {
			return "Attachment";
		} else if ("10".equals(type)) {
			return "Parts";
		} else if ("11".equals(type)) {
			return "Generator";
		} else if ("12".equals(type)) {
			return "Air Compressor";
		} else if ("13".equals(type)) {
			return "Welder";
		} else if ("14".equals(type)) {
			return "Truck";
		} else if ("15".equals(type)) {
			return "Aerial Platform";
		} else if ("99".equals(type)) {
			return "Other";
		} else {
			return "";
		}
	}
	
	/**
	 * 英語の型名を返す
	 * @param obj
	 * @return
	 */
	public static String getTypeEng(Object obj) {
		if (obj == null) {
			return "";
		}
		return getTypeEng(obj.toString());
	}
	
	/**
	 * 日本語の型名を返す
	 * @param type
	 * @return
	 */
	public static String getTypeJa(String type) {
		if ("1".equals(type)) {
			return "油圧ショベル 8t未満";
		} else if ("2".equals(type)) {
			return "油圧ショベル 10t以上";
		} else if ("3".equals(type)) {
			return "ブルドーザー";
		} else if ("4".equals(type)) {
			return "ショベルローダー";
		} else if ("5".equals(type)) {
			return "タイヤショベル";
		} else if ("6".equals(type)) {
			return "舗装機械";
		} else if ("7".equals(type)) {
			return "クレーン";
		} else if ("8".equals(type)) {
			return "キャリアダンプ";
		} else if ("9".equals(type)) {
			return "アタッチメント";
		} else if ("10".equals(type)) {
			return "部品";
		} else if ("11".equals(type)) {
			return "発電機";
		} else if ("12".equals(type)) {
			return "エアーコンプレッサー";
		} else if ("13".equals(type)) {
			return "溶接機";
		} else if ("14".equals(type)) {
			return "トラック";
		} else if ("15".equals(type)) {
			return "高所作業車";
		} else if ("99".equals(type)) {
			return "その他";
		} else {
			return "";
		}
	}
	
	/**
	 * 日本語の分類サブを返す
	 * @param type
	 * @return
	 */
	public static String getTypeSubJa(String type) {
		if ("1".equals(type)) {
			return "大割";
		} else if ("2".equals(type)) {
			return "小割";
		} else if ("3".equals(type)) {
			return "カッター";
		} else if ("4".equals(type)) {
			return "フォークグラブ";
		} else if ("5".equals(type)) {
			return "ブレーカー";
		} else if ("6".equals(type)) {
			return "スケルトンバケット";
		} else if ("7".equals(type)) {
			return "その他";
		} else {
			return "";
		}
	}
	
	/**
	 * 日本語の型名を返す
	 * @param obj
	 * @return
	 */
	public static String getTypeJa(Object obj) {
		if (obj == null) {
			return "";
		}
		return getTypeJa(obj.toString());
	}
	
	/**
	 * 日本語の分類サブを返す
	 * @param obj
	 * @return
	 */
	public static String getTypeSubJa(Object obj) {
		if (obj == null) {
			return "";
		}
		return getTypeSubJa(obj.toString());
	}
	
	/**
	 * 権限名を返す
	 * @param type
	 * @return
	 */
	public static String getAuthName(String aCode) {
		if ("1".equals(aCode)) {
			return "1:一般";
		} else if ("2".equals(aCode)) {
			return "2:経理";
		} else if ("3".equals(aCode)) {
			return "3:管理";
		} else if ("4".equals(aCode)) {
			return "4:事務";
		} else if ("5".equals(aCode)) {
			return "5:サービス";
		} else {
			return "";
		}
	}
	
	/**
	 * 表示／非表示を返す
	 * @param flg
	 * @return
	 */
	public static String getIsDisp(String flg) {
		if ("0".equals(flg)) {
			return "無";
		} else if ("1".equals(flg)) {
			return "F";
		} else if ("2".equals(flg)) {
			return "濱";
		} else if ("3".equals(flg)) {
			return "F濱";
		} else {
			return "";
		}
	}
	
	/**
	 * 表示／非表示を返す
	 * @param obj
	 * @return
	 */
	public static String getIsDisp(Object obj) {
		if (obj == null) {
			return "";
		}
		return getIsDisp(obj.toString());
	}
	
	/**
	 * 写真URL代替文字表示英語
	 * @param url
	 * @return
	 */
	public static String isExistURLEn(String url) {
		if (url != null && url.length() > 0) {
			return "SHOW PHOTOS";
		}
		return "";
	}
	
	/**
	 * 写真URL代替文字表示日本語
	 * @param url
	 * @return
	 */
	public static String isExistURLJa(String url) {
		if (url != null && url.length() > 0) {
			return "写真参照";
		}
		return "";
	}
	
	/**
	 * 査定種類を返す
	 * @param type
	 * @return
	 */
	public static String getSateiType(String type) {
		if ("1".equals(type)) {
			return "相場確認";
		} else if ("2".equals(type)) {
			return "売却希望";
		} else if ("3".equals(type)) {
			return "在庫仮登録";
		} else {
			return "";
		}
	}
	
	/**
	 * 業種を返す
	 * @param type
	 * @return
	 */
	public static String getClientType(String type) {
		if ("1".equals(type)) {
			return "貿易";
		} else if ("2".equals(type)) {
			return "オークション";
		} else if ("3".equals(type)) {
			return "個人ブローカー";
		} else if ("4".equals(type)) {
			return "新車ディーラー";
		} else if ("5".equals(type)) {
			return "中古車ディーラー";
		} else if ("6".equals(type)) {
			return "レンタル";
		} else if ("7".equals(type)) {
			return "修理";
		} else if ("8".equals(type)) {
			return "エンドユーザー";
		} else if ("9".equals(type)) {
			return "その他";
		} else {
			return "";
		}
	}
	
	/**
	 * 業種を返す
	 * @param obj
	 * @return
	 */
	public static String getClientType(Object obj) {
		if (obj == null) {
			return "";
		}
		return getClientType(obj.toString());
	}
	
	/**
	 * 与信管理を返す
	 * @param credit
	 * @return
	 */
	public static String getCredit(String credit) {
		if ("A".equals(credit)) {
			return "入金前引渡OK";
		} else if ("B".equals(credit)) {
			return "入金後引渡";
		} else if ("C".equals(credit)) {
			return "問題あり";
		} else {
			return "";
		}
	}
	
	/**
	 * 与信管理を返す
	 * @param obj
	 * @return
	 */
	public static String getCredit(Object obj) {
		if (obj == null) {
			return "";
		}
		return getCredit(obj.toString());
	}
	
	/**
	 * レンタル状態を返す
	 * @param rental
	 * @return
	 */
	public static String getRental(String rental) {
		if ("0".equals(rental)) {
			return "空車";
		} else if ("1".equals(rental)) {
			return "出庫中";
		} else if ("2".equals(rental)) {
			return "整備中";
		} else {
			return "空車";
		}
	}
	
	/**
	 * レンタル状態を返す
	 * @param obj
	 * @return
	 */
	public static String getRental(Object obj) {
		if (obj == null) {
			return "";
		}
		return getRental(obj.toString());
	}
	
	/**
	 * 整備状況を返す
	 * @param conditionMaintenance
	 * @return
	 */
	public static String getConditionMaintenance(String conditionMaintenance) {
		if ("1".equals(conditionMaintenance)) {
			return "全塗装整備済み";
		} else if ("2".equals(conditionMaintenance)) {
			return "整備済み";
		} else if ("3".equals(conditionMaintenance)) {
			return "現状有姿";
		} else {
			return "";
		}
	}
	
	/**
	 * 整備状況を返す
	 * @param obj
	 * @return
	 */
	public static String getConditionMaintenance(Object obj) {
		if (obj == null) {
			return "";
		}
		return getConditionMaintenance(obj.toString());
	}
	
	/**
	 * 整備状況を返す（英語）
	 * @param conditionMaintenance
	 * @return
	 */
	public static String getConditionMaintenanceEng(String conditionMaintenance) {
		if ("1".equals(conditionMaintenance)) {
			return "Painted and Served";
		} else if ("2".equals(conditionMaintenance)) {
			return "Served";
		} else if ("3".equals(conditionMaintenance)) {
			return "As is condition";
		} else {
			return "";
		}
	}
	
	/**
	 * 整備状況を返す（英語）
	 * @param obj
	 * @return
	 */
	public static String getConditionMaintenanceEng(Object obj) {
		if (obj == null) {
			return "";
		}
		return getConditionMaintenanceEng(obj.toString());
	}
	
	/**
	 * 注文区分を返す
	 * @param orderType
	 * @return
	 */
	public static String getOrderType(String orderType) {
		if ("0".equals(orderType)) {
			return "月極";
		} else if ("1".equals(orderType)) {
			return "日極";
		} else if ("2".equals(orderType)) {
			return "月極日割";
		} else {
			return "";
		}
	}
	
	/**
	 * 注文区分を返す
	 * @param obj
	 * @return
	 */
	public static String getOrderType(Object obj) {
		if (obj == null) {
			return "";
		}
		return getOrderType(obj.toString());
	}
	
	/**
	 * 日報区分を返す
	 * @param obj
	 * @return
	 */
	public static String getDailyReportType(Object obj) {
		String dailyReportType = obj.toString();
		if ("0".equals(dailyReportType)) {
			return "販売";
		} else if ("1".equals(dailyReportType)) {
			return "レンタル"; 
		} else if ("2".equals(dailyReportType)) {
			return "その他";
		} else {
			return "";
		}
		
	}
}
