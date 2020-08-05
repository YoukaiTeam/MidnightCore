package net.absolutioncraft.api.shared.http.config;

import com.google.inject.Singleton;
import lombok.Getter;

/**
 * @author MelonDev
 * @since 0.0.1
 */
@Getter @Singleton
public class WebServerConfig {
    private final String host = "http://api.absolutioncraft.net/";
    private final Integer port = 3000;
    private final String suffix = "api";
}
