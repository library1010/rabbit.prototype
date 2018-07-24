package per.mike.rabbit.api.command.receiver;

import static com.rabbitmq.client.BuiltinExchangeType.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;
import per.mike.rabbit.api.command.receiver.consumer.SystemOutConsumer;

public class GeneralLogMonitor {
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
    channel.exchangeDeclare(EXCHANGE_NAME, FANOUT);
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");
    
    System.out.println(String.format(" [*] Waiting for messages on queue %s. To exit press CTRL+C", queueName));
    channel.basicConsume(queueName, true, new SystemOutConsumer(channel));
  }

  public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
    GeneralLogMonitor receiver = new GeneralLogMonitor();
    receiver.openConnection();
    receiver.command();
  }

}
