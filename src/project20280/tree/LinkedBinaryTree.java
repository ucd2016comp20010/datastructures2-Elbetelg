package project20280.tree;

import project20280.interfaces.BinaryTree;
import project20280.interfaces.Position;
import project20280.interfaces.Tree;


import java.util.ArrayList;
// import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E>{

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {

        //Q1 part H testing height
        Integer [] arrH = new Integer [] {1,
                2,3,
                4,5,6,7,
                8,9,10,11,12, 13, 14, 15,
                16 ,17 ,18 ,19 ,20 ,21 ,22 ,23 ,24 ,25 ,26 ,27 ,28 ,29 ,30 ,31 ,null ,null ,null ,35};
        LinkedBinaryTree<Integer> treeH = new LinkedBinaryTree<>();
        treeH.createLevelOrder(arrH);
        System.out.println("part h of Q1: " + treeH.height());

        System.out.println(callCount);

        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };
        bt.createLevelOrder(arr);
        System.out.println(bt.toBinaryTreeString());

        //Q3 preorder and inorder testing
        LinkedBinaryTree<Integer> bs = new LinkedBinaryTree<>();
        Integer [] inorder= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        Integer [] preorder= {18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16,17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30};

        LinkedBinaryTree<Integer> bs2 = new LinkedBinaryTree<>();
        Integer [] inorder5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,19, 20, 21, 22};
        Integer [] preorder5 = {6, 5, 3, 2, 1, 0, 4, 17, 10, 9, 8, 7, 16, 14, 13, 12, 11, 15, 21,20, 19, 18, 22};
        bs.construct(inorder , preorder);
        System.out.println(bs.toBinaryTreeString ());
        System.out.println("Root to Leaf Paths: " + bs.rootToLeafPaths());

        bs2.construct(inorder5, preorder5);
        System.out.println("Tree diameter: "+ bs2.diameter());

        System.out.println(" Q6 Experiment");
        runQ6Experiment();

        System.out.println(" Q10 Experiment");
        createRandomTree();


    }

    // Main method to call from outside
    public void construct(E[] inorder, E[] preorder) {
        this.root = constructHelper(inorder, 0, inorder.length - 1, preorder, 0, preorder.length - 1);
        this.size = inorder.length;
    }

    // Recursive helper to build the nodes
    private Node<E> constructHelper(E[] inorder, int inStart, int inEnd, E[] preorder, int preStart, int preEnd) {
        if (inStart > inEnd || preStart > preEnd) return null;

        // 1. The first element in preorder is always the root
        E rootValue = preorder[preStart];
        Node<E> node = new Node<>(rootValue, null, null, null);

        // 2. Find the index of this root in the inorder array
        int rootIndex = -1;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i].equals(rootValue)) {
                rootIndex = i;
                break;
            }
        }

        // 3. Calculate the size of the left subtree
        int leftSize = rootIndex - inStart;

        // 4. Recursively build the left and right subtrees
        node.setLeft(constructHelper(inorder, inStart, rootIndex - 1, preorder, preStart + 1, preStart + leftSize));
        node.setRight(constructHelper(inorder, rootIndex + 1, inEnd, preorder, preStart + leftSize + 1, preEnd));

        // Set parent links
        if (node.getLeft() != null) node.getLeft().setParent(node);
        if (node.getRight() != null) node.getRight().setParent(node);

        return node;
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        // TODO
        if (!isEmpty( )) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public void insert(E e) {
        // TODO
       root = addRecursive(root,e);
    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        // TODO
        if(p == null){
            size++;
            return createNode(e,null,null,null);
        }
        Comparable<E> comp = (Comparable<E>) e;
        if (comp.compareTo(p.getElement()) < 0) {
            Node<E> leftChild = addRecursive(p.getLeft(), e);
            p.setLeft(leftChild);
            leftChild.setParent(p);
        } else {
            Node<E> rightChild = addRecursive(p.getRight(), e);
            p.setRight(rightChild);
            rightChild.setParent(p);
        }
        return p;
    }

    //Count external recursive method.
    public int countExternal(Position<E> p){
        if(isExternal(p)){
            return 1;
        }
        int count = 0;
        if(left(p) != null)
            count = count + countExternal(left(p));
        if(right(p) != null)
            count = count + countExternal(right(p));
        return count;

    }


    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        Node<E> parent = validate(p);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        Node<E> parent = validate(p);
        if (parent.getRight( ) != null)
            throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        // TODO
        Node<E> node = validate(p);
        E temp = node.getElement( );
        node.setElement(e);
        return temp;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        // TODO
        Node<E> node = validate(p);
        if(isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
        size += t1.size() + t2.size();
        if(!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if(!t2.isEmpty()){
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        // TODO
        Node<E> node = validate(p);
        if(numChildren(p) == 2){
            throw new IllegalArgumentException("p has 2 children");
        }
        Node<E> child = (node.getLeft()!= null ? node.getLeft(): node.getRight());
        if(child != null)
            child.setParent(node.getParent());
        if(node == root)
            root = child;
        else{
            Node<E> parent = node.getParent();
            if(node == parent.getLeft())
                parent.setLeft(child);
            else parent.setRight(child);
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return  temp;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        // TODO
        root = createLevelOrderHelper(l, root,0 );
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        // TODO
        if (i < l.size() && l.get(i) != null) {
            Node<E> temp = createNode(l.get(i), p, null, null);
            size++;
            temp.setLeft(createLevelOrderHelper(l, temp, 2 * i + 1));
            temp.setRight(createLevelOrderHelper(l, temp, 2 * i + 2));
            return temp;
        }
        return null;

    }

    public void createLevelOrder(E[] arr) {
        this.size = 0;
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        // TODO
        if (i < arr.length && arr[i] != null) {
            Node<E> temp = createNode(arr[i], p, null, null);
            size++;
            temp.setLeft(createLevelOrderHelper(arr, temp, 2 * i + 1));
            temp.setRight(createLevelOrderHelper(arr, temp, 2 * i + 2));
            return temp;
        }
        return null;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

    public java.util.List<java.util.List<E>> rootToLeafPaths() {
        java.util.List<java.util.List<E>> allPaths = new ArrayList<>();
        if (!isEmpty()) {
            rootToLeafPathsHelper(root(), new ArrayList<>(), allPaths);
        }
        return allPaths;
    }
    private void rootToLeafPathsHelper(Position<E> p, java.util.List<E> path, java.util.List<java.util.List<E>> allPaths) {
        if (p == null) return;

        path.add(p.getElement());

        if (isExternal(p)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            if (left(p) != null) rootToLeafPathsHelper(left(p), path, allPaths);
            if (right(p) != null) rootToLeafPathsHelper(right(p), path, allPaths);
        }

        path.remove(path.size() - 1);
    }

    public int diameter() {
        return diameter(root());
    }

    private int diameter(Position<E> p) {
        if (p == null) return 0;

        // Use your existing height method (if it counts nodes, use it as is)
        int lHeight = (left(p) != null) ? height_recursive(left(p)) : 0;
        int rHeight = (right(p) != null) ? height_recursive(right(p)) : 0;

        int leftDiameter = (left(p) != null) ? diameter(left(p)) : 0;
        int rightDiameter = (right(p) != null) ? diameter(right(p)) : 0;

        // The "+ 3" is because the PDF defines diameter by the number of nodes
        return Math.max(lHeight + rHeight + 3, Math.max(leftDiameter, rightDiameter));
    }

    public static void runQ6Experiment() {
        //System.out.println("n,AverageHeight");
        for (int n = 50; n <= 5000; n += 50) {
            double totalHeight = 0;
            int trials = 100;

            for (int i = 0; i < trials; i++) {
                // makeRandom creates a tree with n nodes
                LinkedBinaryTree<Integer> tree = LinkedBinaryTree.makeRandom(n);
                totalHeight += tree.height();
            }

            double avgHeight = totalHeight / (double) trials;
            System.out.println(n + "," + avgHeight);
        }
    }

    //lab 5 Q9
    public  void printLeaves(Node<E> node) {
        if (node == null) return;

        // If it's a leaf, print it!
        if (node.getLeft() == null && node.getRight() == null) {
            System.out.print(node.getElement() + " ");
        }

        // Otherwise, keep looking down both sides
        printLeaves(node.getLeft());
        printLeaves(node.getRight());
    }

    //Q10

    public static void createRandomTree() {

            for (int n = 10; n <= 10000; n += 100) {
                long totalTime = 0;
                int trials = 50;

                for (int i = 0; i < trials; i++) {
                    LinkedBinaryTree<Integer> tree = LinkedBinaryTree.makeRandom(n);

                    long start = System.nanoTime();
                    tree.inorder(); // The method we are testing for Q10
                    long end = System.nanoTime();

                    totalTime += (end - start);
                }

                double avgTime = (double) totalTime / trials;
                System.out.println(n + "," + avgTime);

            }
    }
}

