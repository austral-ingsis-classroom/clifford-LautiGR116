
package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.austral.ingsis.clifford.Dir;
import edu.austral.ingsis.clifford.File;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileTests {
  @Test
  public void testDirectory() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);

    assertEquals(directory.getName(), "directory");
    assertEquals(root.getChildren(), List.of(directory));
  }

  @Test
  public void testGetChildrenList() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);
    Dir directory1 = new Dir("directory1", root);

    assertEquals(root.getChildren(), List.of(directory, directory1));
  }

  @Test
  public void testMultiLevel() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);
    Dir directory1 = new Dir("directory1", directory);

    assertEquals(root.getChildren(), List.of(directory));
    assertEquals(directory.getChildren(), List.of(directory1));
  }

  @Test
  public void testRemoveChild() {
    Dir root = new Dir("");
    Dir directory = new Dir("directory", root);
    Dir directory1 = new Dir("directory1", root);

    root.removeChild(directory);
    assertEquals(root.getChildren(), List.of(directory1));
  }

  @Test
  public void testDirectoryWithFiles() {
    Dir root = new Dir("");
    File file = new File("file", root);
    Dir file1 = new Dir("file1", root);

    assertEquals(root.getChildren(), List.of(file, file1));
  }
}

