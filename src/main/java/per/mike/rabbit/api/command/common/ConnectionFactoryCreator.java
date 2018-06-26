package per.mike.rabbit.api.command.common;

import com.rabbitmq.client.ConnectionFactory;

public class ConnectionFactoryCreator {
  public ConnectionFactory createConnectionFactory(String hostname) {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(hostname);
    return factory;
  }
}
