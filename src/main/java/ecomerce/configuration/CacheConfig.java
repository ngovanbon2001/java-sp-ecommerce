package ecomerce.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String VNEID_USER_INFO = "VNEID_USER_INFO";
    @Bean(name = VNEID_USER_INFO)
    Cache cacheVneidUserInfo() {
        return new ConcurrentMapCache(VNEID_USER_INFO,
                CacheBuilder.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(10000)
                        .build().asMap(),
                false);
    }
}
