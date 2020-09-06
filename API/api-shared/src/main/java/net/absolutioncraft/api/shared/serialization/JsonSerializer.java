package net.absolutioncraft.api.shared.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

/**
 * <h1>JSON Serializer</h1>
 * JsonSerializer class contains util methods
 * for Json parsing & serializing.
 *
 * @author MelonDev
 * @since 0.0.1
 */
@SuppressWarnings("deprecation")
public final class JsonSerializer {

    private JsonParser parser;
    private Gson gson;

    /**
     * Class constructor
     * @param gson A {@link Gson} instance.
     * @param parser A {@link JsonParser} object.
     */
    @Inject JsonSerializer(Gson gson, JsonParser parser) {
        this.parser = parser;
        this.gson = gson;
    }

    /**
     * This method is used to return a plain text displaying
     * the returned error on a JSON-formatted message.
     * @param error A JSON-formatted error {@link String} message.
     * @return Plain {@link String} error context message.
     */
    public String errorContext(String error) {
        JsonElement message = this.parser.parse(error);
        JsonObject object = message.getAsJsonObject();
        return object.get("message").getAsString();
    }

    // TODO 31/07/2020: Document this
    public JsonElement parseJson(String raw, String property) {
        JsonElement element = this.parser.parse(raw);
        JsonObject object = element.getAsJsonObject();
        return object.get(property);
    }

    public JsonObject parseObject(String raw, String object) {
        return this.parser.parse(raw)
                .getAsJsonObject()
                .get(object)
                .getAsJsonObject();
    }

    public JsonObject parseObject(String raw) {
        return this.parser.parse(raw).getAsJsonObject();
    }

    public String encode(JsonObject object) {
        return this.gson.toJson(object);
    }
}
