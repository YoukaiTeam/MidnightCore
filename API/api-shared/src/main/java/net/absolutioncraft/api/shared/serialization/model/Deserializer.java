package net.absolutioncraft.api.shared.serialization.model;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public interface Deserializer<T> {
    T deserialize(String json);
}
