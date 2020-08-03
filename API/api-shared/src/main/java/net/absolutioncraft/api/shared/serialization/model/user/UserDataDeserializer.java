package net.absolutioncraft.api.shared.serialization.model.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

import net.absolutioncraft.api.shared.serialization.JsonSerializer;
import net.absolutioncraft.api.shared.serialization.model.Deserializer;
import net.absolutioncraft.api.shared.user.model.IUser;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class UserDataDeserializer implements Deserializer<IUser> {
    @Inject private Gson gson;
    @Inject private JsonSerializer jsonSerializer;

    @Override
    public IUser deserialize(String json) {
        final JsonObject jsonObject = this.jsonSerializer.parseObject(json);
        return gson.fromJson(jsonObject, IUser.class);
    }
}
