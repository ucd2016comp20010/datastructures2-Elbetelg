package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private final E element;            // reference to the element stored at this node
        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list
        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            // TODO
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            // TODO
           next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    @Override
    public int size() {
        // TODO
        return size;
    }

    @Override
    public boolean isEmpty() {
        // TODO
        if(head == null) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public E get(int position) {
     if(position < 0 ||  position >= size){
                return null;
            }else{
                Node<E> temp = head;
                for(int i = 0; i < position; i++){
                    temp = temp.getNext();
                }
                return temp.getElement();
            }
        }

    @Override
    public void add(int position, E e) {
        // TODO
        if(position == 0){
            addFirst(e);
        }else if(position == size){
            addLast(e);
        }else{
            Node<E> before = head;
            for(int i = 0; i < position - 1; i++){
                before = before.getNext();
            }
            Node<E> newest = new Node<E>(e, before.getNext());
            before.setNext(newest);
            size++;

        }

    }


    @Override
    public void addFirst(E e) {
        head = new Node<E>(e,head);
        size++;
    }

    @Override
    public void addLast(E e) {

        Node<E> newest = new Node<E>(e,null);
        Node<E> last = head;
        if(last == null){
            head = newest;
        }
        else{
            while(last.getNext() != null){
                last = last.getNext();
            }
            last.setNext(newest);
        }
        size++;
    }

    @Override
    public E remove(int position) {
        // TODO
        if(position < 0 || position >= size){
            return null;
        }else if(position == 0){
            return removeFirst();
        }else{
            Node<E> before = head;
            for(int i = 0; i < position - 1; i++){
                before = before.getNext();
            }
            Node<E> target =  before.getNext();
            E sElement = target.getElement();
           before.setNext(target.getNext());
            size--;
            return sElement;
        }
    }

    @Override
    public E removeFirst() {
        // TODO
        if(head == null){
            return  null;
        }else{
            E curr = head.getElement();
            head = head.getNext();
            size--;
            return curr;
        }

    }

    @Override
    public E removeLast() {
        // TODO
        if(isEmpty()){
            return null;
        }else if(size == 1){
            return removeFirst();
        }else{
            Node<E> secondLast = head;
            while (secondLast.getNext().getNext() != null){
                secondLast = secondLast.getNext();
            }
            E lastElement = secondLast.getNext().getElement();
            secondLast.setNext(null);
            size--;
            return lastElement;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    //Q9 merging two sorted lists.
    public SinglyLinkedList<E> sortedMerge(SinglyLinkedList<E> other) {
        SinglyLinkedList<E> result = new SinglyLinkedList<>();

        Node<E> p1 = this.head;
        Node<E> p2 = other.head;

        while (p1 != null && p2 != null) {
            Comparable<E> val1 = (Comparable<E>) p1.getElement();
            E val2 = p2.getElement();

            if (val1.compareTo(val2) <= 0) {
                result.addLast(p1.getElement());
                p1 = p1.getNext();
            } else {
                result.addLast(p2.getElement());
                p2 = p2.getNext();
            }
        }
        while (p1 != null) {
            result.addLast(p1.getElement());
            p1 = p1.getNext();
        }

        while (p2 != null) {
            result.addLast(p2.getElement());
            p2 = p2.getNext();
        }

        return result;
    }

    //Q10 reversed linked lists.
    public void reverse() {
        Node<E> prev = null;
        Node<E> curr = head;
        Node<E> next;
        while(curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }
        head = prev;
    }
    //Q11 cloning linked lists.
    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> twin = new SinglyLinkedList<E>();
        Node<E> tmp = head;
        while (tmp != null) {
            twin.addLast(tmp.getElement());
            tmp = tmp.next;
        }
        return twin;
    }


        public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

            ll.reverse();
            System.out.println("Reversed list: " + ll);

            SinglyLinkedList<Integer> clonedList = ll.copy();
            System.out.println("Cloned list:   " + clonedList);


            clonedList.addFirst(99);
            System.out.println("Modified Clone: " + clonedList);
            System.out.println("Original List:  " + ll);

            System.out.println("\nTesting Sorted Merge");

            SinglyLinkedList<Integer> list1 = new SinglyLinkedList<>();
            list1.addLast(1);
            list1.addLast(3);
            list1.addLast(5);

            SinglyLinkedList<Integer> list2 = new SinglyLinkedList<>();
            list2.addLast(2);
            list2.addLast(4);
            list2.addLast(6);
            list2.addLast(8);

            System.out.println("List 1: " + list1);
            System.out.println("List 2: " + list2);

            SinglyLinkedList<Integer> mergedList = list1.sortedMerge(list2);

            System.out.println("Merged Result: " + mergedList);
        }
    }

