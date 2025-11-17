package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev,T item,Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        public T getItem() {
            return item;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrev() {
            return prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> i = last;
        Node<T> newNode = new Node(i,value,null);
        last = newNode;
        if (Objects.equals(i,null)) {
            first = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if ((index - size) >= 1) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        if ((index - size) == 0) {
            add(value);
        } else {
            indexCheck(index);
            Node<T> currentNode = findNodeByIndex(index);
            Node<T> newNode = new Node(currentNode.prev, value, currentNode);
            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            } else {
                first = newNode;
            }
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T obj : list) {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> currentNode = findNodeByIndex(index);
        return (T)currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> currentNode = findNodeByIndex(index);
        T toReplace = currentNode.item;
        currentNode.item = value;
        return toReplace;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = findNodeByIndex(index);
        if (currentNode.prev == null) {
            if (currentNode.next != null) {
                currentNode.next.prev = null;
                first = currentNode.next;
            }
        } else {
            currentNode.prev.next = currentNode.next;
            if (currentNode.next != null) {
                currentNode.next.prev = currentNode.prev;
            }
        }
        size--;
        return (T)currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (!Objects.equals(currentNode.item,object)) {
            currentNode = currentNode.next;
            if (currentNode == null) {
                return false;
            }
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            first = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            last = currentNode;
        }
        size--;
        if (size == 0) {
            last = null;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexCheck(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("invalid index");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = first;
        while (index > 0) {
            currentNode = currentNode.next;
            index--;
        }
        return currentNode;
    }

    public int getSize() {
        return size;
    }

    public Node<T> getFirst() {
        return first;
    }

    public Node<T> getLast() {
        return last;
    }
}
