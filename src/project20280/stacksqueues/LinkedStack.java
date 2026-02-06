package project20280.stacksqueues;

import project20280.interfaces.Stack;
import project20280.list.DoublyLinkedList;

public class LinkedStack<E> implements Stack<E> {

    DoublyLinkedList<E> ll;

    public LinkedStack() {
        // TODO
        ll = new DoublyLinkedList<>();

    }

    @Override
    public int size() {
        return ll.size();
    }

    @Override
    public boolean isEmpty() {
        return ll.isEmpty();
    }

    @Override
    public void push(E e) {
        // TODO
        ll.addFirst(e);
    }

    @Override
    public E top() {
        // TODO
        return ll.first();
    }

    @Override
    public E pop() {
        // TODO
        if(ll.isEmpty()){
            return null;
        }
            return ll.removeFirst();
    }

    public String toString() {
        return ll.toString();
    }

    static String convertToBinary(long dec_num) {
        if (dec_num == 0) return "0";

        Stack<Long> s = new LinkedStack<>();

        while (dec_num > 0) {
            s.push(dec_num % 2);
            dec_num /= 2;
        }

        StringBuilder sb = new StringBuilder();
        while (!s.isEmpty()) {
            sb.append(s.pop());
        }
        return sb.toString();
    }

    public static boolean checkParentheses(String in) {
        Stack<Character> stack = new LinkedStack<>();

        for (char c : in.toCharArray()) {

            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }

            else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                if (!isMatch(top, c)) return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatch(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '[' && close == ']') ||
                (open == '{' && close == '}');
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()",           // false
                "c[d]",              // true
                "a{b[c]d}e",         // true
                "a{b(c]d}e",         // false
                "a[b{c}d]e}",        // false
                "a{b(c) ",           // false
                "][]][][[]][]][][[[", // false
                "(((abc))((d)))))"   // false
        };

        System.out.println("Testing Parentheses");
        for (String input : inputs) {

            boolean isBalanced = checkParentheses(input);
            System.out.println("isBalanced " + (isBalanced ? " yes! " : " no!  ") + input);
        }

        System.out.println("\nTesting Binary Conversion");
        System.out.println("23 in Binary: " + convertToBinary(23));
    }
 }

