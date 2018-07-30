package per.mike.rabbit.api.command.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogLayer {
  FATAL("fatal"), 
  ERROR("error"), 
  INFO("info"), 
  DEBUG("debug"), 
  WARNING("warning"), 
  NONE("*"),
  ;

  private String routingKey;
}
