package net.jackinpoint.media_iot_handler;

/**
 * Class MessageCallback. Handling callbacks for message-actions.
 */
public class MessageCallback {
    private final MessageHandler messageHandler;

    public MessageCallback(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void onNewMessage(String rawJson) {
        this.messageHandler.handle(rawJson);
    }
}
