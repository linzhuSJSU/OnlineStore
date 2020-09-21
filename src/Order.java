package courseProject;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class Order implements Comparable<Order> {
    private int id;
    private final LocalDate orderDate;
    private int status;
    private final int quantity;
    private Shipping shipment;
    private final String productTitle;
    private final String userName;
    private String trackingNumber;


    public Order(int id,
                 final LocalDate orderDate,
                 int status,
                 final int quantity,
                 Shipping shipment,
                 final String productTitle,
                 final String userName,
                 String trackingNumber) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.quantity = quantity;
        this.shipment = shipment;
        this.productTitle = productTitle;
        this.userName = userName;
        this.trackingNumber = trackingNumber;
    }

    public int getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }

    public Shipping getShipment() {
        return shipment;
    }

    public String getProduct() {
        return productTitle;
    }

    public String getCustomer() {
        return userName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void shipOrder() {
        this.status = 1;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    public int compareTo(Order o2) {
        if(this.getStatus() == o2.getStatus()) {
            if(this.getOrderDate().compareTo(o2.getOrderDate()) == 0) {
                return Integer.compare(this.getShipment().getSpeedCode(), o2.getShipment().getSpeedCode());
            }
            return this.getOrderDate().compareTo(o2.getOrderDate());
        }
        return this.getStatus() == 1 ? -1 : 1;
    }

    @Override
    public int hashCode() {
        return 0;
        // return Objects.hash(orderDate, status, quantity, shipment, product, customer, trackingNumber);
    }

    @Override
    public String toString() {
        if(status == 0) {
            return  "{OrderId = " + id +
                    ", OrderDate = " + orderDate +
                    ", Status = Unshipped" +
                    ", Quantity = " + quantity +
                    ", Shipment = " + shipment +
                    ", Product = " + productTitle +
                    ", TrackingNumber = N/A" +
                    '}';
        } else {
            return  "{OrderId = " + id +
                    ", OrderDate = " + orderDate +
                    ", Status = Shipped" +
                    ", Quantity = " + quantity +
                    ", Shipment = " + shipment +
                    ", Product = " + productTitle +
                    ", TrackingNumber = " + trackingNumber +
                    '}';
        }

    }
    public enum Shipping {
        STANDARD (1), OVERNIGHT (3), RUSH (2);

        private static HashMap<Integer, Shipping> codeMap = new HashMap<>();
        static {
            for (Shipping s: Shipping.values()) {
                codeMap.put(s.speedCode, s);
            }
        }

        private final int speedCode;

        Shipping(int speedCode) {
            this.speedCode = speedCode;
        }

        public int getSpeedCode() {
            return this.speedCode;
        }

        public static Shipping ofCode(int speedCode) {
            return codeMap.get(speedCode);
        }
    }
}
