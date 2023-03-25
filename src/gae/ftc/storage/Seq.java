package gae.ftc.storage;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * シーケンスエンティティ
 * @author yishizu
 * @deprecated
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Seq {
	/** シーケンスID：顧客*/
	public static final String SEQ_ID_CLIENT = "SEQ_ID_CLIENT";
	/** シーケンスID：PI用請求番号*/
	public static final String SEQ_ID_INVOICE_NO = "SEQ_ID_INVOICE_NO";
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String SEQ_ID;

    @Persistent
    private long SEQ;

	/**
	 * @return the sEQ_ID
	 */
	public String getSEQ_ID() {
		return SEQ_ID;
	}

	/**
	 * @param sEQ_ID the sEQ_ID to set
	 */
	public void setSEQ_ID(String sEQ_ID) {
		SEQ_ID = sEQ_ID;
	}

	/**
	 * @return the sEQ
	 */
	public long getSEQ() {
		return SEQ;
	}

	/**
	 * @param sEQ the sEQ to set
	 */
	public void setSEQ(long sEQ) {
		SEQ = sEQ;
	}
    
    
}
