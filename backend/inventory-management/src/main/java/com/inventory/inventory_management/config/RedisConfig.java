package com.inventory.inventory_management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.inventory_management.dto.ProductResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductResponseDTO> redisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, ProductResponseDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(
                new Jackson2JsonRedisSerializer<>(new ObjectMapper(), ProductResponseDTO.class)
        );
        return template;
    }
}