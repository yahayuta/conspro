package gae.ftc.storage;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @deprecated
 * @author yasun
 */
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF(){}

    public static PersistenceManagerFactory getInstance() {
        return pmfInstance;
    }
}
