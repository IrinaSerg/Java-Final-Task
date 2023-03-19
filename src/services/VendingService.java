package services;

import models.IncorrectCodeException;
import models.IncorrectVendingID;
import models.ReserveItemException;
import models.Vending;
import repositories.VendingRepository;

import java.util.ArrayList;
import java.util.Map;

public class VendingService {
    private VendingRepository repository;

    public VendingService(VendingRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Vending> getAllVendings() {
        return this.repository.getAllVendings();
    }

    public Map<String, Integer> getItemsForVendingID(Integer id) throws IncorrectVendingID {
        for (Vending vending : this.repository.getAllVendings()) {
            if (vending.getID().equals(id)) {
                return vending.getItems();
            }
        }

        throw new IncorrectVendingID();
    }

    public String createOrder(Integer vendingID, Map<String, Integer> items) throws ReserveItemException {
        for (Vending vending : this.repository.getAllVendings()) {
            if (vending.getID().equals(vendingID)) {
                try {
                    String code = vending.reserveItems(items);
                    return code;
                } catch (ReserveItemException err) {
                    throw err;
                }
            }
        }
        throw new ReserveItemException();
    }

    public Map<String, Integer> getOrderBy(Integer vendingID, String code) throws IncorrectCodeException {
        for (Vending vending : this.repository.getAllVendings()) {
            if (vending.getID().equals(vendingID)) {
                return vending.getItemsByCode(code);
            }
        }

        throw new IncorrectCodeException();
    }
}
