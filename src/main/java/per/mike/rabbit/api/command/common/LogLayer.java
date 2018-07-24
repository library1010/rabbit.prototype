package per.mike.rabbit.api.command.common;

public enum LogLayer {
  FATAL, ERROR, INFO, DEBUG, WARNING,;

  public String getType() {
    return this.name().toLowerCase();
  }
}
