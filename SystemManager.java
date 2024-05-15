
class Order {
    String type;
    String typeProduct;
    String status;
}

interface Payment {
    void processPayment(Order order);
}
interface Inventory{
    void verifyInventory(Order order);
    void updateOrderStatus(Order order, String status);
}
interface Notification {
    void notifyCustomer(Order order);
}

class StandardPayment implements Payment {
    public void processPayment(Order order) {
        System.out.println("Standard payment processed");
    }
}
class ExpressPayment implements Payment {
    public void processPayment(Order order) {
        System.out.println("Express payment processed");
    }
}

class Tacos implements Inventory {
    public void verifyInventory(Order order) {
        System.out.println("Inventory verified");
    }
    @Override
    public void updateOrderStatus(Order order, String status) {
        System.out.println("Order status updated");
    }

}
class Burritos implements Inventory {
    public void verifyInventory(Order order) {
        System.out.println("Inventory verified");
    }
    @Override
    public void updateOrderStatus(Order order, String status) {
        System.out.println("Order status updated");
    }
}
class EmailNotification implements Notification {
    public void notifyCustomer(Order order) {
        System.out.println("Email notification sent");
    }
}
class SMSNotification implements Notification {

    public void notifyCustomer(Order order) {
        System.out.println("SMS notification sent");
    }

}
class Factory {
    public Payment getPayment(String paymentType) {
        if (paymentType.equalsIgnoreCase("standard")) {
            return new StandardPayment();
        } else if (paymentType.equalsIgnoreCase("express")) {
            return new ExpressPayment();
        }
        return null;
    }
    public Inventory getInventory(String inventoryType) {
        if (inventoryType.equalsIgnoreCase("tacos")) {
            return new Tacos();
        } else if (inventoryType.equalsIgnoreCase("burritos")) {
            return new Burritos();
        }
        return null;
    }
    public Notification getNotification(String notificationType) {
        if (notificationType.equalsIgnoreCase("email")) {
            return new EmailNotification();
        } else if (notificationType.equalsIgnoreCase("sms")) {
            return new SMSNotification();
        }
        return null;
    }
}
class SystemManager {
    public void processOrder(Order order) {
        Factory factory = new Factory();
        Payment payment = factory.getPayment(order.type);
        Inventory inventory = factory.getInventory(order.typeProduct);
        Notification notification = factory.getNotification("email");
        inventory.verifyInventory(order);
        payment.processPayment(order);
        inventory.updateOrderStatus(order, "processed");
        notification.notifyCustomer(order);
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.type = "standard";
        order.typeProduct = "tacos";
        order.status = "pending";
        SystemManager systemManager = new SystemManager();
        systemManager.processOrder(order);
    }
}


