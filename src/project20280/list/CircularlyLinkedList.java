package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private  T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private  int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        //TODO
        if (i < 0 || i >= size) {
            return null;
        }
        Node<E> temp = tail.getNext();
        for (int j = 0; j < i; j++) {
            temp = temp.getNext();
        }
        return temp.getData();
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        // TODO
        if(i == 0){
           addFirst(e);
        }else if(i == size){
            addLast(e);
        }else {
            Node<E> temp = tail.getNext();
            for (int j = 0; j < i - 1; j++) {
                temp = temp.getNext();
            }
            Node<E> newNode = new Node<>(e, temp.getNext());
            temp.setNext(newNode);
            size++;
        }
    }

    @Override
    public E remove(int i) {
        // TODO
        if(i == 0){
            return removeFirst();
        }else if(i == size - 1){
            return removeLast();
        }else {
            Node<E> temp = tail.getNext();
            for (int j = 0; j < i - 1; j++) {
                temp = temp.getNext();
            }
            Node<E> target = temp.getNext(); // The node to be removed
            E res = target.getData();
            temp.setNext(target.getNext());  // Bypass the target
            size--;
            return res;
        }
    }

    public void rotate() {
        // TODO
        if(tail != null){
            tail = tail.getNext();
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        // TODO
        if(isEmpty()){
            return null;
        }else{
            Node<E> head = tail.getNext();
            if(size == 1){
                tail = null;
            }else{
                tail.setNext(head.getNext());
            }
            size--;
            return head.getData();
        }
    }

    @Override
    public E removeLast() {
        // TODO
        if (isEmpty()) return null;
        if (size == 1) return removeFirst();

        Node<E> head = tail.getNext();
        Node<E> curr = head;
        while (curr.getNext() != tail) {
            curr = curr.getNext();
        }
        E oldData = tail.getData();
        curr.setNext(head);
        tail = curr;
        size--;
        return oldData;
    }

    @Override
    public void addFirst(E e) {
        // TODO
        if(size == 0){
            tail = new Node<>(e,null);
            tail.setNext(tail);
        }else{
            Node<E> newNode = new Node<>(e, tail.getNext());
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        // TODO
        addFirst(e);
        tail = tail.getNext();
    }


    public String toString() {
        if(isEmpty()){
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail;
        do {
            curr = curr.next;
            sb.append(curr.data);
            if (curr != tail) {
                sb.append(", ");
            }
        } while (curr != tail);
        sb.append("]");
        return sb.toString();
    }


    public static void main(String[] args){
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);

        System.out.println(ll); // Should print [4, 3, 2, 1, 0, -1]

        ll.remove(5);
        System.out.println(ll); // Should print [4, 3, 2, 1, 0]

        // You can keep a rotate check here just to prove it works!
        ll.rotate();
        System.out.println("After rotate: " + ll);
    }
}
