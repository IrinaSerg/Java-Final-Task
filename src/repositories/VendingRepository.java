package repositories;

import models.Item;
import models.Vending;

import java.util.ArrayList;
import java.util.HashMap;

public class VendingRepository {
    private ArrayList<Vending> vendings;

    public VendingRepository() {
        Item chocolate = new Item("Twix");
        Item water = new Item("Bon Aqua");

        HashMap<String, Integer> firstVendingItems = new HashMap<>();
        firstVendingItems.put(chocolate.getName(), 10);
        firstVendingItems.put(water.getName(), 5);

        HashMap<String, Integer> secondVendingItems = new HashMap<>();
        secondVendingItems.put(chocolate.getName(), 15);
        secondVendingItems.put(water.getName(), 10);

        HashMap<String, Integer> thirdVendingItems = new HashMap<>();
        thirdVendingItems.put(chocolate.getName(), 20);
        thirdVendingItems.put(water.getName(), 15);

        this.vendings = new ArrayList<>();
        this.vendings.add(new Vending(1, firstVendingItems));
        this.vendings.add(new Vending(2, secondVendingItems));
        this.vendings.add(new Vending(3, thirdVendingItems));
    }

    public ArrayList<Vending> getAllVendings() {
        return this.vendings;
    }
}
