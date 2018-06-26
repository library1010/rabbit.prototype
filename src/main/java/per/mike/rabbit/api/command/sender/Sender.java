package per.mike.rabbit.api.command.sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;

public class Sender {

  private final static String QUEUE_NAME = "durable_queue";

  public void command(String message) throws IOException, TimeoutException {
    try (Connection connection = new ConnectionFactoryCreator().createConnectionFactory("localhost").newConnection();
        Channel channel = connection.createChannel()) {

      channel.queueDeclare(QUEUE_NAME, true, false, false, null);
      channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
      System.out.println(String.format(" [x] Sent '%s'", message));
    }
  }

  public static void main(String[] args) throws IOException, TimeoutException {
    sendBulkMessage("Zero task.", "First task.", "Second task.", "Third task.", "Forth task");
  }
  
  private static void sendBulkMessage(String... strings) throws IOException, TimeoutException {
    for(String message : strings) {
      new Sender().command(message);  
    }
  }

  @SuppressWarnings("unused")
  private static String getMessage(String... strings) {
    if (strings.length < 1) {
      return "Hello World! Isn't me you're looking for?";
    }
    return String.join(" ", strings);
  }
}
