package net.absolutioncraft.api.shared.http.exception;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class NotFoundException extends Exception implements HTTPException {
    private String reason;

    public NotFoundException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return this.reason;
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
