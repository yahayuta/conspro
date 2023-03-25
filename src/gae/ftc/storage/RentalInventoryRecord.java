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
public class RentalInventoryRecord
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String ID;//ID

    @Persistent
    private String NAME;//型式

    @Persistent
    private String TYPE;//分類
    
    @Persistent
    private String YEAR;//年式

    @Persistent
    private String SERIALNO;//号機

    @Persistent
    private String HOURS;//稼動時間

    @Persistent
    private String OTHER_JA;//詳細日本語
    
    @Persistent
    private String MANUFACTURER;//メーカー
    
    @Persistent
    private String PIC_URL;//写真アドレス

    @Persistent
    private String SELLER_CODE;//仕入先コード
    
    @Persistent
    private String SELLER;//仕入先
    
    @Persistent
    private String DATA_FLG;//データフラグ
    
    @Persistent
    private String PRICE_DAY;//日単価

    @Persistent
    private String PRICE_MONTH;//月単価
   
    @Persistent
    private String STATUS;//ステータス

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
	 * @return the sTATUS
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * @param sTATUS the sTATUS to set
	 */
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
    
}
