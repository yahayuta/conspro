package gae.ftc.storage;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 顧客エンティティ
 * @author yishizu
 * @deprecated
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Client {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String CLIENT_CODE;

    @Persistent
    private String CLIENT_TYPE;
    
    @Persistent
    private String CREDIT;
    
    @Persistent
    private String COUNTRY;
    
    @Persistent
    private String SEQ;
    
    @Persistent
    private String NAME;
    
    @Persistent
    private String NAME2;
    
    @Persistent
    private String NAME3;
    
    @Persistent
    private String NAME4;
    
    @Persistent
    private String NAME5;
    
    @Persistent
    private String COMPANY;
    
    @Persistent
    private String OFFICE;
    
    @Persistent
    private String ADDRESS;
    
    @Persistent
    private String ZIP;
    
    @Persistent
    private String TEL;
    
    @Persistent
    private String FAX;
    
    @Persistent
    private String MAIL;
    
    @Persistent
    private String COMMENT;

    @Persistent
    private String DATA_FLG;//データフラグ

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
	 * @return the cLIENT_TYPE
	 */
	public String getCLIENT_TYPE() {
		return CLIENT_TYPE;
	}

	/**
	 * @param cLIENT_TYPE the cLIENT_TYPE to set
	 */
	public void setCLIENT_TYPE(String cLIENT_TYPE) {
		CLIENT_TYPE = cLIENT_TYPE;
	}

	/**
	 * @return the cREDIT
	 */
	public String getCREDIT() {
		return CREDIT;
	}

	/**
	 * @param cREDIT the cREDIT to set
	 */
	public void setCREDIT(String cREDIT) {
		CREDIT = cREDIT;
	}

	/**
	 * @return the cOUNTRY
	 */
	public String getCOUNTRY() {
		return COUNTRY;
	}

	/**
	 * @param cOUNTRY the cOUNTRY to set
	 */
	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	/**
	 * @return the sEQ
	 */
	public String getSEQ() {
		return SEQ;
	}

	/**
	 * @param sEQ the sEQ to set
	 */
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
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
	 * @return the nAME2
	 */
	public String getNAME2() {
		return NAME2;
	}

	/**
	 * @param nAME2 the nAME2 to set
	 */
	public void setNAME2(String nAME2) {
		NAME2 = nAME2;
	}

	/**
	 * @return the nAME3
	 */
	public String getNAME3() {
		return NAME3;
	}

	/**
	 * @param nAME3 the nAME3 to set
	 */
	public void setNAME3(String nAME3) {
		NAME3 = nAME3;
	}

	/**
	 * @return the nAME4
	 */
	public String getNAME4() {
		return NAME4;
	}

	/**
	 * @param nAME4 the nAME4 to set
	 */
	public void setNAME4(String nAME4) {
		NAME4 = nAME4;
	}

	/**
	 * @return the nAME5
	 */
	public String getNAME5() {
		return NAME5;
	}

	/**
	 * @param nAME5 the nAME5 to set
	 */
	public void setNAME5(String nAME5) {
		NAME5 = nAME5;
	}

	/**
	 * @return the cOMPANY
	 */
	public String getCOMPANY() {
		return COMPANY;
	}

	/**
	 * @param cOMPANY the cOMPANY to set
	 */
	public void setCOMPANY(String cOMPANY) {
		COMPANY = cOMPANY;
	}

	/**
	 * @return the oFFICE
	 */
	public String getOFFICE() {
		return OFFICE;
	}

	/**
	 * @param oFFICE the oFFICE to set
	 */
	public void setOFFICE(String oFFICE) {
		OFFICE = oFFICE;
	}

	/**
	 * @return the aDDRESS
	 */
	public String getADDRESS() {
		return ADDRESS;
	}

	/**
	 * @param aDDRESS the aDDRESS to set
	 */
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	/**
	 * @return the zIP
	 */
	public String getZIP() {
		return ZIP;
	}

	/**
	 * @param zIP the zIP to set
	 */
	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	/**
	 * @return the tEL
	 */
	public String getTEL() {
		return TEL;
	}

	/**
	 * @param tEL the tEL to set
	 */
	public void setTEL(String tEL) {
		TEL = tEL;
	}

	/**
	 * @return the fAX
	 */
	public String getFAX() {
		return FAX;
	}

	/**
	 * @param fAX the fAX to set
	 */
	public void setFAX(String fAX) {
		FAX = fAX;
	}

	/**
	 * @return the mAIL
	 */
	public String getMAIL() {
		return MAIL;
	}

	/**
	 * @param mAIL the mAIL to set
	 */
	public void setMAIL(String mAIL) {
		MAIL = mAIL;
	}

	/**
	 * @return the cOMMENT
	 */
	public String getCOMMENT() {
		return COMMENT;
	}

	/**
	 * @param cOMMENT the cOMMENT to set
	 */
	public void setCOMMENT(String cOMMENT) {
		COMMENT = cOMMENT;
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

}
