package com.mazentop.plugins.cache;

import com.mazentop.CmsConfig;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * @author zhaoqt
 */
public class EhCacheFactoryBean implements FactoryBean<CacheManager>, InitializingBean, DisposableBean {

    protected final Log logger = LogFactory.getLog(getClass());

    @Nullable
    private Resource configLocation;

    @Nullable
    private String cacheManagerName;

    private boolean acceptExisting = false;

    private boolean shared = false;

    @Nullable
    private CacheManager cacheManager;

    private boolean locallyManaged = true;

    private CmsConfig cmsConfig;


    public EhCacheFactoryBean(CmsConfig cmsConfig) {
        this.cmsConfig = cmsConfig;
    }


    /**
     * Set the location of the EhCache config file. A typical value is "/WEB-INF/ehcache.xml".
     * <p>Default is "ehcache.xml" in the root of the class path, or if not found,
     * "ehcache-failsafe.xml" in the EhCache jar (default EhCache initialization).
     * @see CacheManager#create(java.io.InputStream)
     * @see CacheManager#CacheManager(java.io.InputStream)
     */
    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    /**
     * Set the name of the EhCache CacheManager (if a specific name is desired).
     * @see Configuration#setName(String)
     */
    public void setCacheManagerName(String cacheManagerName) {
        this.cacheManagerName = cacheManagerName;
    }

    /**
     * Set whether an existing EhCache CacheManager of the same name will be accepted
     * for this EhCacheManagerFactoryBean setup. Default is "false".
     * <p>Typically used in combination with {@link #setCacheManagerName "cacheManagerName"}
     * but will simply work with the default CacheManager name if none specified.
     * All references to the same CacheManager name (or the same default) in the
     * same ClassLoader space will share the specified CacheManager then.
     * @see #setCacheManagerName
     * #see #setShared
     * @see CacheManager#getCacheManager(String)
     * @see CacheManager#CacheManager()
     */
    public void setAcceptExisting(boolean acceptExisting) {
        this.acceptExisting = acceptExisting;
    }

    /**
     * Set whether the EhCache CacheManager should be shared (as a singleton at the
     * ClassLoader level) or independent (typically local within the application).
     * Default is "false", creating an independent local instance.
     * <p><b>NOTE:</b> This feature allows for sharing this EhCacheManagerFactoryBean's
     * CacheManager with any code calling <code>CacheManager.create()</code> in the same
     * ClassLoader space, with no need to agree on a specific CacheManager name.
     * However, it only supports a single EhCacheManagerFactoryBean involved which will
     * control the lifecycle of the underlying CacheManager (in particular, its shutdown).
     * <p>This flag overrides {@link #setAcceptExisting "acceptExisting"} if both are set,
     * since it indicates the 'stronger' mode of sharing.
     * @see #setCacheManagerName
     * @see #setAcceptExisting
     * @see CacheManager#create()
     * @see CacheManager#CacheManager()
     */
    public void setShared(boolean shared) {
        this.shared = shared;
    }


    @Override
    public void afterPropertiesSet() throws CacheException {
        if (logger.isInfoEnabled()) {
            logger.info("Initializing EhCache CacheManager" +
                    (this.cacheManagerName != null ? " '" + this.cacheManagerName + "'" : ""));
        }

        Configuration configuration = (this.configLocation != null ?
                EhCacheManagerUtils.parseConfiguration(this.configLocation) : ConfigurationFactory.parseConfiguration());
        if (this.cacheManagerName != null) {
            configuration.setName(this.cacheManagerName);
        }

        // 淇敼 纾佺洏鐩綍鍙互璇诲彇閰嶇疆鏂囦欢
        DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
        configuration.diskStore(diskStoreConfiguration.path(cmsConfig.getEhcacheDiskStore()));

        if (this.shared) {
            // Old-school EhCache singleton sharing...
            // No way to find out whether we actually created a new CacheManager
            // or just received an existing singleton reference.
            this.cacheManager = CacheManager.create(configuration);
        }
        else if (this.acceptExisting) {
            // EhCache 2.5+: Reusing an existing CacheManager of the same name.
            // Basically the same code as in CacheManager.getInstance(String),
            // just storing whether we're dealing with an existing instance.
            synchronized (CacheManager.class) {
                this.cacheManager = CacheManager.getCacheManager(this.cacheManagerName);
                if (this.cacheManager == null) {
                    this.cacheManager = new CacheManager(configuration);
                }
                else {
                    this.locallyManaged = false;
                }
            }
        }
        else {
            // Throwing an exception if a CacheManager of the same name exists already...
            this.cacheManager = new CacheManager(configuration);
        }
    }


    @Override
    @Nullable
    public CacheManager getObject() {
        return this.cacheManager;
    }

    @Override
    public Class<? extends CacheManager> getObjectType() {
        return (this.cacheManager != null ? this.cacheManager.getClass() : CacheManager.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    @Override
    public void destroy() {
        if (this.cacheManager != null && this.locallyManaged) {
            if (logger.isInfoEnabled()) {
                logger.info("Shutting down EhCache CacheManager" +
                        (this.cacheManagerName != null ? " '" + this.cacheManagerName + "'" : ""));
            }
            this.cacheManager.shutdown();
        }
    }

}