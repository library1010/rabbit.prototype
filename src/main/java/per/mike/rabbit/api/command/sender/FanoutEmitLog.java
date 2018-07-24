package per.mike.rabbit.api.command.sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;

public class FanoutEmitLog {
  private final static String EXCHANGE_NAME = "logs";

  public void command(String message) throws IOException, TimeoutException {
    try (Connection connection = new ConnectionFactoryCreator().createConnectionFactory("localhost").newConnection();
        Channel channel = connection.createChannel()) {
      
      channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
      channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
      System.out.println(String.format(" [x] Sent '%s'", message));
    }
  }

  public static void main(String[] args) throws IOException, TimeoutException {
    sendBulkMessage("Zero task.", "First task.", "Second task.", "Third task.", "Forth task", "Fifth task");
  }
  
  private static void sendBulkMessage(String... strings) throws IOException, TimeoutException {
    for(String message : strings) {
      new FanoutEmitLog().command(message);  
    }
  }
}
