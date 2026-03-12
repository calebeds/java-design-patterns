package designpatterns.iterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

class Node<T> {
    private T value;
    private Node<T> left, right, parent;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;

        left.parent = right.parent = this;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }
}

class InOrderIterator<T> implements Iterator<T> {

    private Node<T> current, root;
    private boolean yieldedStart;

    public InOrderIterator(Node<T> root) {
        this.root = current = root;

        while (current.getLeft() != null) {
            current = current.getLeft();
        }
    }

    private boolean hasRightmostParent(Node<T> node) {
        if(node.getParent() == null) {
            return false;
        } else {
            return (node == node.getParent().getLeft())
                    || hasRightmostParent(node.getParent());
        }
    }

    @Override
    public boolean hasNext() {
        return current.getLeft() != null
                || current.getRight() != null
                || hasRightmostParent(current);
    }

    @Override
    public T next() {
        if(!yieldedStart) {
            yieldedStart = true;
            return current.getValue();
        }

        if(current.getRight() != null) {
            current = current.getRight();
            while (current.getLeft() != null) {
                current = current.getLeft();
            }
            return current.getValue();
        } else {
            Node<T> parent = current.getParent();
            while (parent != null && current == parent.getRight()) {
                current = parent;
                parent = parent.getParent();
            }
            current = parent;
            return parent.getValue();
        }
    }
}

class BinaryTree<T> implements Iterable<T> {

    private Node<T> root;

    public BinaryTree(Node<T> root) {
        this.root = root;
    }

    @Override
    public Iterator<T> iterator() {
        return new InOrderIterator<>(root);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for(T item: this) {
            action.accept(item);
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}

public class IteratorEx {
    public static void main(String[] args) {
        //  1
        // / \
        //2   3

        // 213

        Node<Integer> root = new Node<>(1, new Node<>(2), new Node<>(3));

        InOrderIterator<Integer> iterator = new InOrderIterator<>(root);

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + ", ");

        }

        System.out.println();

        BinaryTree<Integer> tree = new BinaryTree<>(root);
        for(int n: tree) {
            System.out.print(n + ", ");
        }
    }
}
