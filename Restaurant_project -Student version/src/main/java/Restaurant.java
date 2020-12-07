import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();
    private List<Item> orders = new ArrayList<>();
    private int orderTotal = 0;

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        return !getCurrentTime().isBefore(openingTime) && !getCurrentTime().isAfter(closingTime);
    }

    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public List<Item> getMenu() {
        return this.menu;
    }

    private Item findItemByName(String itemName) {
        for (Item item : menu) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name, price);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }

    public void displayDetails() {
        System.out.println("Restaurant:" + name + "\n"
                + "Location:" + location + "\n"
                + "Opening time:" + openingTime + "\n"
                + "Closing time:" + closingTime + "\n"
                + "Menu:" + "\n" + getMenu());

    }

    public String getName() {
        return name;
    }

    // Adds an item to order and logs out restaurant, menu & updated order details with updated total.
    public int addItemToOrder(String itemName) throws itemNotFoundException {
        displayDetails();

        Item orderedItem = findItemByName(itemName);
        if (orderedItem == null) {
            displayOrderDetails();
            throw new itemNotFoundException(itemName);
        }

        orders.add(orderedItem);
        orderTotal += Math.max(orderedItem.getPrice(), 0);

        System.out.println("\n" + itemName + " - Added to Order");

        displayOrderDetails();

        return orderTotal;
    }

    // Removes an item from order and logs out restaurant, menu & updated order details with updated total.
    public int removeItemFromOrder(String itemName) throws itemNotFoundException {
        displayDetails();

        Item removedOrderItem = findItemByName(itemName);
        if ((removedOrderItem == null) || !orders.remove(removedOrderItem)) {
            displayOrderDetails();
            throw new itemNotFoundException(itemName);
        }

        orderTotal -= Math.max(removedOrderItem.getPrice(), 0);

        System.out.println("\n" + itemName + " - Removed From Order");

        displayOrderDetails();

        return orderTotal;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public List<Item> getOrders() {
        return this.orders;
    }

    public void displayOrderDetails() {
        System.out.println("Ordered Items:" + "\n" + getOrders() + "\n"
                + "Total Amount: " + getOrderTotal() + "\n");

    }
}
