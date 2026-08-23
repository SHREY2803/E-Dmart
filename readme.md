# 🛒 E-Dmart – Java Web E-Commerce Application

A full-stack **E-Commerce web application** built using Java Servlets, JSP, JDBC, MySQL, HTML, and CSS.

The project follows a layered architecture and provides separate customer and administrator workflows, including product browsing, cart management, checkout, payments, order tracking, and administration.

> **Project Status:** Core functionality is implemented and tested. The remaining major work is the final UI/branding conversion from the old **GameStore** theme to **E-Dmart**.

---

## ✨ Features

### 👤 Customer Features

- User registration and login
- Session-based authentication
- Role-based access control
- Product listing and product details
- Category-based product organization
- Add products to cart
- Remove products from cart
- Cart quantity management
- Automatic subtotal and total calculation
- Checkout flow
- Order placement
- Simulated payment flow
- Payment success/failure handling
- Order history
- Order item details
- Order status tracking
- Delivery fulfillment
- Customer profile/dashboard
- Purchased-product library

---

## 📦 Order Management

The application supports the following order lifecycle:

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

Orders contain:

- Order ID
- Customer information
- Products
- Quantity
- Price
- Total amount
- Order date
- Order status
- Fulfillment type
- Delivery information

---

## 💳 Payment

The project contains a simulated payment workflow.

Payment records support:

- Payment method
- Payment status
- Transaction reference
- Payment timestamp

Supported payment statuses:

- `PENDING`
- `SUCCESS`
- `FAILED`

> Payment processing is simulated for this academic/demo project and is not connected to a real payment gateway.

---

## 🛠️ Admin Features

The administrator can:

- Access the Admin Dashboard
- Add products
- Edit products
- Delete products
- Manage products
- Manage categories
- View registered users
- View customer orders
- View order items
- Update order status
- Manage product inventory
- Upload product images

---

## 🏗️ Project Architecture

The application follows a layered architecture:

```text
                    E-Dmart
                       │
        ┌──────────────┴──────────────┐
        │                             │
 Presentation Layer              Service Layer
      (JSP)                       (Servlets)
        │                             │
        └──────────────┬──────────────┘
                       │
                Business Layer
                (Business Logic)
                       │
                  DAO Layer
             ┌─────────┴─────────┐
             │                   │
        DAO Interfaces      DAO Implementations
             │                   │
             └─────────┬─────────┘
                       │
                 MySQL Database
```

### Main Java Packages

```text
src/main/java
├── business
├── dao
├── daoimpl
├── model
├── service
└── util
```

### Web Layer

```text
src/main/webapp
├── admin
├── assets
├── components
├── customer
└── JSP pages
```

---

## 🧩 Core Components

### Business Layer

The business layer contains components such as:

- `CartManager`
- `OrderManager`
- `ProductManager`
- `LoginValidator`
- `RegisterValidator`

### DAO Layer

Database access is handled through DAO interfaces and implementations for:

- Products
- Categories
- Cart
- Orders
- Users

### Model Layer

Main model classes include:

- `User`
- `Product`
- `Category`
- `Cart`
- `Order`
- `OrderItem`
- `Payment`

### Service Layer

Important customer-side servlets include:

- `LoginServlet`
- `RegisterServlet`
- `AddToCartServlet`
- `CartServlet`
- `RemoveFromCartServlet`
- `CheckoutServlet`
- `PlaceOrderServlet`
- `PaymentServlet`
- `MyOrdersServlet`
- `MyLibraryServlet`
- `ProfileServlet`

Important admin-side servlets include:

- `AdminProductsServlet`
- `AddAdminProductServlet`
- `EditAdminProductServlet`
- `UpdateAdminProductServlet`
- `DeleteAdminProductServlet`
- `AllUsersAdminServlet`
- `ViewAdminOrdersServlet`
- `UpdateOrderStatusServlet`

---

## 🗄️ Database

The application uses **MySQL** as the relational database.

The project database contains tables for:

```text
audit_logs
cart
categories
order_items
orders
payments
products
returns
stores
users
```

### Main Database Relationship

```text
User
 │
 ├── Cart
 │
 └── Orders
       │
       ├── Order Items
       │      │
       │      └── Product
       │
       └── Payment
```

The order placement process uses a database transaction so that:

- Stock reduction
- Order creation
- Order item creation
- Cart clearing

are handled together.

---

## 🛒 Customer Order Flow

```text
Browse Products
       ↓
Search / Filter Products
       ↓
Add Product to Cart
       ↓
View Cart
       ↓
Checkout
       ↓
Place Order
       ↓
Reduce Product Stock
       ↓
Create Order Items
       ↓
Clear Cart
       ↓
Payment
       ↓
Payment Success
       ↓
Order Confirmation
       ↓
Admin Updates Status
       ↓
Customer Tracks Order
       ↓
Delivered
```

---

## 🛍️ Shopping Cart

The cart system supports:

- Adding products
- Removing products
- Increasing product quantity
- Preventing quantity from exceeding available stock
- Calculating product subtotal
- Calculating total cart amount
- Clearing the cart after successful order creation

Example:

```text
Product Price × Quantity = Subtotal

All Subtotals = Total Order Amount
```

---

## 📦 Inventory Management

Product inventory is automatically updated during order placement.

For example:

```text
Available Stock = 10
Customer Orders = 3

Remaining Stock = 7
```

The application also prevents customers from adding more units to the cart than the available inventory.

---

## 🔐 Authentication & Authorization

The application uses session-based authentication and role-based access control.

### Supported Roles

```text
CUSTOMER
ADMIN
STAFF
```

Customer functionality and administrative functionality are separated according to the user's role.

Unauthorized users are redirected to an access-denied page.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Jakarta Servlets | Request handling |
| JSP | Server-side UI |
| JDBC | Database connectivity |
| MySQL | Relational database |
| Apache Tomcat | Application server |
| HTML | Page structure |
| CSS | User interface styling |
| Eclipse IDE | Development environment |

---

## ⚙️ Setup Instructions

### 1. Import the Project

Import the project into **Eclipse** as a Dynamic Web Project.

### 2. Configure Apache Tomcat

Configure an Apache Tomcat server compatible with the Jakarta Servlet version used by the project.

### 3. Configure MySQL

Create the required MySQL database and tables using the project's database SQL script.

### 4. Configure Database Credentials

Update the database configuration in:

```text
src/main/java/util/DBConnection.java
```

Configure:

```text
Database URL
Username
Password
```

### 5. Deploy the Application

Run the project using Apache Tomcat.

The application can then be accessed through the configured local context path.

> The development context path may still contain the older `GameStore` name. This will be addressed during the final E-Dmart UI/branding cleanup.

---

## 🎨 Final UI & Branding

The core backend and e-commerce functionality are implemented.

The remaining major UI task is to completely transform the old **GameStore** identity into the final **E-Dmart** identity.

### Current

```text
🎮 GameStore
```

### Final

```text
🛒 E-Dmart
```

### UI Changes Planned

- Replace `GameStore` branding with `E-Dmart`
- Update navbar branding
- Update footer branding
- Update page titles
- Update headings
- Replace game-specific terminology
- Update buttons and labels
- Update favicon
- Update logo/branding assets
- Improve overall visual consistency
- Remove remaining game-store references

### Important

The UI conversion should **not modify the already-working business logic**.

The following functionality should remain unchanged:

```text
Authentication
      ↓
Products
      ↓
Categories
      ↓
Cart
      ↓
Checkout
      ↓
Orders
      ↓
Payment
      ↓
Order Tracking
      ↓
Admin Management
```

Only the presentation and branding will be changed where necessary.

---

## 📸 Screenshots

Recommended screenshots for the final project documentation:

- Home / Product Listing
- Product Details
- Category Filtering
- Shopping Cart
- Checkout
- Payment
- Order Success
- My Orders
- Order Tracking
- My Library
- Admin Dashboard
- Product Management
- Category Management
- User Management
- Order Management

---

## 🚀 Project Status

### ✅ Completed

- [x] User Registration
- [x] User Login
- [x] Role-based Access Control
- [x] Product Listing
- [x] Product Details
- [x] Product Search
- [x] Category Filtering
- [x] Category Management
- [x] Product Management
- [x] Product Image Upload
- [x] Shopping Cart
- [x] Cart Quantity Management
- [x] Stock Limitation
- [x] Checkout
- [x] Order Creation
- [x] Inventory Reduction
- [x] Cart Clearing After Order
- [x] Payment Flow
- [x] Payment Records
- [x] Order History
- [x] Order Items
- [x] Order Status Updates
- [x] Customer Order Tracking
- [x] My Library / Purchased Products
- [x] Admin Order Management
- [x] Admin User Management

### 🎯 Remaining

- [ ] Complete E-Dmart UI/branding conversion
- [ ] Final visual consistency pass
- [ ] Replace remaining GameStore terminology
- [ ] Update final screenshots
- [ ] Final project review

---

## 👨‍💻 Author

**Shrey Kulkarni**

---

## 📌 Disclaimer

This project is an academic/demo e-commerce application built using Java web technologies and MySQL.

Payment processing is simulated and should not be used for real financial transactions.