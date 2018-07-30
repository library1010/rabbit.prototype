package per.mike.rabbit.api.command.sender.log_monitor;

import per.mike.rabbit.api.command.common.LogLayer;

public class LogEntry {

  private String message;
  private LogLayer type;
  
  public LogEntry(String message, LogLayer type) {
    this.message = message;
    this.type = type;
  }
  
  public String getMessage() {
    return message;
  }
  
  public String getType() {
    return type.getRoutingKey();
  }
  
  public String toString() {
    return String.format("[%s] %s", getType(), getMessage());
  }
}
