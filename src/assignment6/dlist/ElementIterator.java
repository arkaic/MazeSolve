/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment6.dlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterates over a doubly linked list's nodes' elements.
 * Operation: Sets the next iteration as the return for next(). If there is no 
 *      next, an exception throw is "set up" for it. So when next() is called,
 *      it is already targeted on either an actual next or a null representing
 *      none.
 * @author henry
 */
public class ElementIterator<E> implements Iterator
{
    DLinkedList<E> list;
    DNode<E> cursor;
    
    public ElementIterator(DLinkedList<E> list)
    {
        this.list = list;
        cursor = (list.isEmpty()) ? null : list.getFirst();
    }
    
    @Override
    public boolean hasNext()
    {
        return (cursor != null);
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**Returns position of cursor, then moves cursor to next position*/
    @Override
    public E next()
    {
        if (!hasNext()) {
            throw new NoSuchElementException("No next element");
        } else {
            E returnElement = cursor.getElement();
            cursor = (cursor == list.getLast()) ? null : cursor.getNext();
            return returnElement;
        }
    }
}
