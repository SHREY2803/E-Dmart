# 🛒 E-Dmart

### A Java-Based Online Grocery Shopping Platform

E-Dmart is a full-stack **online grocery shopping web application** developed using **Java, JSP, Servlets, JDBC, MySQL, HTML, and CSS**.

The application provides a complete e-commerce experience where customers can browse grocery products, search and filter products, manage their shopping cart, place orders, make simulated payments, and track their orders.

It also provides a dedicated **Admin Panel** for managing products, categories, users, inventory, and customer orders.

---

## ✨ Key Features

### 🛍️ Customer Shopping Experience

- User registration and login
- Product browsing
- Product details
- Product search
- Category-based filtering
- Shopping cart
- Add/remove products from cart
- Dynamic cart quantity management
- Automatic subtotal and total calculation
- Stock availability validation
- Checkout
- Order placement
- Payment processing
- Order history
- Order tracking
- Purchased products section
- Customer profile

### 📦 Smart Inventory Management

E-Dmart maintains product inventory throughout the shopping and ordering process.

The application:

- Displays available product quantity
- Prevents customers from exceeding available stock
- Validates stock during checkout
- Automatically reduces inventory after an order
- Prevents orders when sufficient stock is unavailable

For example:

```text
Available Stock : 5
Customer Orders : 3
--------------------
Remaining Stock : 2
```

This ensures that the inventory shown to customers remains consistent with the database.

---

## 🛒 Shopping Cart

The cart system provides a complete shopping experience.

Customers can:

- Add products
- Increase/decrease quantity
- Remove products
- View individual product prices
- View product subtotals
- View the complete cart total

The application also prevents a customer from adding more products than the available inventory.

---

## 💳 Payment System

E-Dmart contains a **simulated payment system** integrated with the order workflow.

The payment module records:

- Order ID
- Payment method
- Payment status
- Transaction reference
- Payment timestamp

Supported payment states include:

```text
PENDING
SUCCESS
FAILED
```

A successful payment is associated with the corresponding order and stored in the database.

> The payment system is designed for academic/demo purposes and does not connect to a real payment gateway.

---

## 📦 Order Management

E-Dmart provides complete order management from checkout to delivery.

### Customer Order Flow

```text
Browse Products
       ↓
Add to Cart
       ↓
Checkout
       ↓
Place Order
       ↓
Stock Validation
       ↓
Stock Reduction
       ↓
Order Creation
       ↓
Payment
       ↓
Order Confirmation
       ↓
Order Tracking
       ↓
Delivery
```

### Order Status Tracking

Orders move through different stages:

```text
PLACED
   ↓
CONFIRMED
   ↓
PREPARING
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

Customers can see the current status of their order through a visual order-progress tracker.

For example:

```text
✓ Placed
    ↓
✓ Confirmed
    ↓
● Preparing
    ↓
○ Out for Delivery
    ↓
○ Delivered
```

This gives customers a clear view of their order's current progress.

---

## 👨‍💼 Admin Panel

E-Dmart includes a dedicated administrator dashboard.

### Admin capabilities include:

#### 📦 Product Management

- Add new products
- Edit products
- Delete products
- Update product prices
- Update product quantities
- Upload product images
- Activate/deactivate products

#### 🗂️ Category Management

Administrators can manage product categories so that products can be organized and filtered efficiently.

#### 👥 User Management

Administrators can view registered users and their account information.

#### 📋 Order Management

Administrators can:

- View customer orders
- View order details
- View ordered products
- View quantities
- View order totals
- View customer information
- Update order status

This allows the administrator to control the complete order lifecycle.

---

## 🔐 Authentication & Authorization

The application uses **session-based authentication** and **role-based access control**.

### Supported Roles

```text
CUSTOMER
ADMIN
STAFF
```

Different parts of the application are protected according to the user's role.

For example:

```text
Customer
   ↓
Customer Dashboard
Cart
Orders
Profile

Admin
   ↓
Admin Dashboard
Products
Categories
Users
Orders
```

Unauthorized users are prevented from accessing restricted administrative functionality.

---

## 🌟 Project Highlights

One of the main strengths of E-Dmart is that it is not simply a CRUD application.

The project combines multiple real-world e-commerce concepts into one application.

### 🔹 Transaction-Based Order Processing

Order placement is handled using a database transaction.

The following operations are coordinated as one logical transaction:

```text
Validate Cart
      ↓
Validate Stock
      ↓
Reduce Stock
      ↓
Create Order
      ↓
Create Order Items
      ↓
Clear Cart
      ↓
Commit Transaction
```

If an operation fails, the transaction can be rolled back to prevent inconsistent data.

---

### 🔹 Inventory-Aware Cart

The cart is connected to actual product inventory.

Customers cannot continuously increase the cart quantity beyond the available stock.

This prevents situations such as:

```text
Database Stock = 2

Customer Cart = 5  ❌
```

---

### 🔹 Real Order Lifecycle

Orders are not simply inserted into the database and forgotten.

The administrator can update the order status, and customers can see the updated status through their order-tracking interface.

```text
Admin
  ↓
Update Order Status
  ↓
Database
  ↓
Customer
  ↓
Updated Order Tracker
```

---

### 🔹 Integrated Payment & Orders

Payments are connected to orders through the database.

A successful payment is associated with the corresponding order using the order ID and stored with a transaction reference.

This creates a realistic relationship between:

```text
Order
  ↕
Payment
```

---

### 🔹 Layered Architecture

The application separates responsibilities between:

```text
JSP
 ↓
Servlet
 ↓
Business Logic
 ↓
DAO
 ↓
MySQL
```

This makes the application easier to maintain, debug, and extend.

---

# 🏗️ System Architecture

E-Dmart follows a layered Java web application architecture.

```text
┌─────────────────────────────┐
│       Presentation          │
│         JSP / HTML / CSS    │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│          Service            │
│        Java Servlets        │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│          Business           │
│       Business Logic        │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│            DAO              │
│    Database Access Layer    │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│           MySQL             │
│          Database           │
└─────────────────────────────┘
```

---

# 🗄️ Database Design

The application uses **MySQL** as its relational database.

Major entities include:

```text
Users
   │
   ├──────── Cart
   │
   └──────── Orders
                 │
                 ├──── Order Items ──── Products
                 │
                 └──── Payments
```

The database contains tables for major application modules such as:

- Users
- Products
- Categories
- Cart
- Orders
- Order Items
- Payments
- Stores
- Audit Logs

---

# 🛠️ Technology Stack

| Technology | Usage |
|---|---|
| **Java** | Backend development |
| **Jakarta Servlets** | Request handling |
| **JSP** | Dynamic web pages |
| **JDBC** | Database connectivity |
| **MySQL** | Database management |
| **HTML5** | Page structure |
| **CSS3** | UI styling |
| **Apache Tomcat** | Application server |
| **Eclipse IDE** | Development environment |

---

# 📁 Project Structure

```text
E-Dmart
│
├── src/main/java
│   │
│   ├── business
│   │   ├── CartManager
│   │   └── OrderManager
│   │
│   ├── dao
│   │   ├── CartDAO
│   │   ├── CategoryDAO
│   │   ├── OrderDAO
│   │   ├── ProductDAO
│   │   └── UserDAO
│   │
│   ├── daoimpl
│   │   └── DAO Implementations
│   │
│   ├── model
│   │   ├── User
│   │   ├── Product
│   │   ├── Category
│   │   ├── Cart
│   │   ├── Order
│   │   ├── OrderItem
│   │   └── Payment
│   │
│   ├── service
│   │   └── Customer Servlets
│   │
│   └── util
│       └── Database Utilities
│
├── src/main/webapp
│   │
│   ├── admin
│   │   ├── Admin Dashboard
│   │   ├── Product Management
│   │   └── Order Management
│   │
│   ├── customer
│   │   ├── Orders
│   │   ├── Profile
│   │   └── Purchased Products
│   │
│   ├── components
│   │   ├── Navbar
│   │   └── Footer
│   │
│   ├── assets
│   │   ├── css
│   │   └── images
│   │
│   └── JSP Pages
│
└── README.md
```

---

# ⚙️ Installation & Setup

## 1. Clone / Import the Project

Import the project into **Eclipse IDE** as a Dynamic Web Project.

## 2. Configure Apache Tomcat

Add the project to an Apache Tomcat server.

## 3. Configure MySQL

Create the required database and tables using the provided SQL/database setup.

## 4. Configure Database Connection

Open:

```text
src/main/java/util/DBConnection.java
```

Update the database configuration:

```text
Database URL
Username
Password
```

## 5. Start the Application

Run the application using Apache Tomcat.

Open the application through the configured local server URL.



---

# 🎯 Application Workflow

### Customer

```text
Register / Login
       ↓
Browse Products
       ↓
Search / Filter
       ↓
Add to Cart
       ↓
Checkout
       ↓
Place Order
       ↓
Payment
       ↓
Order Confirmation
       ↓
Track Order
       ↓
Receive Order
```

### Administrator

```text
Admin Login
     ↓
Admin Dashboard
     ↓
┌───────────────┬───────────────┬───────────────┐
│   Products    │     Users     │    Orders     │
└───────────────┴───────────────┴───────────────┘
       ↓                ↓                ↓
 Manage Products    View Users      Update Status
```

---

# 🔮 Future Scope

The architecture of E-Dmart can be extended with additional e-commerce capabilities such as:

- Real payment gateway integration
- Product reviews and ratings
- Wishlist
- Coupons and promotional offers
- Advanced product recommendations
- Email/SMS order notifications
- Delivery partner integration
- Advanced analytics and sales reports
- Cloud deployment

---

# 👨‍💻 Author

**Shrey Kulkarni**

---

## 📌 Disclaimer

E-Dmart is an academic/demo e-commerce application developed using Java web technologies and MySQL.

The payment functionality is simulated and is intended for demonstration purposes only.