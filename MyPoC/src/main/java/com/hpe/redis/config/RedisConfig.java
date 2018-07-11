package com.hpe.redis.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import com.hpe.sessionMgmt.vo.Message;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource(name = "redis-config", value = "classpath:/iot-redis.properties")
// @PropertySource("classpath:iot-redis.properties")
//Redis Test call is there from ReviwService - http://localhost:8080/

public class RedisConfig
{

    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Resource
    ConfigurableEnvironment environment;

    @Value("${redis.host:localhost}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private int redisPort;

    @Value("${redis.server.password:password}")
    private String redisServerPassword;

    @Value("${isHAMode:false}")
    private boolean isHAMode;// = false;

    @Value("${redis.connection.pool.size:100}")
    private int redisPoolSize;

    public PropertiesPropertySource propertySource()
    {
        return (PropertiesPropertySource) environment.getPropertySources().get("redis-config");
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        JedisConnectionFactory jedisConFactory = null;
        /*if (isHAMode)
        {
            jedisConFactory = new JedisConnectionFactory(sentinelConfiguration());
            jedisConFactory.setPassword(environment.getProperty("redis.password"));
        } else*/
        {
            logger.info("Redis simplex mode host ={} and port {}", redisHost, redisPort);
            jedisConFactory = new JedisConnectionFactory();
            jedisConFactory.setHostName(redisHost);
            jedisConFactory.setPort(redisPort);
            //jedisConFactory.setPassword(redisServerPassword);
        }
        return jedisConFactory;
    }

   /* @Bean
    public RedisSentinelConfiguration sentinelConfiguration()
    {
        return new RedisSentinelConfiguration(propertySource());
    }*/

    @Bean
    public JedisPoolConfig jedisPoolConfig()
    {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPoolSize);
        return jedisPoolConfig;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate()
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    RedisCacheManager cacheManager()
    {
        return new RedisCacheManager(redisTemplate());
    }

    @Bean
    public RedisTemplate<String, Message> redisMessageTemplate()
    {
        final RedisTemplate<String, Message> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));
        template.afterPropertiesSet();
        return template;
    }
}
