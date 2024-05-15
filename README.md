# iron-hack-lab-2

## Actividad 1
Identificar que el codigo cumpla con los principios SOLID


* Principio de responsabilidad única; no se esta cumpliendo ya que la funcion processOrder esta realizando multiples tareas, como verificar inventario, procesar pagos, actualiza estatus y notificar al cliente.

```javascript
processOrder(order)
{
    if (order.type == "standard") {
        verifyInventory(order);
        processStandardPayment(order);
    } else if (order.type == "express") {
        verifyInventory(order);
        processExpressPayment(order, "highPriority");
    }
    updateOrderStatus(order, "processed");
    notifyCustomer(order);
}
```

* Principio abierto/cerrado; 
  * no se esta cumpliendo ya que si se desea agregar un nuevo tipo de pago, se tendria que modificar el codigo existente.
  * El codigo no esta modularizado en clases e interfaces, ya que se esta realizando todo en una sola funcion.
* Principio de sustitución de Liskov;
  * Al no estar separado en clases e interfaces, no se puede aplicar este principio.
* Principio de segregación de la interfaz;
    * Al no estar separado en interfaces, no se puede aplicar este principio.
* Principio de inversión de dependencias;
    * Al no estar separado en clases e interfaces, no se puede aplicar este principio.
```javascript

    verifyInventory(order) 
    {
        // Checks inventory levels
        if (inventory < order.quantity) {
            throw new Error("Out of stock");
        }
    }
 
    processStandardPayment(order) 
    {
        // Handles standard payment processing
        if (paymentService.process(order.amount)) {
            return true;
        } else {
            throw new Error("Payment failed");
        }
    }
 
    processExpressPayment(order, priority) 
    {
        // Handles express payment processing
        if (expressPaymentService.process(order.amount, priority)) {
            return true;
        } else {
            throw new Error("Express payment failed");
        }
    }
 
    updateOrderStatus(order, status) 
    {
        // Updates the order status in the database
        database.updateOrderStatus(order.id, status);
    }
 
    notifyCustomer(order) 
    {
        // Sends an email notification to the customer
        emailService.sendEmail(order.customerEmail, "Your order has been processed.");
    }
```

## Actividad 2

Se creo la clase `SystemManager` que contiene el codigo refactorizado ya cumpliendo con los principios SOLID.


## Actividad 3

* Para cumplir con los principios SOLID se tuvo que separar en clases e interfaces, para poder aplicar los principios de sustitución de Liskov, segregación de la interfaz y inversión de dependencias como tambien el de una sola resposabilidad.
```java
class Order {}
```
```java
interface Payment {}
```
```java
interface Inventory {}
```
```java
interface Notification {}
```
```java
class StandardPayment implements Payment {}
```
```java
class ExpressPayment implements Payment {}
```
```java
class Tacos implements Inventory {}
```
```java
class Burritos implements Inventory {}
```
```java
class EmailNotification implements Notification {}
```
```java
class SMSNotification implements Notification {}
```

* Se creo la clase `Factory` para poder crear las instancias de las clases que se necesiten.
  * El principio que se estaria cumpliendo es responsabilidad unica y abierto/cerrado.
```java
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
```

* Y al final estarias creando la clase principal `SystemManager` que se encargara de procesar la orden.
```java
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
```








