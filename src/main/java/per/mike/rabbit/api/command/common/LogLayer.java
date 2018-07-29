package per.mike.rabbit.api.command.common;

public enum LogLayer implements IEnumToType {
  FATAL, ERROR, INFO, DEBUG, WARNING,;

  @Override
  public String getType() {
    return this.name().toLowerCase();
  }
}
