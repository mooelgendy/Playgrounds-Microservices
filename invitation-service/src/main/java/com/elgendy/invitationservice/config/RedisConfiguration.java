package com.elgendy.invitationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String redisHostname;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    protected JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = 
            new RedisStandaloneConfiguration(redisHostname, redisPort);
        
        JedisClientConfiguration jedisClientConfiguration = 
            JedisClientConfiguration.builder()
                .usePooling()
                .build();
        
        JedisConnectionFactory factory = 
            new JedisConnectionFactory(configuration, jedisClientConfiguration);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        
        return template;
    }
}