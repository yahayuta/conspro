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
public class DailyReport
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String DATE;//ID

    @Persistent
    private String WORK_DATE;//日時
    
    @Persistent
    private String NAME;//型式

    @Persistent
    private String SERIALNO;//号機

    @Persistent
    private String WORKER;//担当者
    
    @Persistent
    private String MEMO;//作業内容

    @Persistent
    private String DATA_FLG;//データフラグ

    @Persistent
    private String HOURS;//稼動時間
    
    @Persistent
    private String CLIENT_CODE;//顧客コード
    
    @Persistent
    private String CLIENT_NAME;//顧客名

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
	 * @return the wORK_DATE
	 */
	public String getWORK_DATE() {
		return WORK_DATE;
	}

	/**
	 * @param wORK_DATE the wORK_DATE to set
	 */
	public void setWORK_DATE(String wORK_DATE) {
		WORK_DATE = wORK_DATE;
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
	 * @return the wORKER
	 */
	public String getWORKER() {
		return WORKER;
	}

	/**
	 * @param wORKER the wORKER to set
	 */
	public void setWORKER(String wORKER) {
		WORKER = wORKER;
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
}
