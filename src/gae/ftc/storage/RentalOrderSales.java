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
public class RentalOrderSales
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String ID;//ID

    @Persistent
    private String RENTAL_ORDER_ID;//レンタル注文ID
    
    @Persistent
    private String RENTAL_INVENTORY_ID;//レンタル機ID

    @Persistent
    private String AMOUNT;//金額（税別）
    
    @Persistent
    private String SALES_MONTH;//売上月
    
    @Persistent
    private String CLIENT_NAME;//売上先
 
    @Persistent
    private String RENTAL_INVENTORY_CODE;//在庫ID
    
    @Persistent
    private String DATA_FLG;//データフラグ

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
	 * @return the rENTAL_ORDER_ID
	 */
	public String getRENTAL_ORDER_ID() {
		return RENTAL_ORDER_ID;
	}

	/**
	 * @param rENTAL_ORDER_ID the rENTAL_ORDER_ID to set
	 */
	public void setRENTAL_ORDER_ID(String rENTAL_ORDER_ID) {
		RENTAL_ORDER_ID = rENTAL_ORDER_ID;
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
	 * @return the sALES_MONTH
	 */
	public String getSALES_MONTH() {
		return SALES_MONTH;
	}

	/**
	 * @param sALES_MONTH the sALES_MONTH to set
	 */
	public void setSALES_MONTH(String sALES_MONTH) {
		SALES_MONTH = sALES_MONTH;
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
	 * @return the rENTAL_INVENTORY_CODE
	 */
	public String getRENTAL_INVENTORY_CODE() {
		return RENTAL_INVENTORY_CODE;
	}

	/**
	 * @param rENTAL_INVENTORY_CODE the rENTAL_INVENTORY_CODE to set
	 */
	public void setRENTAL_INVENTORY_CODE(String rENTAL_INVENTORY_CODE) {
		RENTAL_INVENTORY_CODE = rENTAL_INVENTORY_CODE;
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
