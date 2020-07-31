package net.absolutioncraft.api.shared.concurrent;

public interface Callback<T> {
    void call(T t);

    /**
     * Throw callback exception to system log.
     * @param throwable an {@link Throwable} object.
     */
    default void handleException(final Throwable throwable) {
        System.out.println("Internal error occurred while handling callback.");
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            System.out.println(stackTraceElement.getClassName() + "|" + stackTraceElement.getLineNumber());
        }
    }
}
