package models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

// Из всех функций модели вынести дублирование кода в отдельные функции
// Пользовательские ошибки постараться объединить по смыслу
public class Vending {
    private Integer id;
    private Map<String, Integer> items;
    private Map<String, Integer> reservedItems;
    private Map<String, Map<String, Integer>> orders;

    public Vending(Integer id, Map<String, Integer> items) {
        this.id = id;
        this.items = items;
        this.reservedItems = new HashMap<String, Integer>();
        for(String item: items.keySet()) {
            this.reservedItems.put(item, 0);
        }
        this.orders = new HashMap<String, Map<String, Integer>>();
    }

    public Integer getID() {
        return this.id;
    }

    public Map<String, Integer> getItems() {
        return this.items;
    }

    public String reserveItems(Map<String, Integer> items) throws ReserveItemException {
        Map<String, Integer> freeItems = new HashMap<String, Integer>();
        for ( String key : this.items.keySet() ) {
            freeItems.put(key, 0);
        }

        for ( String key : this.items.keySet() ) {
            freeItems.put(key, this.items.get(key).intValue() - this.reservedItems.get(key).intValue());
        }

        for (String key: items.keySet()) {
            if (freeItems.get(key).intValue() - items.get(key).intValue() < 0) {
                throw new ReserveItemException();
            }
        }

        for (String key: items.keySet()) {
           this.reservedItems.put(key, this.reservedItems.get(key).intValue() + items.get(key).intValue());
        }

        Integer code = ThreadLocalRandom.current().nextInt();

        this.orders.put(code.toString(), items);
        return code.toString();
    }

    public Map<String, Integer> getItemsByCode(String code) throws IncorrectCodeException {
        if (!orders.keySet().contains(code)) {
            throw new IncorrectCodeException();
        }

        Map<String, Integer> order = this.orders.get(code);
        for (String key: order.keySet()) {
            items.put(key, items.get(key).intValue() - order.get(key).intValue());
            reservedItems.put(key, reservedItems.get(key).intValue() - order.get(key).intValue());
        }

        this.orders.remove(code);
        return order;
    }
}

