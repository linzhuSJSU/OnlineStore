package courseProject;


/**
 * Group member 1 : Mengmeng Yang
 * Group member 2 : Lin Zhu
 * CIS 22C, Lab 7
 * */

import java.util.NoSuchElementException;

public class List<T extends Comparable<T>> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /****CONSTRUCTOR****/

    /**
     * Instantiates a new List with default values
     * @postcondition a List object has be created with length == 0
     */
    public List() {
        first = null;
        last = null;
        length = 0;
        iterator = null;
    }

    /*****copy constructor****/
    /**Instantiates a new List by copying another List
     * @param original the list to make a copy of
     * @postcondition a new List object,which is an identical but separate copy
     * of the List original
     */

    public List(List original) {
        if(original.length == 0) {
            length = 0;
            first = null;
            last = null;
            iterator = null;
        }else {
            Node temp = original.first;
            while(temp != null) {
                addLast(temp.data);
                temp = temp.next;
            }
            iterator = null;
        }
    }
    /****ACCESSORS****/

    /**
     * Returns the value stored in the first node
     * @precondition length != 0
     * @return the integer value stored at node first
     * @throws NoSuchElementException when precondition is violated
     */
    public T getFirst() throws NoSuchElementException{
        if(length == 0) {
            throw new NoSuchElementException("getFirst(): " + "List is Empty. No data to access!");
        }
        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * @precondition length != 0
     * @return the integer value stored in the node last
     * @throws NoSuchElementException when precondition is violated
     */
    public T getLast() throws NoSuchElementException{
        if(length == 0) {
            throw new NoSuchElementException("getLast(): " + " List is Empty. No data to access!");
        }
        return last.data;
    }

    /**
     * Returns the current length of the list
     * @return the length of the list from 0 to n
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns whether the list is currently empty
     * @return whether the list is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * return the element currently pointed at by the iterator
     * @precondition iterator != null
     * @return the value pointed by the iterator
     * @throws NullPointerException when the precondition is violated
     * */
    public T getIterator() throws NullPointerException {
        if(iterator == null) {
            throw new NullPointerException("getIterator():"
                    + "the iterator is off end.Cannot return data.");
        }
        return iterator.data;
    }

    /**
     * returns whether or not the iterator is off the end of the List,i.e. null
     * @return whether the iterator is null
     * */
    public boolean offEnd() {
        return iterator == null;
    }

    /**
     * Determines whether two Lists have the same data
     * in the same order
     * @param L the List to compare to this List
     * @return whether the two Lists are equal
     */
    @Override public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if (!(o instanceof List)) {
            return false;
        } else {
            List<T> L = (List<T>) o;
            if (this.length != L.length) {
                return false;
            } else {
                Node temp1 = this.first;
                Node temp2 = L.first;
                while (temp1 != null) { //Lists are same length
                    if (temp1.data != temp2.data) {
                        return false;
                    }
                    temp1 = temp1.next;
                    temp2 = temp2.next;
                }
                return true;
            }
        }
    }

    /**
     * Determines whether a List is sorted
     * by calling the recursive helper method
     * isSorted
     * Note: A List that is empty can be
     * considered to be (trivially) sorted
     * @return whether this List is sorted
     */
    public boolean isSorted() {
        return isSorted(first);
    }

    /**
     * Recursively determines whether
     * a List is sorted in ascending order
     * @return whether this List is sorted
     */
    private boolean isSorted(Node n) {
        if(n == null || n.next == null) {
            return true;
        }
        if(n.data.compareTo(n.next.data) < 0) {
            return isSorted(n.next);
        }
        return false;
    }

    /**
     * Returns the index of the iterator
     * from 1 to n. Note that there is
     * no index 0.
     * @precondition iterator != null
     * @return the index of the iterator
     * @throws NullPointerException when
     * the precondition is violated
     */
    public int getIndex() throws NullPointerException{
        if(iterator == null) {
            throw new NullPointerException("getIndex(): " + "Iterator is null,no data to access.");
        }else {
            Node temp = first;
            int index = 1;
            while(temp != iterator) {
                temp = temp.next;
                index++;
            }
            return index;
        }
    }

    /**
     * Searches the List for the specified
     * value using the iterative linear
     * search algorithm
     * @param value the value to search for
     * @return the location of value in the
     * List or -1 to indicate not found
     * Note that if the List is empty we will
     * consider the element to be not found
     * @postcondition: position of the iterator remains
     * unchanged!
     */
    public int linearSearch(T value) {
        Node temp = first;
        int index = 1;
        while(temp != null) {
            if(temp.data.equals(value)) {
                return index;
            }else {
                temp = temp.next;
                index++;
            }
        }
        return -1;
    }

    /**
     * Returns the index from 1 to length
     * where value is located in the List
     * by calling the private helper method
     * binarySearch
     * @param value the value to search for
     * @return the index where value is
     * stored from 1 to length, or -1 to
     * indicate not found
     * @precondition isSorted()
     * @postcondition the position of the
     * iterator must remain unchanged!
     * @throws IllegalStateException when the
     * precondition is violated.
     */
    public int binarySearch(T value) throws IllegalStateException {
        if(!isSorted()) {
            throw new IllegalStateException("isSorted(): " + "List is not sorted,unable to binary search!");
        }else {
            return binarySearch(1,length,value);
        }
    }

    /**
     * Searches for the specified value in
     * the List by implementing the recursive
     * binarySearch algorithm
     * @param low the lowest bounds of the search
     * @param high the highest bounds of the search
     * @param value the value to search for
     * @return the index at which value is located
     * or -1 to indicate not found
     * @postcondition the location of the iterator
     * must remain unchanged
     */
    private int binarySearch(int low, int high, T value) {
        if(low > high) {
            return -1;
        }
        int mid = low + (high - low);

        Node temp = first;
        int index = mid;
        while(index > 1) {
            temp = temp.next;
            index--;
        }
        T midValue = temp.data;

        if(midValue.equals(value)) {
            return mid;
        }else if(midValue.compareTo(value) > 0) {
            return binarySearch(low,mid -1, value);
        }else {
            return binarySearch(mid + 1,high,value);
        }
    }
    /****MUTATORS****/

    /**
     * Creates a new first element
     * @param data the data to insert at the
     * front of the list
     * @postcondition a new first element
     */

    public void addFirst(T data) {
        if(first == null) {
            first = last = new Node(data);
        }else {
            Node cur = new Node(data);
            cur.next = first;
            first.prev = cur;
            first = cur;
        }
        length++;
    }

    /**
     * Creates a new last element
     * @param data the data to insert at the
     * end of the list
     * @postcondition a node with data has been added at last
     */
    public void addLast(T data) {
        if(last == null) {
            first = last = new Node(data);
        }else {
            Node cur = new Node(data);
            last.next = cur;
            cur.prev = last;
            last = cur;
        }
        length++;
    }

    /**
     * removes the element at the front of the list
     * @precondition length != 0
     * @postcondition first node be removed
     * @throws NoSuchElementException when precondition is violated
     */

    public void removeFirst() throws NoSuchElementException{
        if(length == 0) {
            throw new NoSuchElementException("removeFirst():" + " The list is empty,no data to access.");
        }else if(length == 1){
            first = null;
            last = null;
        }else {
            first.next.prev = null;
            first = first.next;
        }
        length--;
    }

    /**
     * removes the element at the end of the list
     * @precondition length != 0
     * @postcondition last node be removed
     * @throws NoSuchElementException when precondition is violated
     */
    public void removeLast() throws NoSuchElementException{
        if(last == null) {
            throw new NoSuchElementException("removeLast():" + " The list is empty,no data to access.");
        }else if(length == 1) {
            last = null;
            first = null;
        }else {
            last = last.prev;
            last.next = null;
        }
        length--;
    }

    /**
     * advances the iterator by one node in the List
     * @precondition !offEnd()
     * @throws NullPointerException when the precondition is violated
     * */
    public void advanceIterator()throws NullPointerException {
        if(iterator == null) {
            throw new NullPointerException("advanceIterator():"
                    + "the iterator is off end.Cannot advance");
        }
        iterator = iterator.next;
    }
    /**
     * moves the iterator down by one node
     * @precondition !offEnd()
     * @throws NullPointerException when the precondition is violated
     * */
    public void reverseIterator()throws NullPointerException{
        if(iterator == null) {
            throw new NullPointerException("reverseIterator():"
                    + "the iterator is off end. Cannot reverse");
        }
        iterator = iterator.prev;
    }

    /**
     * Interts new data in the List after the iterator
     * @precondition iterator != null
     * @throws NullPointerException when the precondition is violate
     * **/
    public void addIterator(T data) throws NullPointerException{
        if(offEnd()) {
            throw new NullPointerException("addIterator():"
                    +"iterator is off end.Cannot insert");
        }else if(iterator == last) {
            addLast(data);//already length++ in the addLast();
        }else {
            //general case
            Node cur = new Node(data);
            cur.next = iterator.next;
            iterator.next = cur;
            cur.prev = iterator;
            iterator.next.next.prev = cur;
            length++;
        }
    }
    /**
     *Removes the node pointed to by the iterator
     *@precondition iterator != null
     *@postcondition iterator == null
     * */
    public void removeIterator() throws NullPointerException {

        if(iterator == null) {
            throw new NullPointerException("removeIterator(): "
                    +"iterator is off end. Cannot remove");
            //edge case #1
        }else if(iterator == first) {
            removeFirst();
        }
        //edge case #1
        else if(iterator == last) {
            removeLast();
        }
        //general case
        else {
            iterator.prev.next = iterator.next;
            iterator.next.prev = iterator.prev;
            length--;
        }
        iterator = null;
    }

    public void pointIterator() {//moves the iterator to the beginning of the first
        iterator = first;
    }

    /**
     * Points the iterator at first
     * and then iteratively advances
     * it to the specified index
     * @param index the index where
     * the iterator should be placed
     * @precondition 1 <= index <= length
     * @throws IndexOutOfBoundsException
     * when precondition is violated
     */
    public void moveToIndex(int index) throws IndexOutOfBoundsException{
        if(index < 1 || index > length) {
            throw new IndexOutOfBoundsException("moveToIndex(): " + "Index is out of bound");
        }else {
            pointIterator();
            while(index > 1) {
                advanceIterator();
                index--;
            }
        }
    }

    /****ADDITIONAL OPERATIONS****/

    /**
     * List with each value separated by a blank space
     * At the end of the List a new line
     * @return the List as a String for display
     */
    @Override public String toString() {
        String result = "";
        Node temp = first;
        while(temp != null) {
            result += temp.data + " ";
            temp = temp.next;
        }
        return result;
    }

    public void printNumberedList() {
        Node temp = first;
        while(temp != null) {
            System.out.println("#.<"+ temp.data +">");
            temp = temp.next;
        }
    }
    /**
     * Prints a linked list to the console
     * in reverse by calling the private
     * recursive helper method printReverse
     */
    public void printReverse() {
        printReverse(first);
    }

    /**
     * Prints a linked list to the console
     * recursively (no loop)
     * in reverse order from last to first
     * Each element separated by a space
     * Should print a new line after all
     * elements have been displayed
     */

    private void printReverse(Node n) {
        if(n == null) {
            return;
        }
        printReverse(n.next);
        System.out.print(n.data + " ");
    }
}
