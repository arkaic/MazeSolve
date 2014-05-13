
package assignment6.pq;

import assignment6.Edge;
import assignment6.Vertex;
import java.util.Comparator;

/**
 * [Reusable; just re-edit insert() and use your own Entry class.
 * If you don't provide a comparator, a default one is provided which assumes that
 * the entries' keys are numbers.
 * @author henry
 */
public class MyPriorityQueue
{
    Entry[] array;
    int indexLastEntry = 0;
    private Comparator comparator;
    
    public MyPriorityQueue(int size)
    {
        this(size, new NumberComparator());
    }
    
    public MyPriorityQueue(int size, Comparator comparator)
    {
        array = new Entry[size + 1];
        this.comparator = comparator;
    }
    
    public Boolean isEmpty()
    {
        if (indexLastEntry > 0)
            return false;
        else
            return true;
    }
    
    /*Inserts entry and then heaps up if larger**/
    public void insert(int keyCloudDist, Vertex elemVertex, Edge elemEdge)
    {
        Entry entry = new Entry(keyCloudDist, elemVertex, elemEdge);
        elemVertex.setEntry(entry);
        indexLastEntry++;
        array[indexLastEntry] = entry;
        elemVertex.setEntryIndex(indexLastEntry);
        //if not a root
        if (indexLastEntry > 1) 
            bubbleUp(indexLastEntry);
    }
    
    public Entry removeMin()
    {
        Entry minEntry = removeTopEntry();
        moveLastEntryToRoot();
        bubbleDown(1);
        return minEntry;
    }
    
    private Entry removeTopEntry()
    {
        Entry topEntry = array[1];
        array[1] = null;
        return topEntry;
    }
    
    private void moveLastEntryToRoot()
    {
        Entry lastEntry = array[indexLastEntry];
        if (lastEntry != null)
        {
            array[1] = lastEntry;
            lastEntry.getVertex().setEntryIndex(1);
            array[indexLastEntry] = null;
        }
        indexLastEntry--;
    }
     
    /**
     *  Either heapUp/heapDown will be run in full but not both.
     */
    public void reBubble(int index)
    {
        bubbleUp(index);
        bubbleDown(index);
    }
    
    private void bubbleUp(int index)
    {
        if (index == 1)
            return;
        if (isEntrySmallerThanParent(index))
        {
            Entry bubbleEntry = array[index];
            Entry parent = getParent(index);
            int parentIndex = index/2;
            
            array[index] = parent;
            Vertex parentVertex = parent.getVertex();
            parentVertex.setEntryIndex(index);            
            
            array[parentIndex] = bubbleEntry;
            Vertex heapVertex = bubbleEntry.getVertex();
            heapVertex.setEntryIndex(parentIndex);
            bubbleUp(parentIndex);
        }
    }
    
    /**
     * Bubbles down an entry from the root position. The conditionals are split up
     * into either: 1. There is no right child.
     *              2. There are both children.
     */
    private void bubbleDown(int index)
    {
        if (isExternal(index))
            return;
        if (!hasRight(index))
            bubbleDownLeftChild(index);
        else //if there is a right child...
        {
            if (isEntryLargerThanEitherChild(index))
                bubbleDownSmallestChild(index);
        }
    }
    
    private Boolean isEntrySmallerThanParent(int index)
    {
        Entry entry = array[index];
        if (comparator.compare(entry, getParent(index)) == -1)
            return true;
        else 
            return false;
    }
    
    private void bubbleDownLeftChild(int index) 
    {
        if (isEntryLargerThanLeftChild(index))
        {
            Entry bubbleEntry = array[index];
            Entry leftChild = getLeftChild(index);
            array[index] = leftChild;
            Vertex leftChildVertex = leftChild.getVertex();
            leftChildVertex.setEntryIndex(index);
            array[index*2] = bubbleEntry;
            Vertex vertex = bubbleEntry.getVertex();
            vertex.setEntryIndex(index * 2);
            bubbleDown(index*2);
        }
    }
    
    private Boolean isEntryLargerThanLeftChild(int index)
    {
        Entry entry = array[index];
        if (comparator.compare(entry, getLeftChild(index)) == 1)
            return true;
        else
            return false;
    }
    
    private Boolean isEntryLargerThanEitherChild(int index)
    {
        Entry entry = array[index];
        if ((comparator.compare(entry, getLeftChild(index)) == 1)
                || (comparator.compare(entry, getRightChild(index)) == 1))
            return true;
        else
            return false;
    }
    
    private void bubbleDownSmallestChild(int index)
    {
        Entry entry = array[index];
        Vertex entryVertex = entry.getVertex();
        Entry leftChild = getLeftChild(index);
        Vertex leftChildVertex = leftChild.getVertex();
        Entry rightChild = getRightChild(index);
        Vertex rightChildVertex = rightChild.getVertex();
        if (comparator.compare(leftChild, rightChild) <= 0) 
        {
            array[index] = leftChild;
            leftChildVertex.setEntryIndex(index);
            array[index * 2] = entry;
            entryVertex.setEntryIndex(index * 2);
            bubbleDown(index * 2);
        } 
        else 
        {
            array[index] = rightChild;
            rightChildVertex.setEntryIndex(index);
            array[(index * 2) + 1] = entry;
            entryVertex.setEntryIndex((index * 2) + 1);
            bubbleDown((index * 2) + 1);
        }
    }
    
    private Boolean hasLeft(int index)
    {
        if (isBottomLevel(index))
            return false;
        if (array[index*2] == null)
            return false;
        else
            return true;
    }
    
    private Boolean hasRight(int index)
    {
        if (isBottomLevel(index))
            return false;
        if (array[(index*2) + 1] == null)
            return false;
        else 
            return true;
    }
    
    /**Checks if index is at bottom level of heap tree**/
    private Boolean isBottomLevel(int index)
    {
        if ((index * 2) >= (array.length))
            return true;
        else
            return false;
    }
    
    /*Being external means being sterile**/
    private Boolean isExternal(int index)
    {
        if (hasLeft(index) || hasRight(index))
            return false;
        else
            return true;
    }
    
    /*Assumes hasLeft is checked for**/
    private Entry getLeftChild(int index)
    {
        return array[index * 2];
    }
    
    /*Assumes hasRight is checked for**/
    private Entry getRightChild(int index)
    {
        return array[1 + (index * 2)];
    }
    
    /**Assumes index != 1 */
    private Entry getParent(int index)
    {
        return array[index / 2];
    }
    
    private long power(long value, int power) 
    {
        if (power > 0) {
            long v = value * power(value, power - 1);
            return v;
        } else {
            return 1;
        }
    }
    
    @Override
    public String toString()
    {
        String s = "";
        int powCounter = 1;
        for (int i = 1; i < array.length; i++)
        {
            s += "(" + i + ")-" + array[i].toString() + ", ";
            if (i == (power(2, powCounter) - 1))
            {
                s += "\n";
                powCounter++;
            }
        }
        return s;
    }
}
