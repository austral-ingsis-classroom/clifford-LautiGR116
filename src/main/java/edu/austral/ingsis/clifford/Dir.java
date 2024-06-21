package edu.austral.ingsis.clifford;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dir implements Node {
  private final String name;
  private final List<Node> children;
  private final Dir parent;

  public Dir(String name, Dir parent) {
    this.name = name;
    this.parent = parent;
    this.children = new ArrayList<>();
    if (parent != null) {
      parent.addChild(this);
    }
  }

  public Dir(String name) {
    this.name = name;
    this.parent = null;
    this.children = new ArrayList<>();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Dir getParent() {
    return parent;
  }

  public List<Node> getChildren() {
    return Collections.unmodifiableList(children);
  }

  public void addChild(Node node) {
    if (node instanceof Dir dir) {
      if (dir.getParent() == this) {
        children.add(node);
      }
    } else {
      children.add(node);
    }
  }

  public void removeChild(Node node) {
    children.remove(node);
  }
}
