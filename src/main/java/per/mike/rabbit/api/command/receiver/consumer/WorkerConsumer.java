package per.mike.rabbit.api.command.receiver.consumer;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class WorkerConsumer extends DefaultConsumer {

  public WorkerConsumer(Channel channel) {
    super(channel);
  }

  @Override
  public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
    try {
      doWork(new String(body, "UTF-8"));
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      System.out.println(" [x] Done");
      getChannel().basicAck(envelope.getDeliveryTag(), false);
    }
  }

  private void doWork(String task) throws InterruptedException {
    for(String message : task.split("\\.")) {
      System.out.println("Working on :" + message.trim());
      Thread.sleep(1000);
    }
  }
}
