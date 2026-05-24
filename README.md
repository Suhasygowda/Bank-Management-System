# 🏦 Mirai Bank - Management System

A professional, production-ready Console-based Bank Management System built with **Java 21**, **JDBC**, and **MySQL**. This application features a multi-layered architecture (DAO, Service, Model) and an enhanced UI for a seamless user experience.

---

## ✨ Features

- **User-Friendly Console UI**: Professional headers, color-coded status messages, and smooth transitions.
- **Secure Account Management**: Create accounts with PIN protection.
- **Real-time Transactions**:
  - 💰 **Deposit**: Instantly update your balance.
  - 💸 **Withdraw**: Securely withdraw funds with balance validation.
  - 🔄 **Transfer**: Move money between accounts with dual-ledger transaction logging.
- **Detailed History**: View a formatted table of all past transactions (Deposits, Withdrawals, and Transfers).
- **Data Persistence**: Robust MySQL integration ensures your data is never lost.

---

## 🛠️ Technology Stack

- **Language**: Java 21
- **Database**: MySQL 8.0+
- **Connectivity**: JDBC (MySQL Connector/J)
- **Build Tool**: Maven
- **UI Architecture**: ANSI Escape Codes for CLI Enhancement

---

## 🏗️ Project Architecture

The project follows a clean, modular design to ensure scalability and maintainability:

- **`in.mirai.bms.model`**: POJO classes representing entities like `Account` and `Transaction`.
- **`in.mirai.bms.dao`**: Data Access Object interfaces defining database operations.
- **`in.mirai.bms.daoimpl`**: Concrete implementations of DAO interfaces using JDBC.
- **`in.mirai.bms.service`**: Business logic layer that connects the UI to the Data layer.
- **`in.mirai.bms.util`**: Utility classes for `DBConnection` and the new `ConsoleUI` styling.
- **`Main.java`**: The entry point managing the application lifecycle and menu flow.

---

## 🚀 Getting Started

### 1. Prerequisites
- **JDK 21** or higher.
- **MySQL Server** installed and running.
- **Maven** for dependency management.

### 2. Database Setup
1. Create a database named `bms`:
   ```sql
   CREATE DATABASE bms;
   USE bms;
   ```
2. Create the necessary tables:
   ```sql
   CREATE TABLE accounts(
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    holder_name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL,
    pin INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

   CREATE TABLE transactions (
       transaction_id VARCHAR(50) PRIMARY KEY,
       account_id INT,
       type VARCHAR(20),
       amount INT,
       transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (account_id) REFERENCES accounts(account_id)
   );
   ```

### 3. Configuration
Open `src/main/java/in/mirai/bms/util/DBConnection.java` and update your database credentials:
```java
private static final String URL = "jdbc:mysql://localhost:3306/bms";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### 4. Running the Application
Using Maven:
```bash
mvn compile exec:java -Dexec.mainClass="in.mirai.bms.Main"
```

---

## 📸 Interface Preview

The application uses a polished terminal interface:
- **Headers**: Styled with double-line borders (`╔═══╗`).
- **Success/Error**: Marked with `✔` (Green) and `✘` (Red).
- **Loading States**: `⏳ Processing...` animations for a "live" feel.
- **Tables**: Perfectly aligned transaction history with color-coded amounts.

---

## 📜 License
This project is open-source and available under the MIT License.

---
*Developed by Mirai Team*
