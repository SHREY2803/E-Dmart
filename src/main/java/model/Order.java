package model;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class Order {

    private int id;
    private int userId;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;

    // ==========================================
    // Fulfillment details
    // ==========================================

    private String fulfillmentType;
    private String deliveryAddress;
    private Integer pickupStoreId;
    private Date pickupDate;
    private String pickupSlot;

    // ==========================================
    // For display purposes
    // ==========================================

    private String userName;
    private List<OrderItem> items;


    // ==========================================
    // ID
    // ==========================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // ==========================================
    // User ID
    // ==========================================

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    // ==========================================
    // Order Date
    // ==========================================

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }


    // ==========================================
    // Total Amount
    // ==========================================

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    // ==========================================
    // Status
    // ==========================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // ==========================================
    // Fulfillment Type
    // DELIVERY / PICKUP
    // ==========================================

    public String getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(String fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }


    // ==========================================
    // Delivery Address
    // ==========================================

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }


    // ==========================================
    // Pickup Store
    // ==========================================

    public Integer getPickupStoreId() {
        return pickupStoreId;
    }

    public void setPickupStoreId(Integer pickupStoreId) {
        this.pickupStoreId = pickupStoreId;
    }


    // ==========================================
    // Pickup Date
    // ==========================================

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }


    // ==========================================
    // Pickup Slot
    // ==========================================

    public String getPickupSlot() {
        return pickupSlot;
    }

    public void setPickupSlot(String pickupSlot) {
        this.pickupSlot = pickupSlot;
    }


    // ==========================================
    // User Name
    // ==========================================

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    // ==========================================
    // Order Items
    // ==========================================

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

}