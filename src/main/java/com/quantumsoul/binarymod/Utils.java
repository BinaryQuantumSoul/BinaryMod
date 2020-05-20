package com.quantumsoul.binarymod;

public class Utils
{
    /**
     * Return an element selected by position in iteration order.
     *
     * @param data The source from which an element is to be selected
     * @param n    The index of the required element. If it is not in the
     *             range of elements of the iterable, the method returns null.
     * @return The selected element.
     */
    public static final <T> T nthElement(Iterable<T> data, int n)
    {
        int index = 0;
        for (T element : data)
        {
            if (index == n)
            {
                return element;
            }
            index++;
        }
        return null;
    }


    /**
     * Return an element position in iteration order.
     *
     * @param data    The source from which an element is to be selected
     * @param element The element. If it is not in the range of elements of the iterable, the method returns -1.
     * @return The index.
     */
    public static final <T> int elementIndex(Iterable<T> data, T element)
    {
        int index = 0;
        for (T e : data)
        {
            if (element == e)
            {
                return index;
            }
            index++;
        }
        return -1;
    }
}
