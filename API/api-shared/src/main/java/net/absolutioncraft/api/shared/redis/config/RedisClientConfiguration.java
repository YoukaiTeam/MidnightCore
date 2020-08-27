package net.absolutioncraft.api.shared.redis.config;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public class RedisClientConfiguration {

    private static final String ADDRESS = "localhost";
    private static final Integer PORT = 6379;

    public String getAddress() {
        return ADDRESS;
    }

    public Integer getPort() {
        return PORT;
    }
}