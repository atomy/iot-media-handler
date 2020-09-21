package net.jackinpoint.media_iot_handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class MessageHandler. Handling new messages and forwarding them to the API.
 */
public class MessageHandler {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static final Pattern VALID_MESSAGE_CONTENT = Pattern.compile("^[a-zA-Z0-9_-]+$", Pattern.CASE_INSENSITIVE);

    private final OkHttpClient client;

    public MessageHandler() {
        this.client = new OkHttpClient();
    }

    /**
     * Handle given message.
     *
     * @param rawJson String
     */
    public void handle(String rawJson) {
        NatsIotMessage natsIotMessage = this.mapAndValidate(rawJson);

        if (null == natsIotMessage) {
            System.out.println("DISMISSing invalid message: " + rawJson);
            return;
        }

        System.out.println("Got message: " + natsIotMessage.toString());
//
//        NatsIotMessage natsIotMessage = (new Gson()).fromJson(message.content, NatsIotMessage.class);
//        RequestBody body = RequestBody.create((new Gson()).toJson(natsIotMessage), JSON);
//        Request request = new Request.Builder()
//                .url(this.url + "/event")
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            try {
//                ResponseBody responseBody = response.body();
//
//                if (null == responseBody) {
//                    System.out.println("DISMISSing message with empty response body!");
//                    return;
//                }
//
//                String responseContent = responseBody.string();
//                System.out.println("RESPONSE: " + responseContent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Roughly validate content of messsage to not break json or anything like that.
     *
     * @param jsonContent String
     * @return boolean
     */
    public @Nullable NatsIotMessage mapAndValidate(String jsonContent) {
        Gson gson = new Gson();
        NatsIotMessage natsIotMessage = null;

        try {
            natsIotMessage = gson.fromJson(jsonContent, NatsIotMessage.class);
        } catch (JsonSyntaxException jsonSyntaxException) {
            jsonSyntaxException.printStackTrace();
            return null;
        }

        if (null == natsIotMessage.action || natsIotMessage.action.length() <= 0) {
            return null;
        }

        return natsIotMessage;
    }
}
