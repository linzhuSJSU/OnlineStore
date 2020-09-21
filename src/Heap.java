package courseProject;/* heap using array from 0 not 1
 */
//import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.ArrayList;

public class Heap {

    private int heap_size;
    private ArrayList<Order> heap;

    public Heap(ArrayList<Order> heap) {
        this.heap = heap;
    }

    private void heap_increase_key(int i, Order newOrder){
        if (heap.get(i) == null || compareOrder(newOrder, heap.get(i)) > 0) {
            heap.set(i,newOrder);
            while(i > 0 && compareOrder(heap.get(get_parent(i)), heap.get(i)) < 0) {
                Order temp = heap.get(i);
                heap.set(i, heap.get(get_parent(i)));
                heap.set(get_parent(i), temp);
                i = get_parent(i);
            }
        }
    }


    /**Constructors*/

    public Heap(){
        this.heap_size = 0;
        heap = new ArrayList<>();
    }

    /**Mutators*/

    public void buildHeap(){
        for(int i = heap_size / 2 -1; i >= 0; i--){
            heapifyDown(heap_size, i);
        }
    }

    public void insert(Order newOrder){
        heap_size++;
        heap.add(heap_size-1, null);
        heap_increase_key(heap_size-1, newOrder);

    }

    public void remove(int index){
        Order item = heap.get(index);
        heap.set(index, heap.get(heap_size - 1));
        heap.remove(heap_size-1);
        heap_size--;
        heapifyDown(heap_size,index);
    }

    public ArrayList<Order> sort() {
        int n = heap_size;

        for (int i = n-1; i>= 1; i--){
            Order temp = heap.get(0);
            heap.set(0, heap.get(i));
            heap.set(i, temp); //swap


            n --; //consider your heap to be one smaller

            heapifyDown(n,0); //restore max heap property
        }
        return heap;
    }

    /**Accessors*/

    public Order getMax(){
        return heap.get(0);
    }

    public int get_parent(int i) {
        return (i -1) / 2;
    }

    public int getLeft(int i) {
        return 2 * i + 1;
    }

    public int getRight(int i) {
        return 2 * i + 2;
    }

    public int getSize() {
        return heap_size;
    }

    public Order getElement(int i) {
        return heap.get(i);
    }


    private void heapifyDown(int length, int index){
        Order temp = heap.get(index);
        int indexOfSon = 2 * index +1;
        while (indexOfSon < length)
        {
            if (indexOfSon + 1 < length && compareOrder(heap.get(indexOfSon + 1), heap.get(indexOfSon)) > 0)
                indexOfSon++;
            if (compareOrder(heap.get(indexOfSon), temp) < 0)
                break;
            heap.set(index, heap.get(indexOfSon));
            index = indexOfSon;
            indexOfSon = 2 * index + 1;
        }
        heap.set(index, temp);
    }

    public Order searchOrder(int id) {
        for (int i = 0; i < heap_size; i++) {
            if (heap.get(i).getId() == id) {
                return heap.get(i);
            }
        }
        return null;
    }

    /**Additional Operations*/

    //recommended
    @Override public String toString(){
        return heap.toString();
    }

    public void displayArray(){
        System.out.println(heap.toString());
    }

    private int compareOrder(Order o1, Order o2) {
        if (o1.getStatus() == o2.getStatus()) {
            if (o1.getOrderDate().compareTo(o2.getOrderDate()) == 0) {
                return Integer.compare(o1.getShipment().getSpeedCode(), o2.getShipment().getSpeedCode());
            }
            return o1.getOrderDate().compareTo(o2.getOrderDate()) * -1;
        }
        return o1.getStatus() == 1 ? -1 : 1;
    }
}
