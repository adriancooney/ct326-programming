package com.labs.nine.stock;

/**
 * Adrian Cooney (12394581)
 * 17/11/14 com.labs.nine
 */
public class StockAttendant implements Runnable {

    // The reference to the stock array list
    private StockRoom stock;
    public String name;
    public static int _uid = 0;

    /**
     * Create a new stock room with a store reference.
     * @param store
     */
    public StockAttendant(StockRoom store) {
        this.name = "Stock Attendant #" + (++_uid);
        stock = store;
    }

    /**
     * Add an item to stock.
     * @param item
     */
    public void add(String item) {
        System.out.println(String.format("%s is adding '%s' to the stock room.", this.name, item));
        stock.add(item);
    }

    /**
     * Remove item from stock.
     * @param item
     */
    public void remove(String item) {
        System.out.println(String.format("%s is removing '%s' from the stock room.", this.name, item));
        stock.remove(item);
    }

    /**
     * Remove item from stock.
     * @param item
     * @return int with stock count.
     */
    public void check(String item) {
        System.out.println(String.format("%s is checking '%s' in the stock room.", this.name, item));
        stock.check(item);
    }

    /**
     * Run a random operation on the stock room.
     */
    public void lookBusy() {
        // Get a piece of stock
        String item = this.getStockItem();

        // Add it
        this.add(item);

        // Randomly remove or check item
        if(Math.random() > 0.5) this.remove(item);
        else this.check(item);
    }

    @Override
    public void run() {
        // Busy body running around adding stuff and what not
        // being that attendant that never wants to help you
        while(true) {
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            lookBusy();
        }
    }

    // Letters for the stock.
    public static String _az = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public int _count = 0; // How many stock has been generated
    public int _hits = 0; // How many times stock has been asked for
    public String[] _stock = new String[300]; // The high level stock store that attendants are asked about

    /**
     * Generate a simple name for an item.
     * @return
     */
    public String getStockItem() {
        if(_hits % 14 == 0 && _count < _stock.length)
            return (_stock[_count++] = String.format("%s%s-%d", _az.charAt(Main.rand(0, 26)), _az.charAt(Main.rand(0, 26)), Main.rand(0, 240)));
        else
            return _stock[Main.rand(0, _count)];
    }
}
