package net.absolutioncraft.api.shared.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@AllArgsConstructor @Getter
public class RedisWrapper<O> {
    String id;
    O object;
}
