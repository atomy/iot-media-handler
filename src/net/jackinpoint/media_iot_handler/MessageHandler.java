package net.jackinpoint.media_iot_handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.*;

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
    private final String url;

    public MessageHandler(final String url) {
        this.client = new OkHttpClient();
        this.url = url;
    }

    /**
     * Handle given message.
     *
     * @param message Message
     */
    public void handle(Message message) {
        if (!this.validate(message.content)) {
            System.out.println("DISMISSing invalid message: " + message.content);
            return;
        }

        NatsIotMessage natsIotMessage = (new Gson()).fromJson(message.content, NatsIotMessage.class);
        RequestBody body = RequestBody.create((new Gson()).toJson(natsIotMessage), JSON);
        Request request = new Request.Builder()
                .url(this.url + "/event")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            try {
                ResponseBody responseBody = response.body();

                if (null == responseBody) {
                    System.out.println("DISMISSing message with empty response body!");
                    return;
                }

                String responseContent = responseBody.string();
                System.out.println("RESPONSE: " + responseContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Roughly validate content of messsage to not break json or anything like that.
     *
     * @param messageContent String
     * @return boolean
     */
    public boolean validate(String messageContent) {
        Gson gson = new Gson();
        NatsIotMessage natsIotMessage = null;

        try {
            natsIotMessage = gson.fromJson(messageContent, NatsIotMessage.class);
        } catch (JsonSyntaxException jsonSyntaxException) {
            jsonSyntaxException.printStackTrace();
            return false;
        }

        if (null == natsIotMessage.action || natsIotMessage.action.length() <= 0) {
            return false;
        }

        return true;
    }
}
