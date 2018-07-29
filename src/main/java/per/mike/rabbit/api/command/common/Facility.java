package per.mike.rabbit.api.command.common;

public enum Facility implements IEnumToType {

  KERNEL, CRON, AUTH,;
  
  @Override
  public String getType() {
    return this.name().toLowerCase();
  }
}
