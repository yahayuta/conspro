package gae.ftc.storage;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @deprecated
 * @author yasun
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class RentalOrder
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String ID;//ID

    @Persistent
    private String RENTAL_INVENTORY_ID;//レンタル機コード

    @Persistent
    private String NAME;//型式
   
    @Persistent
    private String SERIALNO;//号機
    
    @Persistent
    private String PRICE_DAY;//日単価

    @Persistent
    private String PRICE_MONTH;//月単価
    
    @Persistent
    private String ORDER_TYPE;//注文区分
    
    @Persistent
    private String OUT_DATE;//出庫日
    
    @Persistent
    private String IN_DATE;//返却日
    
    @Persistent
    private String START_DATE;//レンタル開始日
    
    @Persistent
    private String END_DATE;//レンタル終了日
    
    @Persistent
    private String AMOUNT;//金額（税別）
    
    @Persistent
    private String TRANS_FEE;//搬入運賃

    @Persistent
    private String RETURN_FEE;//返却運賃
    
    @Persistent
    private String ORDER_NAME;//ユーザー名
    
    @Persistent
    private String ORDER_PLACE;//搬入先
    
    @Persistent
    private String CLIENT_CODE;//顧客コード

    @Persistent
    private String CLIENT_NAME;//顧客名
    
    @Persistent
    private String HOURS_START;//稼動時間開始時
    
    @Persistent
    private String HOURS_END;//稼動時間終了時
    
    @Persistent
    private String DATA_FLG;//データフラグ

    @Persistent
    private String MEMO;//備考欄

    @Persistent
    private String PRICE;//単価（税別）
    
    @Persistent
    private String COUNT;//回数

    @Persistent
    private String FEE1;//その他費用1
    
    @Persistent
    private String FEE2;//その他費用2
    
    @Persistent
    private String FEE3;//その他費用3
    
    @Persistent
    private String FEE4;//その他費用4
    
    @Persistent
    private String FEE5;//その他費用5
    
    @Persistent
    private String FEE6;//その他費用6
    @Persistent
    private String FEE1_NAME;//その他費用名1
    
    @Persistent
    private String FEE2_NAME;//その他費用名2
    
    @Persistent
    private String FEE3_NAME;//その他費用名3
    
    @Persistent
    private String FEE4_NAME;//その他費用名4
    
    @Persistent
    private String FEE5_NAME;//その他費用名5
    
    @Persistent
    private String FEE6_NAME;//その他費用名6
    
    @Persistent
    private String TOTAL;//合計金額（税別）

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the rENTAL_INVENTORY_ID
	 */
	public String getRENTAL_INVENTORY_ID() {
		return RENTAL_INVENTORY_ID;
	}

	/**
	 * @param rENTAL_INVENTORY_ID the rENTAL_INVENTORY_ID to set
	 */
	public void setRENTAL_INVENTORY_ID(String rENTAL_INVENTORY_ID) {
		RENTAL_INVENTORY_ID = rENTAL_INVENTORY_ID;
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
	 * @return the pRICE_DAY
	 */
	public String getPRICE_DAY() {
		return PRICE_DAY;
	}

	/**
	 * @param pRICE_DAY the pRICE_DAY to set
	 */
	public void setPRICE_DAY(String pRICE_DAY) {
		PRICE_DAY = pRICE_DAY;
	}

	/**
	 * @return the pRICE_MONTH
	 */
	public String getPRICE_MONTH() {
		return PRICE_MONTH;
	}

	/**
	 * @param pRICE_MONTH the pRICE_MONTH to set
	 */
	public void setPRICE_MONTH(String pRICE_MONTH) {
		PRICE_MONTH = pRICE_MONTH;
	}

	/**
	 * @return the oRDER_TYPE
	 */
	public String getORDER_TYPE() {
		return ORDER_TYPE;
	}

	/**
	 * @param oRDER_TYPE the oRDER_TYPE to set
	 */
	public void setORDER_TYPE(String oRDER_TYPE) {
		ORDER_TYPE = oRDER_TYPE;
	}

	/**
	 * @return the oUT_DATE
	 */
	public String getOUT_DATE() {
		return OUT_DATE;
	}

	/**
	 * @param oUT_DATE the oUT_DATE to set
	 */
	public void setOUT_DATE(String oUT_DATE) {
		OUT_DATE = oUT_DATE;
	}

	/**
	 * @return the iN_DATE
	 */
	public String getIN_DATE() {
		return IN_DATE;
	}

	/**
	 * @param iN_DATE the iN_DATE to set
	 */
	public void setIN_DATE(String iN_DATE) {
		IN_DATE = iN_DATE;
	}

	/**
	 * @return the sTART_DATE
	 */
	public String getSTART_DATE() {
		return START_DATE;
	}

	/**
	 * @param sTART_DATE the sTART_DATE to set
	 */
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}

	/**
	 * @return the eND_DATE
	 */
	public String getEND_DATE() {
		return END_DATE;
	}

	/**
	 * @param eND_DATE the eND_DATE to set
	 */
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}

	/**
	 * @return the aMOUNT
	 */
	public String getAMOUNT() {
		return AMOUNT;
	}

	/**
	 * @param aMOUNT the aMOUNT to set
	 */
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}

	/**
	 * @return the tRANS_FEE
	 */
	public String getTRANS_FEE() {
		return TRANS_FEE;
	}

	/**
	 * @param tRANS_FEE the tRANS_FEE to set
	 */
	public void setTRANS_FEE(String tRANS_FEE) {
		TRANS_FEE = tRANS_FEE;
	}

	/**
	 * @return the rETURN_FEE
	 */
	public String getRETURN_FEE() {
		return RETURN_FEE;
	}

	/**
	 * @param rETURN_FEE the rETURN_FEE to set
	 */
	public void setRETURN_FEE(String rETURN_FEE) {
		RETURN_FEE = rETURN_FEE;
	}

	/**
	 * @return the oRDER_NAME
	 */
	public String getORDER_NAME() {
		return ORDER_NAME;
	}

	/**
	 * @param oRDER_NAME the oRDER_NAME to set
	 */
	public void setORDER_NAME(String oRDER_NAME) {
		ORDER_NAME = oRDER_NAME;
	}

	/**
	 * @return the oRDER_PLACE
	 */
	public String getORDER_PLACE() {
		return ORDER_PLACE;
	}

	/**
	 * @param oRDER_PLACE the oRDER_PLACE to set
	 */
	public void setORDER_PLACE(String oRDER_PLACE) {
		ORDER_PLACE = oRDER_PLACE;
	}

	/**
	 * @return the cLIENT_CODE
	 */
	public String getCLIENT_CODE() {
		return CLIENT_CODE;
	}

	/**
	 * @param cLIENT_CODE the cLIENT_CODE to set
	 */
	public void setCLIENT_CODE(String cLIENT_CODE) {
		CLIENT_CODE = cLIENT_CODE;
	}

	/**
	 * @return the cLIENT_NAME
	 */
	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}

	/**
	 * @param cLIENT_NAME the cLIENT_NAME to set
	 */
	public void setCLIENT_NAME(String cLIENT_NAME) {
		CLIENT_NAME = cLIENT_NAME;
	}

	/**
	 * @return the hOURS_START
	 */
	public String getHOURS_START() {
		return HOURS_START;
	}

	/**
	 * @param hOURS_START the hOURS_START to set
	 */
	public void setHOURS_START(String hOURS_START) {
		HOURS_START = hOURS_START;
	}

	/**
	 * @return the hOURS_END
	 */
	public String getHOURS_END() {
		return HOURS_END;
	}

	/**
	 * @param hOURS_END the hOURS_END to set
	 */
	public void setHOURS_END(String hOURS_END) {
		HOURS_END = hOURS_END;
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
	 * @return the cOUNT
	 */
	public String getCOUNT() {
		return COUNT;
	}

	/**
	 * @param cOUNT the cOUNT to set
	 */
	public void setCOUNT(String cOUNT) {
		COUNT = cOUNT;
	}

	/**
	 * @return the fEE1
	 */
	public String getFEE1() {
		return FEE1;
	}

	/**
	 * @param fEE1 the fEE1 to set
	 */
	public void setFEE1(String fEE1) {
		FEE1 = fEE1;
	}

	/**
	 * @return the fEE2
	 */
	public String getFEE2() {
		return FEE2;
	}

	/**
	 * @param fEE2 the fEE2 to set
	 */
	public void setFEE2(String fEE2) {
		FEE2 = fEE2;
	}

	/**
	 * @return the fEE3
	 */
	public String getFEE3() {
		return FEE3;
	}

	/**
	 * @param fEE3 the fEE3 to set
	 */
	public void setFEE3(String fEE3) {
		FEE3 = fEE3;
	}

	/**
	 * @return the fEE4
	 */
	public String getFEE4() {
		return FEE4;
	}

	/**
	 * @param fEE4 the fEE4 to set
	 */
	public void setFEE4(String fEE4) {
		FEE4 = fEE4;
	}

	/**
	 * @return the fEE5
	 */
	public String getFEE5() {
		return FEE5;
	}

	/**
	 * @param fEE5 the fEE5 to set
	 */
	public void setFEE5(String fEE5) {
		FEE5 = fEE5;
	}

	/**
	 * @return the fEE6
	 */
	public String getFEE6() {
		return FEE6;
	}

	/**
	 * @param fEE6 the fEE6 to set
	 */
	public void setFEE6(String fEE6) {
		FEE6 = fEE6;
	}

	/**
	 * @return the fEE1_NAME
	 */
	public String getFEE1_NAME() {
		return FEE1_NAME;
	}

	/**
	 * @param fEE1_NAME the fEE1_NAME to set
	 */
	public void setFEE1_NAME(String fEE1_NAME) {
		FEE1_NAME = fEE1_NAME;
	}

	/**
	 * @return the fEE2_NAME
	 */
	public String getFEE2_NAME() {
		return FEE2_NAME;
	}

	/**
	 * @param fEE2_NAME the fEE2_NAME to set
	 */
	public void setFEE2_NAME(String fEE2_NAME) {
		FEE2_NAME = fEE2_NAME;
	}

	/**
	 * @return the fEE3_NAME
	 */
	public String getFEE3_NAME() {
		return FEE3_NAME;
	}

	/**
	 * @param fEE3_NAME the fEE3_NAME to set
	 */
	public void setFEE3_NAME(String fEE3_NAME) {
		FEE3_NAME = fEE3_NAME;
	}

	/**
	 * @return the fEE4_NAME
	 */
	public String getFEE4_NAME() {
		return FEE4_NAME;
	}

	/**
	 * @param fEE4_NAME the fEE4_NAME to set
	 */
	public void setFEE4_NAME(String fEE4_NAME) {
		FEE4_NAME = fEE4_NAME;
	}

	/**
	 * @return the fEE5_NAME
	 */
	public String getFEE5_NAME() {
		return FEE5_NAME;
	}

	/**
	 * @param fEE5_NAME the fEE5_NAME to set
	 */
	public void setFEE5_NAME(String fEE5_NAME) {
		FEE5_NAME = fEE5_NAME;
	}

	/**
	 * @return the fEE6_NAME
	 */
	public String getFEE6_NAME() {
		return FEE6_NAME;
	}

	/**
	 * @param fEE6_NAME the fEE6_NAME to set
	 */
	public void setFEE6_NAME(String fEE6_NAME) {
		FEE6_NAME = fEE6_NAME;
	}

	/**
	 * @return the tOTAL
	 */
	public String getTOTAL() {
		return TOTAL;
	}

	/**
	 * @param tOTAL the tOTAL to set
	 */
	public void setTOTAL(String tOTAL) {
		TOTAL = tOTAL;
	}

}
