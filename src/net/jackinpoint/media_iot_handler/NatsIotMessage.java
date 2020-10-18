package net.jackinpoint.media_iot_handler;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class NatsIotMessage.
 */
public class NatsIotMessage {
    @SerializedName("emitter_version")
    public String clientVersion;

    public String action;

    public String message;

    public String hostname;

    public long timestamp;

    public String toString() {
        Date date = new Date((long) timestamp);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("Europe/Berlin"));

        return String.format("[T:%s][A:%s][V:%s][H:%s] %s", sdf.format(date), action, clientVersion, hostname, message);
    }
}
