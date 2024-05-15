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
