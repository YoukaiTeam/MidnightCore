package net.absolutioncraft.api.shared.http.response;

import net.absolutioncraft.api.shared.http.exception.HTTPException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public class RestResponse<T> {
    private HTTPException throwedException;
    private Status status;
    private T response;

    public RestResponse(HTTPException throwedException, @NotNull Status status, @Nullable T response) {
        this.throwedException = throwedException;
        this.status = status;
        this.response = response;
    }

    @NotNull
    public HTTPException getThrowedException() {
        return throwedException;
    }

    public Status getStatus() {
        return status;
    }

    public T getResponse() {
        return response;
    }

    public enum Status {
        SUCCESS, ERROR
    }
}
