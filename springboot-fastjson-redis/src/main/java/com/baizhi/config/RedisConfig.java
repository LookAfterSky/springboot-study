package com.baizhi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;


@Configuration
@EnableCaching /*开启缓存支持*/
public class RedisConfig extends CachingConfigurerSupport {
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public KeyGenerator keyGenerator() {
        /*return new KeyGenerator((Object o,Method method,Object... objects)->{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getName()).append(method.getName());
            for (Object object : objects) {
                stringBuilder.append(object.toString());
            }
            return stringBuilder.toString();
        });*/
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuffer sb = new StringBuffer();
                sb.append(o.getClass().getName()).append(method.getName());
                Arrays.asList(objects).forEach(obj -> sb.append(obj.toString()));
               /* for (Object object : objects) {
                    sb.append(object.toString());
                }*/
                return sb.toString();
            }
        };

    }

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }


    //缓存管理器
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder build = RedisCacheManager.
                RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
        HashSet<String> codeNameCache = new HashSet<>();
        codeNameCache.add("codeNameCache");
        build.initialCacheNames(codeNameCache);
        return build.build();
    }

    /*redis Template配置*/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {

        //配置redis template
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);//key序列化
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());//value序列化
        redisTemplate.setHashKeySerializer(redisSerializer);//hashKey序列化
        redisTemplate.setHashValueSerializer(redisSerializer);//hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
