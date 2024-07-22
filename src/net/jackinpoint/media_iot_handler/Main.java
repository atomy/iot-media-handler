package net.jackinpoint.media_iot_handler;

import java.io.IOException;

/**
 * Class Main. Main-class obviously.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {

        NatsObserver natsObserver = new NatsObserver();

        try {
            System.out.println("Connecting to backend: " + Config.getNatsUri());
            natsObserver.connect(Config.getNatsUri());
            natsObserver.setOnNewMessage(new MessageCallback(new MessageHandler()));
            natsObserver.subscribe();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
//            System.out.println("running...");

            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
