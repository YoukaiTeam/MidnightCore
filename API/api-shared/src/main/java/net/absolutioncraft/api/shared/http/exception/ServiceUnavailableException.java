package net.absolutioncraft.api.shared.http.exception;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public class ServiceUnavailableException extends Exception implements HTTPException {
    @Override
    public int statusCode() {
        return 503;
    }
}
