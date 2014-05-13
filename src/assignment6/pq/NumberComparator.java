
package assignment6.pq;

import java.util.Comparator;

/**
 * Number comparator for Entry class in a Priority Queue in which keys are just
 * numbers. 
 * @author henry
 */
public class NumberComparator implements Comparator
{
    public int counter = 0;
    @Override
    public int compare(Object o1, Object o2)
    {
        counter++;
        //throw some exceptions saying either invalid entry or invalid key
        Entry entry1 = (Entry) o1;
        Entry entry2 = (Entry) o2;
        if (entry1.getKey() > entry2.getKey())
            return 1;
        else if (entry1.getKey() < entry2.getKey())
            return -1;
        else 
            return 0;
    }
}
