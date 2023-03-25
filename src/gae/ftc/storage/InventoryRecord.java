package gae.ftc.storage;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

/**
 * @deprecated
 * @author yasun
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class InventoryRecord
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String DATE;//ID

    @Persistent
    private String NAME;//型式

    @Persistent
    private String PRICE;//表示価格

    @Persistent
    private String CONDITION;//程度

    @Persistent
    private String TYPE;//分類
    
    @Persistent
    private String YEAR;//年式

    @Persistent
    private String SERIALNO;//号機

    @Persistent
    private String HOURS;//稼動時間

    @Persistent
    private String OTHER;//詳細英語

    @Persistent
    private String OTHER_JA;//詳細日本語
    
    @Persistent
    private String MANUFACTURER;//メーカー
    
    @Persistent
    private String PIC_URL;//写真アドレス
    
    @Persistent
    private Blob PIC;//表紙写真用データ

    // 以下内部用項目
    
    @Persistent
    private String ACCOUNT;//仕入担当
    
    @Persistent
    private String ORDER_DATE;//発注日
    
    @Persistent
    private String WEB_DISP;//WEB表示
    
    @Persistent
    private String SELLER;//仕入先
    
    @Persistent
    private String ORDER_PRICE;//仕入価格
    
    @Persistent
    private String ORDER_TRANS_COST;//仕入運賃
    
    @Persistent
    private String PARTS_COST;//部品代
    
    @Persistent
    private String MAINTENANCE_COST;//整備費
    
    @Persistent
    private String ORDER_OUT_ORDER_COST;//仕入外注費
    
    @Persistent
    private String ORDER_COST_PRICE;//仕入原価
    
    @Persistent
    private String SELL_TRANCE_COST;//販売運賃
    
    @Persistent
    private String SHIP_COST;//船積費用
    
    @Persistent
    private String SELL_OUT_ORDER_COST;//販売外注費
    
    @Persistent
    private String INS_COST;//保険料
    
    @Persistent
    private String FREIGHT_COST;//フレイト
    
    @Persistent
    private String SELL_COST_PRICE;//販売原価
    
    @Persistent
    private String SELL_PRICE;//販売価格
    
    @Persistent
    private String WHOL_PRICE;//業販価格
    
    @Persistent
    private String PROFIT;//利益
    
    @Persistent
    private String BUYER;//販売先
    
    @Persistent
    private String ORDER_PAY_DATE;//仕入代金支払日
    
    @Persistent
    private String SELL_PAY_DATE;//売上入金日
    
    @Persistent
    private String SELL_MONTH;//売上月

    @Persistent
    private String MEMO;//在庫メモ

    // 制御項目
    
    @Persistent
    private String DATA_FLG;//データフラグ
    
    @Persistent
    private String WEB_DISP_DATE;//WEB表示日時
    
    // 201511 顧客コード対応
    
    @Persistent
    private String SELLER_CODE;//仕入先コード
    
    @Persistent
    private String BUYER_CODE;//販売先コード
    
    // 201601 帳票対応
    
    @Persistent
    private String TRAN_PLACE;//引渡場所
    
    @Persistent
    private String INVOICE_NO;

    // 201606 HZJ対応
    @Persistent
    private String RENTAL;
    
    // 201704 整備状況項目追加
    private String CONDITION_MAINTENANCE;

	/**
	 * @return the dATE
	 */
	public String getDATE() {
		return DATE;
	}

	/**
	 * @param dATE the dATE to set
	 */
	public void setDATE(String dATE) {
		DATE = dATE;
	}

	/**
	 * @return the nAME
	 */
	public String getNAME() {
		return NAME;
	}

	/**
	 * @param nAME the nAME to set
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/**
	 * @return the pRICE
	 */
	public String getPRICE() {
		return PRICE;
	}

	/**
	 * @param pRICE the pRICE to set
	 */
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	/**
	 * @return the cONDITION
	 */
	public String getCONDITION() {
		return CONDITION;
	}

	/**
	 * @param cONDITION the cONDITION to set
	 */
	public void setCONDITION(String cONDITION) {
		CONDITION = cONDITION;
	}

	/**
	 * @return the tYPE
	 */
	public String getTYPE() {
		return TYPE;
	}

	/**
	 * @param tYPE the tYPE to set
	 */
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	/**
	 * @return the yEAR
	 */
	public String getYEAR() {
		return YEAR;
	}

	/**
	 * @param yEAR the yEAR to set
	 */
	public void setYEAR(String yEAR) {
		YEAR = yEAR;
	}

	/**
	 * @return the sERIALNO
	 */
	public String getSERIALNO() {
		return SERIALNO;
	}

	/**
	 * @param sERIALNO the sERIALNO to set
	 */
	public void setSERIALNO(String sERIALNO) {
		SERIALNO = sERIALNO;
	}

	/**
	 * @return the hOURS
	 */
	public String getHOURS() {
		return HOURS;
	}

	/**
	 * @param hOURS the hOURS to set
	 */
	public void setHOURS(String hOURS) {
		HOURS = hOURS;
	}

	/**
	 * @return the oTHER
	 */
	public String getOTHER() {
		return OTHER;
	}

	/**
	 * @param oTHER the oTHER to set
	 */
	public void setOTHER(String oTHER) {
		OTHER = oTHER;
	}

	/**
	 * @return the oTHER_JA
	 */
	public String getOTHER_JA() {
		return OTHER_JA;
	}

	/**
	 * @param oTHER_JA the oTHER_JA to set
	 */
	public void setOTHER_JA(String oTHER_JA) {
		OTHER_JA = oTHER_JA;
	}

	/**
	 * @return the mANUFACTURER
	 */
	public String getMANUFACTURER() {
		return MANUFACTURER;
	}

	/**
	 * @param mANUFACTURER the mANUFACTURER to set
	 */
	public void setMANUFACTURER(String mANUFACTURER) {
		MANUFACTURER = mANUFACTURER;
	}

	/**
	 * @return the pIC_URL
	 */
	public String getPIC_URL() {
		return PIC_URL;
	}

	/**
	 * @param pIC_URL the pIC_URL to set
	 */
	public void setPIC_URL(String pIC_URL) {
		PIC_URL = pIC_URL;
	}

	/**
	 * @return the pIC
	 */
	public Blob getPIC() {
		return PIC;
	}

	/**
	 * @param pIC the pIC to set
	 */
	public void setPIC(Blob pIC) {
		PIC = pIC;
	}

	/**
	 * @return the aCCOUNT
	 */
	public String getACCOUNT() {
		return ACCOUNT;
	}

	/**
	 * @param aCCOUNT the aCCOUNT to set
	 */
	public void setACCOUNT(String aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	/**
	 * @return the oRDER_DATE
	 */
	public String getORDER_DATE() {
		return ORDER_DATE;
	}

	/**
	 * @param oRDER_DATE the oRDER_DATE to set
	 */
	public void setORDER_DATE(String oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}

	/**
	 * @return the wEB_DISP
	 */
	public String getWEB_DISP() {
		return WEB_DISP;
	}

	/**
	 * @param wEB_DISP the wEB_DISP to set
	 */
	public void setWEB_DISP(String wEB_DISP) {
		WEB_DISP = wEB_DISP;
	}

	/**
	 * @return the sELLER
	 */
	public String getSELLER() {
		return SELLER;
	}

	/**
	 * @param sELLER the sELLER to set
	 */
	public void setSELLER(String sELLER) {
		SELLER = sELLER;
	}

	/**
	 * @return the oRDER_PRICE
	 */
	public String getORDER_PRICE() {
		return ORDER_PRICE;
	}

	/**
	 * @param oRDER_PRICE the oRDER_PRICE to set
	 */
	public void setORDER_PRICE(String oRDER_PRICE) {
		ORDER_PRICE = oRDER_PRICE;
	}

	/**
	 * @return the oRDER_TRANS_COST
	 */
	public String getORDER_TRANS_COST() {
		return ORDER_TRANS_COST;
	}

	/**
	 * @param oRDER_TRANS_COST the oRDER_TRANS_COST to set
	 */
	public void setORDER_TRANS_COST(String oRDER_TRANS_COST) {
		ORDER_TRANS_COST = oRDER_TRANS_COST;
	}

	/**
	 * @return the pARTS_COST
	 */
	public String getPARTS_COST() {
		return PARTS_COST;
	}

	/**
	 * @param pARTS_COST the pARTS_COST to set
	 */
	public void setPARTS_COST(String pARTS_COST) {
		PARTS_COST = pARTS_COST;
	}

	/**
	 * @return the mAINTENANCE_COST
	 */
	public String getMAINTENANCE_COST() {
		return MAINTENANCE_COST;
	}

	/**
	 * @param mAINTENANCE_COST the mAINTENANCE_COST to set
	 */
	public void setMAINTENANCE_COST(String mAINTENANCE_COST) {
		MAINTENANCE_COST = mAINTENANCE_COST;
	}

	/**
	 * @return the oRDER_OUT_ORDER_COST
	 */
	public String getORDER_OUT_ORDER_COST() {
		return ORDER_OUT_ORDER_COST;
	}

	/**
	 * @param oRDER_OUT_ORDER_COST the oRDER_OUT_ORDER_COST to set
	 */
	public void setORDER_OUT_ORDER_COST(String oRDER_OUT_ORDER_COST) {
		ORDER_OUT_ORDER_COST = oRDER_OUT_ORDER_COST;
	}

	/**
	 * @return the oRDER_COST_PRICE
	 */
	public String getORDER_COST_PRICE() {
		return ORDER_COST_PRICE;
	}

	/**
	 * @param oRDER_COST_PRICE the oRDER_COST_PRICE to set
	 */
	public void setORDER_COST_PRICE(String oRDER_COST_PRICE) {
		ORDER_COST_PRICE = oRDER_COST_PRICE;
	}

	/**
	 * @return the sELL_TRANCE_COST
	 */
	public String getSELL_TRANCE_COST() {
		return SELL_TRANCE_COST;
	}

	/**
	 * @param sELL_TRANCE_COST the sELL_TRANCE_COST to set
	 */
	public void setSELL_TRANCE_COST(String sELL_TRANCE_COST) {
		SELL_TRANCE_COST = sELL_TRANCE_COST;
	}

	/**
	 * @return the sHIP_COST
	 */
	public String getSHIP_COST() {
		return SHIP_COST;
	}

	/**
	 * @param sHIP_COST the sHIP_COST to set
	 */
	public void setSHIP_COST(String sHIP_COST) {
		SHIP_COST = sHIP_COST;
	}

	/**
	 * @return the sELL_OUT_ORDER_COST
	 */
	public String getSELL_OUT_ORDER_COST() {
		return SELL_OUT_ORDER_COST;
	}

	/**
	 * @param sELL_OUT_ORDER_COST the sELL_OUT_ORDER_COST to set
	 */
	public void setSELL_OUT_ORDER_COST(String sELL_OUT_ORDER_COST) {
		SELL_OUT_ORDER_COST = sELL_OUT_ORDER_COST;
	}

	/**
	 * @return the iNS_COST
	 */
	public String getINS_COST() {
		return INS_COST;
	}

	/**
	 * @param iNS_COST the iNS_COST to set
	 */
	public void setINS_COST(String iNS_COST) {
		INS_COST = iNS_COST;
	}

	/**
	 * @return the fREIGHT_COST
	 */
	public String getFREIGHT_COST() {
		return FREIGHT_COST;
	}

	/**
	 * @param fREIGHT_COST the fREIGHT_COST to set
	 */
	public void setFREIGHT_COST(String fREIGHT_COST) {
		FREIGHT_COST = fREIGHT_COST;
	}

	/**
	 * @return the sELL_COST_PRICE
	 */
	public String getSELL_COST_PRICE() {
		return SELL_COST_PRICE;
	}

	/**
	 * @param sELL_COST_PRICE the sELL_COST_PRICE to set
	 */
	public void setSELL_COST_PRICE(String sELL_COST_PRICE) {
		SELL_COST_PRICE = sELL_COST_PRICE;
	}

	/**
	 * @return the sELL_PRICE
	 */
	public String getSELL_PRICE() {
		return SELL_PRICE;
	}

	/**
	 * @param sELL_PRICE the sELL_PRICE to set
	 */
	public void setSELL_PRICE(String sELL_PRICE) {
		SELL_PRICE = sELL_PRICE;
	}

	/**
	 * @return the wHOL_PRICE
	 */
	public String getWHOL_PRICE() {
		return WHOL_PRICE;
	}

	/**
	 * @param wHOL_PRICE the wHOL_PRICE to set
	 */
	public void setWHOL_PRICE(String wHOL_PRICE) {
		WHOL_PRICE = wHOL_PRICE;
	}

	/**
	 * @return the pROFIT
	 */
	public String getPROFIT() {
		return PROFIT;
	}

	/**
	 * @param pROFIT the pROFIT to set
	 */
	public void setPROFIT(String pROFIT) {
		PROFIT = pROFIT;
	}

	/**
	 * @return the bUYER
	 */
	public String getBUYER() {
		return BUYER;
	}

	/**
	 * @param bUYER the bUYER to set
	 */
	public void setBUYER(String bUYER) {
		BUYER = bUYER;
	}

	/**
	 * @return the oRDER_PAY_DATE
	 */
	public String getORDER_PAY_DATE() {
		return ORDER_PAY_DATE;
	}

	/**
	 * @param oRDER_PAY_DATE the oRDER_PAY_DATE to set
	 */
	public void setORDER_PAY_DATE(String oRDER_PAY_DATE) {
		ORDER_PAY_DATE = oRDER_PAY_DATE;
	}

	/**
	 * @return the sELL_PAY_DATE
	 */
	public String getSELL_PAY_DATE() {
		return SELL_PAY_DATE;
	}

	/**
	 * @param sELL_PAY_DATE the sELL_PAY_DATE to set
	 */
	public void setSELL_PAY_DATE(String sELL_PAY_DATE) {
		SELL_PAY_DATE = sELL_PAY_DATE;
	}

	/**
	 * @return the sELL_MONTH
	 */
	public String getSELL_MONTH() {
		return SELL_MONTH;
	}

	/**
	 * @param sELL_MONTH the sELL_MONTH to set
	 */
	public void setSELL_MONTH(String sELL_MONTH) {
		SELL_MONTH = sELL_MONTH;
	}

	/**
	 * @return the mEMO
	 */
	public String getMEMO() {
		return MEMO;
	}

	/**
	 * @param mEMO the mEMO to set
	 */
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}

	/**
	 * @return the dATA_FLG
	 */
	public String getDATA_FLG() {
		return DATA_FLG;
	}

	/**
	 * @param dATA_FLG the dATA_FLG to set
	 */
	public void setDATA_FLG(String dATA_FLG) {
		DATA_FLG = dATA_FLG;
	}

	/**
	 * @return the wEB_DISP_DATE
	 */
	public String getWEB_DISP_DATE() {
		return WEB_DISP_DATE;
	}

	/**
	 * @param wEB_DISP_DATE the wEB_DISP_DATE to set
	 */
	public void setWEB_DISP_DATE(String wEB_DISP_DATE) {
		WEB_DISP_DATE = wEB_DISP_DATE;
	}

	/**
	 * @return the sELLER_CODE
	 */
	public String getSELLER_CODE() {
		return SELLER_CODE;
	}

	/**
	 * @param sELLER_CODE the sELLER_CODE to set
	 */
	public void setSELLER_CODE(String sELLER_CODE) {
		SELLER_CODE = sELLER_CODE;
	}

	/**
	 * @return the bUYER_CODE
	 */
	public String getBUYER_CODE() {
		return BUYER_CODE;
	}

	/**
	 * @param bUYER_CODE the bUYER_CODE to set
	 */
	public void setBUYER_CODE(String bUYER_CODE) {
		BUYER_CODE = bUYER_CODE;
	}

	/**
	 * @return the tRAN_PLACE
	 */
	public String getTRAN_PLACE() {
		return TRAN_PLACE;
	}

	/**
	 * @param tRAN_PLACE the tRAN_PLACE to set
	 */
	public void setTRAN_PLACE(String tRAN_PLACE) {
		TRAN_PLACE = tRAN_PLACE;
	}

	/**
	 * @return the iNVOICE_NO
	 */
	public String getINVOICE_NO() {
		return INVOICE_NO;
	}

	/**
	 * @param iNVOICE_NO the iNVOICE_NO to set
	 */
	public void setINVOICE_NO(String iNVOICE_NO) {
		INVOICE_NO = iNVOICE_NO;
	}

	/**
	 * @return the rENTAL
	 */
	public String getRENTAL() {
		return RENTAL;
	}

	/**
	 * @param rENTAL the rENTAL to set
	 */
	public void setRENTAL(String rENTAL) {
		RENTAL = rENTAL;
	}

	/**
	 * @return the cONDITION_MAINTENANCE
	 */
	public String getCONDITION_MAINTENANCE() {
		return CONDITION_MAINTENANCE;
	}

	/**
	 * @param cONDITION_MAINTENANCE the cONDITION_MAINTENANCE to set
	 */
	public void setCONDITION_MAINTENANCE(String cONDITION_MAINTENANCE) {
		CONDITION_MAINTENANCE = cONDITION_MAINTENANCE;
	}

}
