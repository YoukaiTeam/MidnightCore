package net.absolutioncraft.api.shared;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class SharedModule extends AbstractModule {
    @Override
    public void configure() {
        bind(ExecutorService.class).to(ListeningExecutorService.class).in(Scopes.SINGLETON);
        bind(ListeningExecutorService.class).toInstance(MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())));

        bind(Gson.class).toProvider(() -> new GsonBuilder()
                .serializeNulls()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create()).in(Scopes.SINGLETON);
    }
}
