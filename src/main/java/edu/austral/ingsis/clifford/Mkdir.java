package edu.austral.ingsis.clifford;

public class Mkdir implements Command {
  private final FileSystem fileSystem;
  private final String name;

  public Mkdir(FileSystem fileSystem, String name) {
    this.fileSystem = fileSystem;
    this.name = name;
  }

  @Override
  public String execute() {
    try {
      if (name.isEmpty()) {
        return "Error: Directory name cannot be empty";
      }

      new Dir(name, fileSystem.getCurrentDirectory());

      return "'" + name + "' directory created";
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    }
  }
}
