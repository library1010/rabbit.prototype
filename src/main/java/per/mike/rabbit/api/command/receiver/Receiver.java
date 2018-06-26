package per.mike.rabbit.api.command.receiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

import per.mike.rabbit.api.command.common.ConnectionFactoryCreator;

public class Receiver {

  private final static String QUEUE_NAME = "durable_queue";
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
    boolean autoAck = false;
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    channel.basicQos(1);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    channel.basicConsume(QUEUE_NAME, autoAck, new WorkerConsumer(channel));
  }

  public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
    Receiver receiver = new Receiver();
    receiver.openConnection();
    receiver.command();
  }

}
