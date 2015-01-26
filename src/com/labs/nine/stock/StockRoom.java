package com.labs.nine.stock;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adrian Cooney (12394581)
 * 18/11/14 com.labs.nine
 */
public class StockRoom {

    // The reference to the stock array list
    private HashMap<String, Integer> stock;

    public StockRoom() {
        stock = new HashMap<String, Integer>();
    }

    /**
     * Add an item to stock.
     * @param item
     */
    public synchronized void add(String item) {
        if(!stock.containsKey(item)) stock.put(item, 1);
        else stock.put(item, stock.get(item) + 1);
    }

    /**
     * Remove item from stock.
     * @param item
     */
    public synchronized void remove(String item) {
        Integer value = stock.get(item) - 1;
        if(value == 0) stock.remove(item);
        else stock.put(item, value);
    }

    /**
     * Remove item from stock.
     * @param item
     * @return int with stock count.
     */
    public synchronized int check(String item) {
        Integer value = stock.get(item);
        return value == null ? 0 : value;
    }

    /**
     * Check if the stock room is empty.
     * @return boolean
     */
    public synchronized boolean isEmpty() {
        return this.stock.isEmpty();
    }
}
