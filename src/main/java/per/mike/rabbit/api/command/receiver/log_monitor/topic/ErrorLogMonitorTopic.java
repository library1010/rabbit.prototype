package per.mike.rabbit.api.command.receiver.log_monitor.topic;

import static com.rabbitmq.client.BuiltinExchangeType.*;
import static per.mike.rabbit.api.command.common.LogLayer.ERROR;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;
import per.mike.rabbit.api.command.receiver.consumer.SystemOutConsumer;

public class ErrorLogMonitorTopic {
  private static final String LOCALHOST = "localhost";
  private final static String EXCHANGE_NAME = "logs";
  private Channel channel;

  public ErrorLogMonitorTopic() {
    try {
      // @formatter:off
      channel = new ConnectionFactoryCreator()
          .createConnectionFactory(LOCALHOST)
          .newConnection()
          .createChannel();
    } catch (Exception ex) {}
    // @formatter:on
  }

  public void command() throws IOException, InterruptedException, TimeoutException {
    channel.exchangeDeclare(EXCHANGE_NAME, TOPIC);
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, ERROR.getType());

    System.out.println(
        String.format(" [*] Waiting for messages on queue %s. To exit press CTRL+C", queueName));
    channel.basicConsume(queueName, true, new SystemOutConsumer(channel));
  }

  public static void main(String[] args) throws Exception {
    new ErrorLogMonitorTopic().command();
  }
}
