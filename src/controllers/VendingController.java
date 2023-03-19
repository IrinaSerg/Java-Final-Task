package controllers;

import models.Vending;
import services.VendingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingController {
    private VendingService service;
    private Boolean isCanceled;

    private Scanner scanner;

    public VendingController(VendingService service) {
        this.service = service;
        this.isCanceled = false;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Добро пожаловать в Vending Market Service!");
        while (!this.isCanceled) {
            printOperations();
            String operation = this.scanner.nextLine();
            if (operation.equals("1")) {
                getVendings();
            } else if (operation.equals("2")) {
                System.out.println("Выберите вендинг, который хотите посмотреть:");
                getVendings();
                String id = this.scanner.nextLine();
                getItemsForVending(Integer.parseInt(id));
            } else if (operation.equals("3")) {
                System.out.println("Выберите вендинг, в котором хотите сделать заказ:");
                getVendings();
                String id = this.scanner.nextLine();
                getItemsForVending(Integer.parseInt(id));
                String itemsRaw = this.scanner.nextLine();

                HashMap<String, Integer> items = new HashMap<>();
                String[] itemsComponents = itemsRaw.split(",");
                for(String str: itemsComponents) {
                    String[] parts = str.split(":");
                    items.put(parts[0], Integer.parseInt(parts[1].replaceAll("\\s", "")));
                }
                createOrder(Integer.parseInt(id), items);
            } else if (operation.equals("4")) {
                System.out.println("Выберите вендинг, в котором хотите получить заказ:");
                getVendings();
                String id = this.scanner.nextLine();
                System.out.println("Введите код получения:");
                String code = this.scanner.nextLine();
                getOrder(Integer.parseInt(id), code);
            } else if (operation.equalsIgnoreCase("q")) {
                this.isCanceled = true;
            } else {
                System.out.println("Выбрана недоступная операция, повторите выбор");
            }

        }
        System.out.println("Заканчиваю выполнение...");
    }

    private void printOperations() {
        System.out.println("Выберите операцию:");
        System.out.println("===================");
        System.out.println("1 - Посмотреть список автоматов");
        System.out.println("2 - Посмотреть товары в конкретном автомате");
        System.out.println("3 - Сделать заказ в конкретном автомате");
        System.out.println("4 - Получить заказ по коду из конкретного автомата");
        System.out.println("===================");
    }

    private void getVendings() {
        for (Vending vending: this.service.getAllVendings()) {
            System.out.println("Vending #" + vending.getID());
        }
    }

    private void getItemsForVending(Integer id) {
        for (Vending vending: this.service.getAllVendings()) {
            if (vending.getID().equals(id)) {
                for (String item: vending.getItems().keySet()) {
                    System.out.println("Товар " + item + " в количестве: " + vending.getItems().get(item).toString());
                }
                return;
            }
        }

        System.out.println("Введен не коректный номер вендинга!");
    }

    private void createOrder(Integer id, HashMap<String, Integer> items) {
        String code = this.service.createOrder(id, items);
        System.out.println("Ваш код для получения заказа: " + code);
    }

    private void getOrder(Integer id, String code) {
        Map<String, Integer> order = this.service.getOrderBy(id, code);
        System.out.println("Ваш заказ получен!");
        for(String item: order.keySet()) {
            System.out.println("Товар " + item + " в количестве " + order.get(item).toString());
        }
    }
}
