package com.baizhi.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashSet;


@Configuration
@EnableCaching /*开启缓存支持*/
public class RedisConfig extends CachingConfigurerSupport {
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public KeyGenerator keyGenerator(){
        KeyGenerator keyGenerator = new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuffer sb = new StringBuffer();
                sb.append(o.getClass().getName()).append(method.getName());
                for (Object object : objects) {
                    sb.append(object.toString());
                }
                return sb.toString();
            }
        };
        return keyGenerator;
    }

    //缓存管理器
    @Bean
    public CacheManager cacheManager(){
        RedisCacheManager.RedisCacheManagerBuilder build = RedisCacheManager.
                RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
        HashSet<String> codeNameCache = new HashSet<String>() {
            {
                add("codeNameCache");
            }
        };
        build.initialCacheNames(codeNameCache);
        return build.build();
    }
    /*redis Template配置*/
    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        //设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //配置redis template
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(redisSerializer);//hashKey序列化
        redisTemplate.setHashValueSerializer(redisSerializer);//hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
