package edu.austral.ingsis.clifford;

public class FileSystem {
  private final Dir root;
  private Dir currentDirectory;

  public FileSystem() {
    this.root = new Dir("");
    this.currentDirectory = root;
  }

  public FileSystem(Dir root) {
    this.root = root;
    this.currentDirectory = root;
  }

  public Dir getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(Dir directory) {
    currentDirectory = directory;
  }

  public Dir getRoot() {
    return root;
  }

  public String executeCommand(String commandLine) {
    try {
      String[] tokens = commandLine.split(" ");
      if (tokens.length == 0) {
        return "Error: Empty command";
      }

      String commandName = tokens[0];
      Command command = null;

      switch (commandName) {
        case "ls":
          String order = tokens.length > 1 ? tokens[1].split("=")[1] : null;
          command = new Ls(this, order);
          break;
        case "cd":
          if (tokens.length < 2) {
            return "Error: Missing argument for cd command";
          }
          String path = tokens[1];
          command = new Cd(this, path);
          break;
        case "touch":
          if (tokens.length < 2) {
            return "Error: Missing argument for touch command";
          }
          String fileName = tokens[1];
          command = new Touch(this, fileName);
          break;
        case "mkdir":
          if (tokens.length < 2) {
            return "Error: Missing argument for mkdir command";
          }
          String dirName = tokens[1];
          command = new Mkdir(this, dirName);
          break;
        case "rm":
          if (tokens.length < 2) {
            return "Error: Missing argument for rm command";
          }
          String name;
          boolean recursive = false;
          if (tokens.length > 2) {
            name = tokens[2];
            recursive = tokens[1].equals("--recursive");
          } else {
            name = tokens[1];
          }
          command = new Rm(this, name, recursive);
          break;
        case "pwd":
          command = new Pwd(this);
          break;
        default:
          return "Error: Unknown command";
      }

      return command.execute();
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }
}