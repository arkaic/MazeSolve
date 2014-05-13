



package assignment6.dlist;

/**
 *
 * @author henry
 */
public class DNode<E>
{
    private DNode<E> next;
    private DNode<E> previous;
    private E element;

    public DNode()
    {
        this(null, null, null);
    }

    public DNode(E element)
    {
        this(element, null, null);
    }

    public DNode(E element, DNode<E> previous, DNode<E> next)
    {
        this.element = element;
        this.next = next;
        this.previous = previous;
    }
    
    public DNode<E> getNext()
    {
        return next;
    }

    public DNode<E> getPrevious()
    {
        return previous;
    }
    
    public E getElement()
    {
        return element;
    }

    public void setNext(DNode<E> newNext)
    {
        next = newNext;
    }
    
    public void setPrevious(DNode<E> newPrevious)
    {
        previous = newPrevious;
    }

    public void setElement(E newElement)
    {
        element = newElement;
    }

    @Override
    public String toString()
    {
        return "(Node:" + element.toString() + ")";
    }
}
