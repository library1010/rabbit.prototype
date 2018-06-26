package per.mike.rabbit.api.command.receiver;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class SystemOutConsumer extends DefaultConsumer {

  public SystemOutConsumer(Channel channel) {
    super(channel);
  }

  @Override
  public void handleDelivery(String consumerTag,
      Envelope envelope,
      AMQP.BasicProperties properties,
      byte[] body) throws IOException {
    System.out.println(String.format(" [x] Received '%s'", new String(body, "UTF-8")));
  }

}
