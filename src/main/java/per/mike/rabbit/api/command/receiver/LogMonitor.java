package per.mike.rabbit.api.command.receiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;
import per.mike.rabbit.api.command.receiver.consumer.SystemOutConsumer;

public class LogMonitor {
  private final static String EXCHANGE_NAME = "logs";
  private Channel channel;

  public void openConnection() throws IOException {
    try {
      channel = new ConnectionFactoryCreator()
          .createConnectionFactory("localhost")
          .newConnection()
          .createChannel();
    } catch (Exception ex) {
    }
  }

  public void command() throws IOException, InterruptedException, TimeoutException {
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");
    
    System.out.println(String.format(" [*] Waiting for messages on queue %s. To exit press CTRL+C", queueName));
    channel.basicConsume(queueName, true, new SystemOutConsumer(channel));
  }

  public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
    LogMonitor receiver = new LogMonitor();
    receiver.openConnection();
    receiver.command();
  }

}
