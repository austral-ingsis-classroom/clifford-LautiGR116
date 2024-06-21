package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.ingsis.clifford.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandTests {

  private FileSystem fileSystem;

  @BeforeEach
  public void setUp() {
    fileSystem = new FileSystem();
  }

  @Test
  public void testLSEmpty() {
    assertEquals("", new Ls(fileSystem, null).execute());
  }

  @Test
  public void testLSDirectories() {
    Dir root = new Dir("");
    new Dir("directory", root);
    new Dir("directory1", root);
    fileSystem = new FileSystem(root);

    assertEquals("directory directory1", new Ls(fileSystem, null).execute());
  }

  @Test
  public void testLSDescending() {
    Dir root = new Dir("");
    new Dir("directory", root);
    new Dir("directory1", root);
    fileSystem = new FileSystem(root);

    assertEquals("directory1 directory", new Ls(fileSystem, "desc").execute());
  }

  @Test
  public void testTouchFile() {
    assertEquals("'file' file created", new Touch(fileSystem, "file").execute());
    assertEquals("file", fileSystem.getRoot().getChildren().get(0).getName());
  }

  @Test
  public void testTouchFileInDirectory() {
    Dir root = new Dir("");
    new Dir("directory", root);
    fileSystem = new FileSystem(root);

    assertEquals("'file' file created", new Touch(fileSystem, "file").execute());
    assertEquals("directory", fileSystem.getRoot().getChildren().get(0).getName());
    assertEquals("file", fileSystem.getRoot().getChildren().get(1).getName());
  }

  @Test
  public void testRemoveChild() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);
    Dir directory1 = new Dir("directory1", root);
    fileSystem = new FileSystem(root);

    assertEquals("directory1", fileSystem.getCurrentDirectory().getChildren().get(1).getName());

    new Rm(fileSystem, "directory", true).execute();

    assertEquals(List.of(directory1), fileSystem.getCurrentDirectory().getChildren());
  }

  @Test
  public void testRemoveNonExistingFile() {
    assertEquals(
        "Error: File or directory not found",
        new Rm(fileSystem, "non-existing-file", false).execute());
  }

  @Test
  public void testRemoveDirectoryWithoutRecursive() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);
    fileSystem = new FileSystem(root);

    assertEquals(
        "cannot remove 'directory', is a directory",
        new Rm(fileSystem, "directory", false).execute());
  }

  @Test
  public void testRemoveDirectoryWithRecursive() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);
    Dir subdirectory = new Dir("subdirectory", directory);
    FileSystem fileSystem = new FileSystem(root);

    new Rm(fileSystem, "directory", true).execute();

    assertEquals(List.of(), root.getChildren());
  }

  @Test
  public void testRemoveFile() {
    Dir root = new Dir("");
    File file = new File("file", root);
    fileSystem = new FileSystem(root);

    new Rm(fileSystem, "file", false).execute();

    assertEquals(List.of(), root.getChildren());
  }
}
