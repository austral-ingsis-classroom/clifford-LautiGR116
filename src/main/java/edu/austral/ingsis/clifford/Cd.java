package edu.austral.ingsis.clifford;

import java.util.Objects;

public class Cd implements Command {
  private final FileSystem fileSystem;
  private final String path;

  public Cd(FileSystem fileSystem, String path) {
    this.fileSystem = fileSystem;
    this.path = path;
  }

  public String execute() {
    if (Objects.equals(path, "/")) {
      fileSystem.setCurrentDirectory(fileSystem.getRoot());
      return "Moved to directory '/'";
    }

    Dir targetDirectory = navigateToDirectory(path);

    if (targetDirectory != null) {
      fileSystem.setCurrentDirectory(targetDirectory);
      return "Moved to directory '" + getDirectoryPath(targetDirectory) + "'";
    } else {
      return "Directory '" + path + "' does not exist";
    }
  }

  private Dir navigateToDirectory(String path) {
    Dir currentDirectory = fileSystem.getCurrentDirectory();
    if (path.startsWith("/")) {
      currentDirectory = fileSystem.getRoot();
      path = path.substring(1);
    }

    if (path.isEmpty()) {
      return currentDirectory;
    }

    String[] components = path.split("/");

    for (String component : components) {
      if (component.equals("..")) {
        if (currentDirectory.getParent() != null) {
          currentDirectory = currentDirectory.getParent();
        } else {
          return fileSystem.getRoot();
        }
      } else if (!component.equals(".") && !component.isEmpty()) {
        currentDirectory = findChildDirectory(currentDirectory, component);
        if (currentDirectory == null) {
          return null;
        }
      }
    }

    return currentDirectory;
  }

  private Dir findChildDirectory(Dir directory, String name) {
    for (Node node : directory.getChildren()) {
      if (node instanceof Dir && node.getName().equals(name)) {
        return (Dir) node;
      }
    }
    return null;
  }

  private String getDirectoryPath(Dir directory) {
    if (directory == fileSystem.getRoot()) {
      return "/";
    }

    StringBuilder pathBuilder = new StringBuilder();
    while (directory != null && directory != fileSystem.getRoot()) {
      pathBuilder.insert(0, "/" + directory.getName());
      directory = directory.getParent();
    }
    return pathBuilder.toString();
  }
}