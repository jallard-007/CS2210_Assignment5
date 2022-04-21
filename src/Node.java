class Node<T> {
  String key;
  T data;
  Node<T> next;

  Node(String key, T data) {
    this.key = key;
    this.data = data;
    this.next = null;
  }
}
