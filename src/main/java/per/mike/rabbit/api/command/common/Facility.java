package per.mike.rabbit.api.command.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Facility {

  KERNEL("kernel"), 
  CRON("cron"),
  AUTH("auth"), 
  NONE("*"),
  ;
  
  private String routingKey;
}
