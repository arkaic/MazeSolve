
package assignment6.dlist;

import java.util.Iterator;

/**
 *  Doubly linked list used mainly to hold edge lists.
 * @author henry
 */
public class DLinkedList<E> implements Iterable<E>
{
    protected int size;
    protected DNode<E> header, trailer;
    
    public DLinkedList()
    {
        size = 0;
        header = new DNode<>(null, null, null);
        trailer = new DNode<>(null, header, null);
        header.setNext(trailer);
    }
    
    public int size()
    {
        return size;
    }
    
    public Boolean isEmpty()
    {
        return (size == 0);
    }
    
    public void clear()
    {
        header.setNext(null);
        trailer.setPrevious(null);
        size = 0;
    }
    
    public DNode<E> getFirst() throws IllegalStateException
    {
        if (isEmpty())
            throw new IllegalStateException("List is empty");
        else
            return header.getNext();
    }
    
    public DNode<E> getLast() throws IllegalStateException
    {
        if (isEmpty())
            throw new IllegalStateException("List is empty");
        else
            return trailer.getPrevious();
    }
    
    public DNode<E> getNext(DNode<E> node)
    {
        if (node == trailer)
            throw new IllegalStateException("Can't move past trailer of list");
        else
            return node.getNext();
    }
    
    public DNode<E> getPrevious(DNode<E> node)
    {
        if (node == header)
            throw new IllegalStateException("Can't move before header of list");
        else
            return node.getPrevious();
    }
    
    public void addFirst(DNode<E> node)
    {
        getNext(header).setPrevious(node);
        node.setNext(getNext(header));
        node.setPrevious(header);
        header.setNext(node);
        size++;
    }
    
    public void addFirst(E element)
    {
        addFirst(new DNode<>(element));
    }    
    
    public void addLast(DNode<E> node)
    {
        getPrevious(trailer).setNext(node);
        node.setPrevious(getPrevious(trailer));
        node.setNext(trailer);
        trailer.setPrevious(node);
        size++;
    }
    
    public void addLast(E element)
    {
        addLast(new DNode<>(element));
    }
    
    public void addBefore(DNode<E> node, DNode<E> beforeNode)
    {
        DNode<E> previousNode = getPrevious(node);
        node.setPrevious(beforeNode);
        beforeNode.setNext(node);
        beforeNode.setPrevious(previousNode);
        previousNode.setNext(beforeNode);
        size++;
    }
    
    public void addAfter(DNode<E> node, DNode<E> afterNode)
    {
        DNode<E> nextNode = getNext(node);
        nextNode.setPrevious(afterNode);
        afterNode.setNext(nextNode);
        afterNode.setPrevious(node);
        node.setNext(afterNode);
        size++;
    }
    
    public void remove(DNode<E> node)
    {
        getNext(node).setPrevious(getPrevious(node));
        getPrevious(node).setNext(getNext(node));
        node.setPrevious(null);
        node.setPrevious(null);
        size--;
    }
    
    public Boolean hasNext(DNode<E> node)
    {
        return (node != trailer);
    }
    
    public Boolean hasPrevious(DNode<E> node)
    {
        return (node != header);
    }
    
    @Override
    public String toString()
    {
        if (isEmpty())
        {
            return "List is empty";
        }
        else
        {
            String s = "";
            DNode<E> pointer = header.getNext();
            while (pointer != trailer)
            {
                s += "[" + pointer + "]";
                pointer = pointer.getNext();
            }
            return s;
        }
    }

    /**Returns an iterable list of elements in the nodes*/
    @Override
    public Iterator<E> iterator()
    {
        return new ElementIterator<>(this);
    }
    
    /**     Returns an iterable list of nodes. 
     * It builds up another DLinkedList of nodes which hold nodes holding the 
     * element E. With the iterable that results, it can then call its own iterator
     * that iterates over the nodes instead of the elements.
     * 
     * MazeConstruct
     */
    public Iterable<DNode<E>> nodes()
    {
        DLinkedList<DNode<E>> nodes = new DLinkedList<>();
        if (!isEmpty())
        {
            DNode<E> node = getFirst();
            while (node != trailer)
            {
                nodes.addLast(new DNode<>(node));
                node = node.getNext();
            }
        }
        return nodes;
    }
}
