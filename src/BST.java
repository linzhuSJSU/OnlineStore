package courseProject;

import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
    private class Node {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    /*** CONSTRUCTORS ***/

    /**
     * Default constructor for BST sets root to null
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Copy constructor for BST
     *
     * @param bst the BST to make a copy of
     */
    public BST(BST<T> bst) {
        if (bst.size == 0) {
            size = 0;
            root = null;
        } else {
            Node temp = bst.root;
            /*
             * while (temp != null) {
             *
             * }
             */
            if (temp.left != null) {

            }
        }
    }

    /**
     * Helper method for copy constructor
     *
     * @param node the node containing data to copy
     */
    private void copyHelper(Node node) {
        /*
         * insert(node.data); if(node.left != null) { copyHelper(Node node); }
         */
    }

    /*** ACCESSORS ***/

    /**
     * Returns the data stored in the root
     *
     * @precondition !isEmpty()
     * @return the data stored in the root
     * @throws NoSuchElementException when preconditon is violated
     */
    public T getRoot() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("getRoot(): the BST is empty. There is no root node.");
        }
        return root.data;
    }

    /**
     * Determines whether the tree is empty
     *
     * @return whether the tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the current size of the tree (number of nodes)
     *
     * @return the size of the tree
     */
    public int getSize() {
        if (isEmpty()) {
            return 0;
        } else {
            return getSize(root);
        }
    }

    /**
     * Helper method for the getSize method
     *
     * @param node the current node to count
     * @return the size of the tree
     */
    private int getSize(Node node) {
        return size;
    }

    /**
     * Returns the height of tree by counting edges.
     *
     * @return the height of the tree
     */
    public int getHeight() {
        if (isEmpty()) {
            return -1;
        } else {
            return getHeight(root);
        }
    }

    /**
     * Helper method for getHeight method
     *
     * @param node the current node whose height to count
     * @return the height of the tree
     */
    private int getHeight(Node node) {
        if ((node.left == null) && (node.right == null)) {
            return 0;
        } else if (node.left == null) {
            return getHeight(node.right) + 1;
        } else if (node.right == null) {
            return getHeight(node.left) + 1;
        } else {
            if (getHeight(node.left) > getHeight(node.right)) {
                return getHeight(node.left) + 1;
            } else {
                return getHeight(node.right) + 1;
            }
        }
    }

    /**
     * Returns the smallest value in the tree
     *
     * @precondition !isEmpty()
     * @return the smallest value in the tree
     * @throws NoSuchElementException when the precondition is violated
     */
    public T findMin() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("findMin(): the BST is empty. There is no value.");
        }
        return findMin(root);
    }

    /**
     * Helper method to findMin method
     *
     * @param node the current node to check if it is the smallest
     * @return the smallest value in the tree
     */
    private T findMin(Node node) {
        if (node.left != null) {
            return findMin(node.left);
        }
        return node.data;
    }

    /**
     * Returns the largest value in the tree
     *
     * @precondition !isEmpty()
     * @return the largest value in the tree
     * @throws NoSuchElementException when the precondition is violated
     */
    public T findMax() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("findMax(): the BST is empty. There is no value.");
        }
        return findMax(root);
    }

    /**
     * Helper method to findMax method
     *
     * @param node the current node to check if it is the largest
     * @return the largest value in the tree
     */
    private T findMax(Node node) {
        if (node.right != null) {
            return findMax(node.right);
        }
        return node.data;
    }

    /**
     * Searches for a specified value in the tree
     *
     * @param data the value to search for
     * @return whether the value is stored in the tree
     */
    public boolean search(T data) {
        if (isEmpty()) {
            return false;
        } else {
            return search(data, root);
        }

    }

    /**
     * Helper method for the search method
     *
     * @param data the data to search for
     * @param node the current node to check
     * @return whether the data is stored in the tree
     */
    private boolean search(T data, Node node) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.data) == 0) {
            return true;
        } else if (data.compareTo(node.data) < 0) {
            if (node.left == null) {
                return false;
            } else {
                return search(data, node.left);
            }
        } else {
            if (node.right == null) {
                return false;
            } else {
                return search(data, node.right);
            }
        }
    }

    /**
     * Determines whether two trees store identical data in the same structural
     * position in the tree
     *
     * @param o another Object
     * @return whether the two trees are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BST)) {
            return false;
        } else {
            BST bst = (BST) o;
            if (this.getSize() != bst.getSize()) {
                return false;
            } else {
                Node temp1 = this.root;
                Node temp2 = bst.root;
                return equals(temp1, temp2);
            }
        }
//		return false;
    }

    /**
     * Helper method for the equals method
     *
     * @param node1 the node of the first bst
     * @param node2 the node of the second bst
     * @return whether the two trees contain identical data stored in the same
     *         structural position inside the trees
     */
    private boolean equals(Node node1, Node node2) {
        boolean rtEqual = false;
        boolean lEqual = false;
        boolean rEqual = false;
        if ((node1 != null) && (node2 != null)) {
            rtEqual = (node1.data == node2.data);
            if ((node1.left != null) && (node2.left != null)) {
                lEqual = equals(node1.left, node2.left);
            } else if ((node1.left == null) && (node2.left == null)) {
                lEqual = true;
            }
            if ((node1.right != null) && (node2.right != null)) {
                rEqual = equals(node1.right, node2.right);
            } else if ((node1.right == null) && (node2.right == null)) {
                rEqual = true;
            }
            return (rtEqual && lEqual && rEqual);
        }
        return false;
    }

    /*** MUTATORS ***/

    /**
     * Inserts a new node in the tree
     *
     * @param data the data to insert
     */
    public void insert(T data) {
        if (isEmpty()) {
            root = new Node(data);
            size++;
        } else {
            insert(data, root);
        }
    }

    /**
     * Helper method to insert Inserts a new value in the tree
     *
     * @param data the data to insert
     * @param node the current node in the search for the correct location in which
     *             to insert
     */
    private void insert(T data, Node node) {
        if (data.compareTo(node.data) <= 0) {
            if (node.left == null) {
                node.left = new Node(data);
                size++;
            } else {
                insert(data, node.left);
            }
        } else {
            if (node.right == null) {
                node.right = new Node(data);
                size++;
            } else {
                insert(data, node.right);
            }
        }
    }

    /**
     * Removes a value from the BST
     *
     * @param data the value to remove
     * @precondition !isEmpty()
     * @precondition the data is located in the tree
     * @throws NoSuchElementException when the precondition is violated
     */

    public void remove(T data) throws NoSuchElementException {
        if (root == null) {
            throw new NoSuchElementException("remove(): " + "BST is empty,no data to access!");
        } else if (!search(data)) {
            throw new NoSuchElementException("remove(): " + "data is not in BST,cannot remove!");
        } else {
            root = remove(data, root);
        }
    }

    /**
     * Helper method to the remove method
     *
     * @param data the data to remove
     * @param node the current node
     * @return an updated reference variable
     */
    private Node remove(T data, Node node) {
        if (node == null) {
            return null;
        } else if (data.compareTo(node.data) < 0) {
            node.left = remove(data, node.left);
        } else if (data.compareTo(node.data) > 0) {
            node.right = remove(data, node.right);
        } else {
            if (node.left == null && node.right == null) {// leaf node
                node = null;
            } else if (node.left != null && node.right == null) {
                node = node.left;
            } else if (node.right != null && node.left == null) {
                node = node.right;
            } else {
                T min = findMin(node.right);// right node 里面找最小的
                node.data = min;// node变成最小的
                node.right = remove(min, node.right);// 最小的删掉
            }
        }
        return node;
    }

    /*** ADDITIONAL OPERATIONS ***/

    /**
     * Prints the data in pre order to the console
     */
    public void preOrderPrint() {
        if (isEmpty()) {
            System.out.println("The BST is empty.");
        }
        preOrderPrint(root);
        System.out.println();
    }

    /**
     * Helper method to preOrderPrint method Prints the data in pre order to the
     * console
     */
    private void preOrderPrint(Node node) {
        if (node != null) {
            System.out.println(node.data + " ");
            preOrderPrint(node.left);
            preOrderPrint(node.right);
        }
    }

    /**
     * Prints the data in sorted order to the console
     */
    public void inOrderPrint() {
        if (isEmpty()) {
            System.out.println("The BST is empty.");
        }
        inOrderPrint(root);
        System.out.println();
    }

    /**
     * Helper method to inOrderPrint method Prints the data in sorted order to the
     * console
     */
    private void inOrderPrint(Node node) {
        if (node != null) {
            inOrderPrint(node.left);
            System.out.println(node.data + " ");
            inOrderPrint(node.right);
        }
    }

    /**
     * Prints the data in post order to the console
     */
    public void postOrderPrint() {
        if (isEmpty()) {
            System.out.println("The BST is empty.");
        }
        postOrderPrint(root);
        System.out.println();
    }

    /**
     * Helper method to postOrderPrint method Prints the data in post order to the
     * console
     */
    private void postOrderPrint(Node node) {
        if (node != null) {
            postOrderPrint(node.left);
            postOrderPrint(node.right);
            System.out.println(node.data + " ");
        }
    }
}
