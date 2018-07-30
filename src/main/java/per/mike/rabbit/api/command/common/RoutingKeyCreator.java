package per.mike.rabbit.api.command.common;

import lombok.Builder;
import lombok.Builder.Default;

@Builder
public class RoutingKeyCreator {

  @Default
  private LogLayer logLayer = LogLayer.NONE;
  @Default
  private Facility facility = Facility.NONE;

  public String createRoutingKey() {
    return String.format("%s.%s", logLayer.getRoutingKey(), facility.getRoutingKey());
  }
}
