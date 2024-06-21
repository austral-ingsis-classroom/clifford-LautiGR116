package edu.austral.ingsis.clifford;

import java.util.Objects;

public class File implements Node {
  private final String name;
  private final Dir parent;

  public File(String name, Dir parent) {
    if (name == null || parent == null) {
      throw new IllegalArgumentException("Name and parent cannot be null");
    }
    this.name = name;
    this.parent = parent;
    if (!parent.getChildren().contains(this)) {
      parent.addChild(this);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Dir getParent() {
    return parent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    File file = (File) o;
    return Objects.equals(name, file.name) && Objects.equals(parent, file.parent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent);
  }
}
