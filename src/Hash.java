package courseProject;

import java.util.ArrayList;

public class Hash {

    private int numElements;
    private ArrayList<List<Customer> > Table;

    /**
     * Constructor for the Hash.java
     * class. Initializes the Table to
     * be sized according to value passed
     * in as a parameter
     * Inserts size empty Lists into the
     * table. Sets numElements to 0
     * @param size the table size
     */
    public Hash(int size) {
        Table = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            Table.add(new List<Customer>());
        }
        numElements = 0;
    }

    /**Accessors*/

    /**
     * Returns the hash value in the Table
     * for a given key by taking modulus
     * of the hashCode value for that key
     * and the size of the table
     * @param t the key
     * @return the index in the Table
     */
    private int hash(Customer t) {
        int code = t.hashCode();
        return code % Table.size();
    }

    /**
     * Counts the number of keys at this index
     * @param index the index in the Table
     * @precondition 0 <= index < Table.length
     * @return the count of keys at this index
     * @throws IndexOutOfBoundsException
     */
    public int countBucket(int index) throws IndexOutOfBoundsException{
        if(index < 0 || index >= Table.size()) {
            throw new IndexOutOfBoundsException("countBucket(): "
                    + "index is outside bounds of the table");
        }
        return Table.get(index).getLength();
    }

    /**
     * Returns total number of keys in the Table
     * @return total number of keys
     */
    public int getNumElements() {
        return numElements;
    }

    /**
     * Searches for a specified key in the Table
     * @param t the key to search for
     * @return the index in the Table (0 to Table.length - 1)
     * or -1 if t is not in the Table
     */
    public int search(Customer t) {
        int bucket = hash(t);
        if(bucket < 0 || bucket > Table.size() - 1) {
            return -1;
        }
        List<Customer> list = Table.get(bucket);
        for(int i = 0; i < list.getLength();i++) {
            if(list.linearSearch(t) != -1) {
                return bucket;
            }
        }
        return -1;
    }

    public void searchName(String name) {
        int hashCode = 0;
        for(int i = 0; i < name.length();i++) {
            hashCode += (int) name.charAt(i);
        }
        int bucket = hashCode % Table.size();

        if(bucket < 0 || bucket > Table.size() - 1) {
            return;
        }

        List<Customer> list = Table.get(bucket);
        list.pointIterator();
        boolean flag = false;
        for(int i = 0; i < list.getLength();i++) {
            Customer temp = list.getIterator();
            if(name.equals(temp.getFistName() + temp.getLastName()))  {
                flag = true;
                System.out.println(temp.toString());
            }
            list.advanceIterator();
        }
        if(!flag) {
            System.out.println("This person is not in our customer database.\n");
        }
    }

    /**Manipulation Procedures*/

    /**
     * Inserts a new key in the Table
     * calls the hash method to determine placement
     * @param t the key to insert
     */
    public void insert(Customer t) {
        int bucket = hash(t);
        Table.get(bucket).addLast(t);
        numElements++;
    }

    /**
     * removes the key t from the Table
     * calls the hash method on the key to
     * determine correct placement
     * has no effect if t is not in
     * the Table
     * @param t the key to remove
     */
    public void remove(Customer t) {
        int bucket = search(t);
        if(bucket == -1) {
            return;
        }
        List<Customer> list = Table.get(bucket);
        list.pointIterator();

        for(int i = 0; i < list.getLength();i++) {
            if(list.getIterator().equals(t)) {
                list.removeIterator();
                numElements--;
                return;
            }
            list.advanceIterator();
        }
    }

    /**Additional Methods*/

    /**
     * Prints all the keys at a specified
     * bucket in the Table. Each key displayed
     * on its own line, with a blank line
     * separating each key
     * Above the keys, prints the message
     * "Printing bucket #<bucket>:"
     * Note that there is no <> in the output
     * @param bucket the index in the Table
     */
    public void printBucket(int bucket) {
        //System.out.println("Printing bucket #" + bucket + ":\n");
        if(bucket < 0 || bucket > Table.size() - 1) {
            System.out.println("The bucket number is out of range!");
            return;
        }
        List<Customer> list = Table.get(bucket);
        list.pointIterator();
        for(int i = 0; i < list.getLength();i++){
            System.out.println(list.getIterator()+ "\n");
            list.advanceIterator();
        }
    }

    /**
     * Prints the first key at each bucket
     * along with a count of the total keys
     * with the message "+ <count> -1 more
     * at this bucket." Each bucket separated
     * with to blank lines. When the bucket is
     * empty, prints the message "This bucket
     * is empty." followed by two blank lines
     */
    public void printTable(){
        for(int i = 0; i < Table.size(); i++){
            System.out.println("Bucket: " + i);
            if(Table.get(i).getLength() > 0) {

                System.out.println(Table.get(i).getFirst() + " \n+ " + (Table.get(i).getLength() - 1) + " more at this bucket.\n");
            }else {
                System.out.println("This bucket is empty!\n");
            }
        }
    }
}

