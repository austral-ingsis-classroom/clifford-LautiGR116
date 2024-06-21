package edu.austral.ingsis.clifford;

import java.util.List;

public class Rm implements Command {
  private final FileSystem fileSystem;
  private final String name;
  private final boolean recursive;

  public Rm(FileSystem fileSystem, String name, boolean recursive) {
    this.fileSystem = fileSystem;
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute() {
    if (name.isEmpty()) {
      return "Error: Missing argument for rm command";
    }

    List<Node> items = fileSystem.getCurrentDirectory().getChildren();
    Node itemToRemove = null;

    for (Node item : items) {
      if (item.getName().equals(name)) {
        itemToRemove = item;
        break;
      }
    }

    if (itemToRemove != null) {
      if (itemToRemove instanceof Dir) {
        if (!recursive) {
          return "cannot remove '" + name + "', is a directory";
        } else {
          removeDirectoryRecursively((Dir) itemToRemove);
        }
      } else {
        fileSystem.getCurrentDirectory().removeChild(itemToRemove);
      }
      return "'" + name + "' removed";
    }

    return "Error: File or directory not found";
  }

  private void removeDirectoryRecursively(Dir directory) {
    for (Node child : directory.getChildren()) {
      if (child instanceof Dir) {
        removeDirectoryRecursively((Dir) child);
      }
      fileSystem.getCurrentDirectory().removeChild(child);
    }
    fileSystem.getCurrentDirectory().removeChild(directory);
  }
}
