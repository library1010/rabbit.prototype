package per.mike.rabbit.api.command.sender.log_monitor;

import static per.mike.rabbit.api.command.common.LogLayer.*;
import static com.rabbitmq.client.BuiltinExchangeType.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;

public class EmitLogTopic {
  private final static String EXCHANGE_NAME = "logs";

  public void command(LogEntry logEntry) throws IOException, TimeoutException {
    try (Connection connection = new ConnectionFactoryCreator().createConnectionFactory("localhost").newConnection();
        Channel channel = connection.createChannel()) {
      
      channel.exchangeDeclare(EXCHANGE_NAME, DIRECT);
      channel.basicPublish(EXCHANGE_NAME, logEntry.getType(), null, logEntry.toString().getBytes());
      System.out.println(String.format(" [x] Sent '%s'", logEntry.toString()));
    }
  }

  public static void main(String[] args) throws IOException, TimeoutException {
    sendBulkMessage(new LogEntry("Run something", INFO),
        new LogEntry("Run another thing", INFO),
        new LogEntry("Opps, error!", ERROR),
        new LogEntry("Error occurs", INFO));
  }
  
  private static void sendBulkMessage(LogEntry... entries) throws IOException, TimeoutException {
    for(LogEntry message : entries) {
      new EmitLogTopic().command(message);  
    }
  }
}
