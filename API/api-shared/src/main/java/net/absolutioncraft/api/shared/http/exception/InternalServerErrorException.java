package net.absolutioncraft.api.shared.http.exception;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public class InternalServerErrorException extends Exception implements HTTPException {
    private String reason;

    public InternalServerErrorException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return this.reason;
    }

    @Override
    public int statusCode() {
        return 500;
    }
}
