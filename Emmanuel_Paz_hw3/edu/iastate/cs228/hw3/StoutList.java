package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author emmanuelpaz
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
    /**
     * Default number of elements that may be stored in each node.
     */
    private static final int DEFAULT_NODESIZE = 4;

    /**
     * Number of elements that can be stored in each node.
     */
    private final int nodeSize;

    /**
     * Dummy node for head.  It should be private but set to public here only
     * for grading purpose.  In practice, you should always make the head of a
     * linked list a private instance variable.
     */
    public Node head;

    /**
     * Dummy node for tail.
     */
    private Node tail;

    /**
     * Number of elements in the list.
     */
    private int size;

    /**
     * Constructs an empty list with the default node size.
     */
    public StoutList()
    {
        this(DEFAULT_NODESIZE);
    }

    /**
     * Constructs an empty list with the given node size.
     * @param nodeSize number of elements that may be stored in each node, must be
     *   an even number
     */
    public StoutList(int nodeSize)
    {
        if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();

        // dummy nodes
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
        this.nodeSize = nodeSize;
    }

    /**
     * Constructor for grading only.  Fully implemented.
     * @param head
     * @param tail
     * @param nodeSize
     * @param size
     */
    public StoutList(Node head, Node tail, int nodeSize, int size)
    {
        this.head = head;
        this.tail = tail;
        this.nodeSize = nodeSize;
        this.size = size;
    }

    /**
     * size of array of elements
     * @return how many elements
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * adds item at the end of list
     * @param item type of item
     * @return return true if added, false if not
     */
    @Override
    public boolean add(E item)
    {
        if(item == null)
            throw new NullPointerException();
        else if(tail.previous == head){
            Node newNode = new Node();
            newNode.previous = head;
            head.next = newNode;
            tail.previous = newNode;
            newNode.next = tail;
            newNode.addItem(item);
            size++;
            return true;
        }
        else if(tail.previous.count == nodeSize){
            Node newNode = new Node();
            newNode.previous = tail.previous;
            newNode.next = tail;
            tail.previous.next = newNode;
            tail.previous = newNode;
            newNode.addItem(item);
            size++;
            return true;
        }
        else if(tail.previous.count < nodeSize){
            tail.previous.addItem(item);
            size++;
            return true;
        }
        return false;
    }

    /**
     * add to list in specific position
     * @param pos the position
     * @param item the item
     */
    @Override
    public void add(int pos, E item)
    {
        if(pos < 0 || pos > size)
            throw new IndexOutOfBoundsException();

        int count = 0;
        int offset = 0;
        Node currentNode = head.next;
        while(count + currentNode.count <= pos-1){
            count += currentNode.count;
            currentNode = currentNode.next;
        }
        offset = pos - count;
        if(pos == size){
            add(item);
        }
        else if(head.next == tail && tail.previous == head){
            Node newNode = new Node();
            newNode.previous = head;
            newNode.next = tail;
            head.next = newNode;
            tail.previous = newNode;
            newNode.addItem(0, item);
            size++;
        }
        else if(pos == 0){
            if(currentNode.previous.count < nodeSize && currentNode.previous != head){
                currentNode.previous.addItem(0, item);
            }
            if(currentNode.next == tail && currentNode.previous.count == nodeSize){
                Node newNode = new Node();
                newNode.next = tail;
                newNode.previous = currentNode;
                tail.previous = newNode;
                currentNode.next = newNode;
                newNode.addItem(0, item);
            }
            if(currentNode.count < nodeSize){
                currentNode.addItem(0, item);
            }
            size++;
        }
        else if(currentNode.count < nodeSize){
            currentNode.addItem(offset, item);
            size++;
        }
        else{
            Node newNode = new Node();
            newNode.previous = currentNode;
            newNode.next = currentNode.next;
            currentNode.next.previous = newNode;
            currentNode.next = newNode;
            int j = 0;
            for(int i = nodeSize/2; currentNode.data[i] != null; j++){
                newNode.addItem(j, currentNode.data[i]);
                currentNode.removeItem(i);
            }
            if(offset <= nodeSize/2)
                currentNode.addItem(offset, item);
            if(offset > nodeSize/2)
                newNode.addItem(offset-nodeSize/2, item);
            size++;
        }
    }

    /**
     * remove item in specific position
     * @param pos the index
     * @return the object removed
     */
    @Override
    public E remove(int pos)
    {
        if(pos < 0 || pos > size){
            throw new IndexOutOfBoundsException();
        }
        E returnData = null;
        int count = 0;
        int offset = 0;
        Node currentNode = head.next;
        while(count + currentNode.count <= pos){
            count += currentNode.count;
            currentNode = currentNode.next;
        }
        offset = pos - count;
        if(currentNode.next == tail && currentNode.count == 1){
            returnData = currentNode.data[0];
            currentNode.removeItem(0);
            currentNode.previous.next = tail;
            tail.previous = currentNode.previous;
            size--;
        }
        else if(currentNode.next == tail || currentNode.count > nodeSize/2){
            returnData = currentNode.data[offset];
            currentNode.removeItem(offset);
            size--;
        }
        else{
            if(currentNode.next.count > nodeSize/2){
                returnData = currentNode.data[offset];
                currentNode.removeItem(offset);
                E tempData = currentNode.next.data[0];
                currentNode.next.removeItem(0);
                currentNode.addItem(tempData);
            }
            else if(currentNode.next.count <= nodeSize/2){
                returnData = currentNode.data[offset];
                currentNode.removeItem(offset);
                int j = 0;
                for(int i = 0; i<currentNode.next.data.length;i++){
                    if(currentNode.next.data[j] != null){
                        currentNode.addItem(currentNode.next.data[j]);
                        currentNode.next.removeItem(0);
                    }
                }
                currentNode.next.next.previous = currentNode;
                currentNode.next = currentNode.next.next;
            }
            size--;
        }

        return returnData;
    }

    /**
     * Sort all elements in the stout list in the NON-DECREASING order. You may do the following.
     * Traverse the list and copy its elements into an array, deleting every visited node along
     * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting
     * efficiency is not a concern for this project.)  Finally, copy all elements from the array
     * back to the stout list, creating new nodes for storage. After sorting, all nodes but
     * (possibly) the last one must be full of elements.
     *
     * Comparator<E> must have been implemented for calling insertionSort().
     */
    public void sort()
    {
        Comparator<E> comp = new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return o1.compareTo(o2);
            }
        };
        int howMany = 0;
        for(Node eachNode = head.next; eachNode != null; eachNode = eachNode.next){
            howMany += eachNode.count;
        }
        E[] newData = (E[]) new Comparable[howMany];
        Node pending = head.next;
        Node remover = head;
        int j = 0;
        int i = 0;
        while(i < size){
            for(E obj : pending.data){
                if(obj != null) {
                    newData[j++] = obj;
                    i++;
                }
            }
            remover = remover.next;
            remover.previous.next = null;
            pending.previous = null;
            pending = pending.next;

        }
        pending.next = null;
        head.next = tail;
        tail.previous = head;
        size = 0;
        insertionSort(newData, comp);
        for(E k : newData){
            add(k);
        }
    }


    /**
     * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
     * method.  After sorting, all but (possibly) the last nodes must be filled with elements.
     *
     * Comparable<? super E> must be implemented for calling bubbleSort().
     */
    public void sortReverse()
    {
        int howMany = 0;
        for(Node eachNode = head.next; eachNode != null; eachNode = eachNode.next){
            howMany += eachNode.count;
        }
        E[] newData = (E[]) new Comparable[howMany];
        Node pending = head.next;
        Node remover = head;
        int j = 0;
        int i = 0;
        while(i < size){
            for(E obj : pending.data){
                if(obj != null) {
                    newData[j++] = obj;
                    i++;
                }
            }
            remover = remover.next;
            remover.previous.next = null;
            pending.previous = null;
            pending = pending.next;

        }
        pending.next = null;
        head.next = tail;
        tail.previous = head;
        size = 0;
        bubbleSort(newData);
        for(E k : newData){
            add(k);
        }
    }

    /**
     * makes a new listIterator
     * @return StoutListIterator
     */
    @Override
    public Iterator<E> iterator()
    {
        return new StoutListIterator();
    }
    /**
     * makes a new listIterator
     * @return StoutListIterator
     */
    @Override
    public ListIterator<E> listIterator()
    {
        return new StoutListIterator();
    }
    /**
     * makes a new listIterator
     * @return StoutListIterator
     */
    @Override
    public ListIterator<E> listIterator(int index)
    {
        return new StoutListIterator(index);
    }

    /**
     * Returns a string representation of this list showing
     * the internal structure of the nodes.
     */
    public String toStringInternal()
    {
        return toStringInternal(null);
    }

    /**
     * Returns a string representation of this list showing the internal
     * structure of the nodes and the position of the iterator.
     *
     * @param iter
     *              an iterator for this list
     */
    public String toStringInternal(ListIterator<E> iter)
    {
        int count = 0;
        int position = -1;
        if (iter != null) {
            position = iter.nextIndex();
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = head.next;
        while (current != tail) {
            sb.append('(');
            E data = current.data[0];
            if (data == null) {
                sb.append("-");
            } else {
                if (position == count) {
                    sb.append("| ");
                    position = -1;
                }
                sb.append(data.toString());
                ++count;
            }

            for (int i = 1; i < nodeSize; ++i) {
                sb.append(", ");
                data = current.data[i];
                if (data == null) {
                    sb.append("-");
                } else {
                    if (position == count) {
                        sb.append("| ");
                        position = -1;
                    }
                    sb.append(data.toString());
                    ++count;

                    // iterator at end
                    if (position == size && count == size) {
                        sb.append(" |");
                        position = -1;
                    }
                }
            }
            sb.append(')');
            current = current.next;
            if (current != tail)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }


    /**
     * Node type for this list.  Each node holds a maximum
     * of nodeSize elements in an array.  Empty slots
     * are null.
     */
    private class Node
    {
        /**
         * Array of actual data elements.
         */
        // Unchecked warning unavoidable.
        public E[] data = (E[]) new Comparable[nodeSize];

        /**
         * Link to next node.
         */
        public Node next;

        /**
         * Link to previous node;
         */
        public Node previous;

        /**
         * Index of the next available offset in this node, also
         * equal to the number of elements in this node.
         */
        public int count;

        /**
         * Adds an item to this node at the first available offset.
         * Precondition: count < nodeSize
         * @param item element to be added
         */
        void addItem(E item)
        {
            if (count >= nodeSize)
            {
                return;
            }
            data[count++] = item;
            //useful for debugging
            //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
        }

        /**
         * Adds an item to this node at the indicated offset, shifting
         * elements to the right as necessary.
         *
         * Precondition: count < nodeSize
         * @param offset array index at which to put the new element
         * @param item element to be added
         */
        void addItem(int offset, E item)
        {
            if (count >= nodeSize)
            {
                return;
            }
            for (int i = count - 1; i >= offset; --i)
            {
                data[i + 1] = data[i];
            }
            ++count;
            data[offset] = item;
            //useful for debugging
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
        }

        /**
         * Deletes an element from this node at the indicated offset,
         * shifting elements left as necessary.
         * Precondition: 0 <= offset < count
         * @param offset 
         */
        void removeItem(int offset)
        {
            E item = data[offset];
            for (int i = offset + 1; i < nodeSize; ++i)
            {
                data[i - 1] = data[i];
            }
            data[count - 1] = null;
            --count;
        }
    }

    /**
     * This class implements a listIterator for the StoutList
     */
    private class StoutListIterator implements ListIterator<E>
    {
        // constants you possibly use ...
        private static final int BEHIND = -1;
        private static final int AHEAD = 1;
        private static final int NONE = 0;
        // instance variables ...
        private E cursor;
        private Node currentNode;
        private int direction;
        private int index;
        private int offset;
        private E currentData;
        private boolean removable;
        private boolean setable;
        /**
         * Default constructor
         */
        public StoutListIterator()
        {
            this(0);
        }

        /**
         * Constructor finds node at a given position.
         * @param pos the index wanting to start at
         */
        public StoutListIterator(int pos)
        {
            if(pos<0 || pos > size){
                throw new IndexOutOfBoundsException();
            }

            currentNode = head.next;
            int count = 0;
            while(count + currentNode.count <= pos){
                count += currentNode.count;
                currentNode = currentNode.next;
            }
            offset = pos - count;
            cursor = currentNode.data[offset];
            direction = NONE;
            index = pos;
            currentData = cursor;
        }

        /**
         * if the list has a next element
         * @return true if there is a element
         */
        @Override
        public boolean hasNext()
        {
            return index < size;
        }

        /**
         * moves to the next element
         * @return returns the element that the cursor was on before moving
         */
        @Override
        public E next()
        {
            if(hasNext()){
                currentData = currentNode.data[offset];
                if (offset + 1 == nodeSize || currentNode.data[offset + 1] == null && currentNode.next != tail) {
                    currentNode = currentNode.next;
                    offset = 0;
                    if(currentNode == tail)
                    cursor = currentNode.data[offset];
                }
                else{
                    cursor = currentNode.data[offset++];
                }
                index++;
                direction = AHEAD;
                removable = true;
                setable = true;
                return currentData;
            }
            else{
                throw new NoSuchElementException();
            }
        }

        /**
         * checks if you haven't reached the beginning of the list
         * @return true if not reached beginning
         */
        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        /**
         * moves to the element to the left
         * @return the element that it moved onto
         */
        @Override
        public E previous() {
            if(hasPrevious()){
                if(offset - 1 < 0){
                    currentNode = currentNode.previous;
                    offset = currentNode.count-1;
                    cursor = currentNode.data[offset];

                }
                else {
                    cursor = currentNode.data[offset--];
                }
                currentData = currentNode.data[offset];
                index--;
                direction = BEHIND;
                removable = true;
                setable = true;
                return currentData;

            }
            else{
                throw new NoSuchElementException();
            }
        }

        /**
         * returns the next index or where the cursor is
         * @return index
         */
        @Override
        public int nextIndex() {
            return index;
        }

        /**
         * returns the index that was before the current index
         * @return previous index
         */
        @Override
        public int previousIndex() {
            return index - 1;
        }

        /**
         * removes element based on if next() or previous() was called
         */
        @Override
        public void remove()
        {
            if(direction == NONE || removable != true)
                throw new IllegalStateException();
            else if(direction == BEHIND) {
                StoutList.this.remove(index);
                cursor = currentNode.data[index];

            }
            else if(direction == AHEAD) {
                StoutList.this.remove(index-1);
                cursor = currentNode.data[offset];
                index--;
                offset--;
            }
            removable = false;
            setable = false;
        }

        /**
         * sets an element based on if next() or previous() was called
         * @param e the element to be set
         */
        @Override
        public void set(E e) {
            if(!setable)
                throw new IllegalStateException();
            else if(e == null)
                throw new NullPointerException();
            else if(direction == AHEAD)
                currentNode.data[offset-1] = e;
            else if(direction == BEHIND)
                currentNode.data[offset] = e;

        }

        /**
         * adds an item behind the cursor
         * @param e the element to be added
         */
        @Override
        public void add(E e) {
            if(e == null)
                throw new NullPointerException();
            else if(direction == AHEAD) {
                if(index == 0)
                    StoutList.this.add(index, e);
                else
                    StoutList.this.add(index-1, e);
                index++;
                offset++;
            }
            else if(direction == BEHIND) {
                StoutList.this.add(index, e);
                index++;
                offset++;
            }
            setable = false;
            removable =false;

        }

        // Other methods you may want to add or override that could possibly facilitate
        // other operations, for instance, addition, access to the previous element, etc.
        //
        // ...
        //
    }


    /**
     * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order.
     * @param arr   array storing elements from the list
     * @param comp  comparator used in sorting
     */
    private void insertionSort(E[] arr, Comparator<? super E> comp)
    {
        int j=0;
        for(int i = 1; i < arr.length; i++){
            j=i;
            while(j > 0 && comp.compare(arr[j], arr[j-1]) < 0){
                E temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            }
        }
    }

    /**
     * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
     * description of bubble sort please refer to Section 6.1 in the project description.
     * You must use the compareTo() method from an implementation of the Comparable
     * interface by the class E or ? super E.
     * @param arr  array holding elements from the list
     */
    private void bubbleSort(E[] arr)
    {
        for(int i = 0; i < arr.length-1;i++){
            for(int j = 0; j < arr.length-i-1;j++){
                if(arr[j].compareTo(arr[j+1]) < 0){
                    E temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

    }


}