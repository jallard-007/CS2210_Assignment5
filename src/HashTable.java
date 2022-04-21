import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class HashTable<T> implements Collection<T> {
  ArrayList<Node<T>> table;
  int size;

  public HashTable(int size) {
    this.size = size;
    this.table = new ArrayList<>(size);
    for (int i = 0; i < size; ++i) {
      this.table.add(new Node<>("", null));
    }
  }

  public void _insert(Vertex location, T data) {
    int hashCode = location.hashCode() % this.size;
    String key = location.toString();
    if (this.table.get(hashCode).data == null) {
      this.table.set(hashCode, new Node<>(key, data));
      return;
    }
    Node<T> currNode = this.table.get(hashCode);
    Node<T> prevNode = currNode;
    while (currNode != null) {
      if (currNode.key.equals(key)) {
        return;
      }
      prevNode = currNode;
      currNode = currNode.next;
    }
    currNode = new Node<>(key, data);
    if (prevNode != null) {
      prevNode.next = currNode;
    }
  }

  public T _get(Vertex data) {
    int hashCode = data.hashCode() % this.size;
    String key = data.toString();
    Node<T> currNode =this.table.get(hashCode);
    while (currNode != null && !currNode.key.equals(key)) {
      currNode = currNode.next;
    }
    if (currNode == null) {
      return null;
    }
    return currNode.data;
  }

  public void _remove(T data) {
    int hashCode = data.hashCode() % this.size;
    String key = data.toString();
    if (this.table.get(hashCode).data != null && this.table.get(hashCode).key.equals(key)){
      this.table.set(hashCode, this.table.get(hashCode).next);
      return;
    }
    Node<T> currNode = this.table.get(hashCode);
    Node<T> prevNode = currNode;
    while (currNode != null && !currNode.key.equals(key)) {
      prevNode = currNode;
      currNode = currNode.next;
    }
    if (currNode == null) {
      return;
    }
    prevNode.next = currNode.next;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    for (int i = 0; i < this.size; ++i) {
      if (this.table.get(i) != null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean contains(Object o) {
    int hashCode = o.hashCode() % size;
    Node<T> node = this.table.get(hashCode);
    while (node.data != null) {
      if (node.data.equals(o)) {
        return true;
      }
      node = node.next;
    }
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return this.iterator(this.table);
  }

  @Override
  public Object[] toArray() {
    return new Object[1000];
  }

  @Override
  public <T1> T1[] toArray(T1[] a) {
    return null;
  }

  @Override
  public boolean add(T t) {
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {
    for (int i = 0; i < size; ++i) {
      this.table.set(i, new Node<>("", null));
    }
  }

  private Iterator<T> iterator(ArrayList<Node<T>> table) {
    ArrayList<T> arrayList = new ArrayList<>();
    for (Node<T> t : table) {
      Node<T> currNode = t;
      while (currNode != null) {
        arrayList.add(currNode.data);
        currNode = currNode.next;
      }
    }
    return new Iterator<T>() {
      public final int size = arrayList.size();
      int i = 0;
      @Override
      public boolean hasNext() {
        for (int j = i; j < size; ++j) {
          if (arrayList.get(j) != null) {
            return true;
          }
        }
        return false;
      }

      @Override
      public T next() {
        for (int j = i; j < size; ++j) {
          T next = arrayList.get(j);
          if (next != null) {
            i = j + 1;
            return next;
          }
        }
        return null;
      }

    };
  }
}
