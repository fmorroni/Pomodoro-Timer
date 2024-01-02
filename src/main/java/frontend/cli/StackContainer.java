package frontend.cli;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackContainer {
  private final Deque<TextArea> components = new ArrayDeque<>();

  public void add(TextArea component) {
    components.addLast(component);
  }

  public void addAll(TextArea... components) {
    for (TextArea c : components) {
      this.components.addLast(c);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (TextArea component : components) {
      sb.append(component);
    }
    return sb.toString();
  }
}
