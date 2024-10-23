package distri.gestion_usuarios.config;

import distri.beans.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/*
 EStoy definiendo las claves del cache de manera programática para
identificar claramente qué está siendo cacheado.
Usando un KeyGenerator personalizado.
        // Genera una clave única usando el nombre de la clase, el método y los parámetros
*
*
* */


@Configuration
public class RedisConfig {

// Usamos configuracion para el tiempo ttl

    @Value("${cache.ttl.segundos}")
    private int ttlSegundos;

    @Value("${cache.ttl.minutes}")
    private int ttlMinutes;

    @Value("${cache.ttl.horas}")
    private int ttlHoras;


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //Al final decante por usar  segundos, mas facil.
        int ttlTotalSegundos = ttlSegundos + (ttlMinutes * 60) + (ttlHoras * 3600);

        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttlTotalSegundos))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }


    /*CACHE MANAGER:
    generacion de clave por codigo.
    Sera su id.
    Le doy un nombre a la FUERZA porque no funcionaba
    */

    @Bean(name = "keyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            // Si el primer parámetro es un UsuarioDTO y tiene un ID, usamos ese ID como clave
            if (params.length > 0 && params[0] instanceof UsuarioDTO) {
                UsuarioDTO usuario = (UsuarioDTO) params[0];
                return target.getClass().getSimpleName() + "_" + usuario.getId();
            }
            // Si el parámetro es un ID (Long), usamos directamente ese ID como clave
            if (params.length > 0 && params[0] instanceof Long) {
                return target.getClass().getSimpleName() + "_" + params[0];
            }
            // todo falla? hacemos clave genérica basada en los parámetros
            return Arrays.toString(params);
        };
    }


}


