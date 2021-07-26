package com.mazentop.plugins.i18n.run;

import com.mazentop.plugins.i18n.cache.I18nCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/30 10:01
 */
@Component
public class I18nCommandLineRunner implements CommandLineRunner  {
    @Override
    public void run(String... args) {
        I18nCache.loadAll();
    }
}
