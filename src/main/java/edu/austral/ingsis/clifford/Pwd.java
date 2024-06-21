package edu.austral.ingsis.clifford;

public class Pwd implements Command {
  private final FileSystem fileSystem;

  public Pwd(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute() {
    StringBuilder path = new StringBuilder();
    Dir currentDirectory = fileSystem.getCurrentDirectory();

    while (currentDirectory != null) {
      if (path.length() > 0) {
        path.insert(0, "/");
      }
      path.insert(0, currentDirectory.getName());
      currentDirectory = currentDirectory.getParent();
    }

    return path.toString();
  }
}
