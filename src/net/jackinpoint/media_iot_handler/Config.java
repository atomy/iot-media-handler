package net.jackinpoint.media_iot_handler;

/**
 * Class Message. Model-class.
 */
public class Config {
    /**
     * Retrieve setting for api endpoint url.
     *
     * @return String
     */
    public static String getApiUrl() {
        String apiUrl = System.getenv("API_URL");

        if (apiUrl.length() <= 0) {
            throw new RuntimeException("Missing env *API_URL*!");
        }

        return apiUrl;
    }

    public static String getNatsUri() {
        String natsUri = System.getenv("NATS_URI");

        if (natsUri.length() <= 0) {
            throw new RuntimeException("Missing env *NATS_URI*!");
        }

        return natsUri;
    }
}
