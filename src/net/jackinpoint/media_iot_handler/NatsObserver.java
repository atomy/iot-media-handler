package net.jackinpoint.media_iot_handler;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Subscription;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class NatsObserver. Connecting to NATS backend and listening for new messages.
 */
public class NatsObserver {
    private Connection connection;
    private Subscription subscription;
    private Dispatcher dispatcher;
    private MessageCallback messageCallback;

    /**
     * Connect to backend.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect(String uri) throws IOException, InterruptedException {
        this.connection = Nats.connect(uri);
        System.out.println(String.format("Connection-Status to \"%s\" is: %s", uri, this.connection.getStatus()));
    }

    /**
     * Subscribe to message-topic, create callback for new messages.
     */
    public void subscribe() {
        this.dispatcher = this.connection.createDispatcher((message) -> {
            if (null == this.messageCallback) {
                return;
            }

            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("Received message: " + response);
            this.messageCallback.onNewMessage(new Message(response));
        });

        this.dispatcher.subscribe("iot.meta");
        System.out.println("SUBSCRIBED to *iot.meta*");
    }

    /**
     * Setter for callback.
     *
     * @param messageCallback MessageCallback
     */
    public void setOnNewMessage(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }
}
