# NovaBank — Online Banking System

![Java](https://img.shields.io/badge/Java-17%2B-007396?logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![Build](https://img.shields.io/badge/Build-Ant-A81C7D?logo=apacheant&logoColor=white)
![Course](https://img.shields.io/badge/Course-25CSCI04C-brightgreen)
![Project](https://img.shields.io/badge/Team-3-orange)
![Phase](https://img.shields.io/badge/Phase-2%20of%202-yellow)
![University](https://img.shields.io/badge/University-BUE-red)
![License](https://img.shields.io/badge/License-Educational-lightgrey)

A Java Swing–based Online Banking System developed as part of the **Programming in Java (25CSCI04C)** course at the British University in Egypt (BUE). The system provides a desktop GUI for managing bank accounts, transactions, loans, and insurance, with role-based access for Admins and Clients, persistent storage in CSV files, and comprehensive exception handling.

## 📋 Project Info

| Field            | Details                                  |
|------------------|------------------------------------------|
| Course           | Programming in Java — 25CSCI04C          |
| University       | British University in Egypt              |
| Phase            | Phase 2 of 2 (GUI, Files, Exceptions)    |
| Assessment Weight| 25% of total course mark                 |
| Deadline         | Week 13                                  |

## 👥 Team Members (Group 3)

| ID     | Name             | Email                        | Group |
|--------|------------------|------------------------------|-------|
| 257678 | Mahmoud Samir    | mahmoud257678@bue.edu.eg     | A-14  |
| 250498 | Youssef Hassan   | youssef250498@bue.edu.eg     | A-12  |
| 257511 | Ibrahim Mostafa  | ibrahim257511@bue.edu.eg     | A-8   |
| 254588 | Zeina Alaaeldin  | zeina254588@bue.edu.eg       | A-14  |
| 257156 | Zainab Sabit     | zainab257156@bue.edu.eg      | A-14  |
| 252304 | Malak Waleed     | malak252304@bue.edu.eg       | A-7   |

## ✨ Phase 2 Highlights

### Graphical User Interface (Swing)
- **Login & Registration** screens with show-password toggle and lockout after 3 failed attempts
- **Role-based dashboards** — Admins and Clients see only the operations available to them
- **Dedicated panels** for Deposit/Withdraw, Transfer, Transaction History, and Loan & Insurance
- Consistent NovaBank branding (deep navy + accent yellow), built with NetBeans `.form` files

### Authentication & Authorisation
- Centralised `AuthService` (singleton) holds the user list and current session
- Limits failed login attempts to **three** before locking the login button
- Authenticated users are routed to the correct interface based on their role (`Admin` → AdminDashboard, `Client` → ClientDashboard)
- Explicit logout from any dashboard returns the user to the login screen

### File Persistence (CSV)
- `FileManager` saves and loads users, accounts, and transactions to/from CSV files in `data/`
- Three files are maintained:
  - `bank_users.csv` — userId, name, email, password, role, phone
  - `bank_accounts.csv` — accountNumber, type, balance, status, ownerId, extra
  - `bank_transactions.csv` — txId, amount, type, accountNumber, status, date
- Data is reloaded automatically on startup so previous accounts and balances persist between runs

### Exception Handling
A custom exception hierarchy rooted at `BankException` covers every realistic failure path:
- `InvalidLoginException`, `UserAlreadyExistsException` — auth errors
- `InvalidAmountException`, `InsufficientFundsException`, `TransferFailedException` — transaction errors
- `AccountNotFoundException` — lookup errors
- `FileAccessException` — I/O errors
- `TransactionException` — generic transaction wrapper
Every GUI handler catches these and surfaces a friendly `JOptionPane` message rather than a stack trace.

## 🏗️ Project Structure

```
OnlineBankingSystem/
├── data/                              # Persistent CSV storage
│   ├── bank_users.csv
│   ├── bank_accounts.csv
│   └── bank_transactions.csv
├── src/banking/
│   ├── Main.java                      # Application entry point
│   ├── api/                           # Capability interfaces
│   │   ├── Insurable.java
│   │   ├── LoanEligible.java
│   │   └── Transferable.java
│   ├── common/                        # Enums
│   │   ├── AccountType.java
│   │   ├── AccountStatus.java
│   │   ├── ClientType.java
│   │   └── TransactionType.java
│   ├── core/
│   │   ├── account/                   # Account hierarchy
│   │   │   ├── Account.java           (abstract)
│   │   │   ├── SavingsAccount.java
│   │   │   ├── CurrentAccount.java
│   │   │   ├── BusinessAccount.java
│   │   │   └── PremiumAccount.java
│   │   ├── transaction/               # Transaction hierarchy
│   │   │   ├── Transaction.java       (abstract)
│   │   │   ├── Deposit.java
│   │   │   ├── Withdrawal.java
│   │   │   ├── Transfer.java
│   │   │   └── TransactionHistory.java
│   │   └── user/                      # User hierarchy
│   │       ├── User.java              (abstract)
│   │       ├── Admin.java
│   │       ├── Client.java            (abstract)
│   │       ├── StandardClient.java
│   │       ├── PremiumClient.java
│   │       └── FirstClassClient.java
│   ├── exception/                     # Custom exceptions
│   ├── gui/                           # Swing frames and panels
│   │   ├── LoginFrame.java
│   │   ├── RegisterFrame.java
│   │   ├── AdminDashboardFrame.java
│   │   ├── ClientDashboardFrame.java
│   │   ├── DepositWithdrawPanel.java
│   │   ├── TransferPanel.java
│   │   ├── HistoryPanel.java
│   │   └── loanInsuranceFrame.java
│   ├── persistence/
│   │   └── FileManager.java
│   ├── presentation/
│   │   └── TransactionView.java
│   └── service/                       # Application services
│       ├── AuthService.java
│       └── TransactionManager.java
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) **17 or higher** (project is configured for JDK 25)
- Apache Ant (for command-line build), or NetBeans / IntelliJ IDEA

### Run from NetBeans (recommended)
1. Open the `OnlineBankingSystem` folder as a NetBeans project
2. Right-click the project → **Run** (main class is `banking.Main`)

### Run from the command line
```bash
# from the OnlineBankingSystem/ directory
ant clean jar
java -jar dist/OnlineBankingSystem.jar
```

Or compile manually:
```bash
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out banking.Main
```

## 🔐 Demo Accounts

The login screen lists ready-to-use accounts loaded from `data/bank_users.csv`:

| Role     | Email                  | Password   |
|----------|------------------------|------------|
| Admin    | admin@novabank.com     | admin123   |
| Client   | youssef@gmail.com      | youssef123 |
| Client   | omar.test@novabank.com | omar123    |
| Client   | sara.test@novabank.com | sara123    |

> New users registered through the Sign Up screen are saved as `StandardClient`s, but an Admin must create a bank account for them before they can log in to the dashboard.

## 📦 Core Components

### Account Hierarchy
- `Account` (abstract) — accountNumber, balance, owner, status, transactionHistory
  - `SavingsAccount` — interest-bearing
  - `CurrentAccount` — supports overdraft up to a configurable limit
  - `BusinessAccount` — tied to a business name
  - `PremiumAccount` — extended withdrawal limit via insurance buffer

### User Hierarchy
- `User` (abstract) — userId, name, email, password, login/logout
  - `Admin` — manages users and accounts
  - `Client` (abstract) — owns accounts, performs transactions
    - `StandardClient` — basic tier with withdrawal limit
    - `PremiumClient` — implements `LoanEligible` + `Insurable`
    - `FirstClassClient` — top tier with priority level + higher loan/insurance limits

### Transaction Types
- `Deposit`, `Withdrawal`, `Transfer` — all extend `Transaction` (abstract) and implement `execute()`
- Each transaction has a UUID, timestamp, amount, type, and status (`PENDING` / `SUCCESS`)

### Capability Interfaces
- `Insurable` — `getInsurance()`, `claimInsurance()`
- `LoanEligible` — `applyForLoan(amount)`, `getLoanLimit()`
- `Transferable` — `transfer(destination, amount)`, `getBalance()`

## 🖥️ Screens at a Glance

| Screen                    | Who can use it | What it does                                                  |
|---------------------------|----------------|---------------------------------------------------------------|
| `LoginFrame`              | Everyone       | Email + password login with attempt limiter                   |
| `RegisterFrame`           | Everyone       | Create a new Standard client profile                          |
| `AdminDashboardFrame`     | Admin          | View users / accounts, remove user, create client + account   |
| `ClientDashboardFrame`    | Client         | Hub showing client info and links to all client operations    |
| `DepositWithdrawPanel`    | Client         | Pick an account and deposit or withdraw funds                 |
| `TransferPanel`           | Client         | Send money between any two accounts in the system             |
| `HistoryPanel`            | Client         | View transactions for the client's accounts                   |
| `loanInsuranceFrame`      | Premium / FC   | Apply for a loan or claim insurance                           |

## 🛠️ Technologies Used

- **Language**: Java
- **GUI**: Swing (with NetBeans `.form` designer)
- **Architecture**: Object-Oriented Design with clear package separation (api / common / core / exception / gui / persistence / service)
- **Design patterns**: Singleton (`AuthService`), Template Method (`Transaction.execute`), inheritance + polymorphism throughout
- **Persistence**: CSV files via `java.io` (`PrintWriter`, `Scanner`)
- **Build**: Apache Ant (NetBeans-generated)

## 🧪 Work Division (Phase 2)

| Member          | Primary responsibility                                              |
|-----------------|---------------------------------------------------------------------|
| Mahmoud Samir   | User hierarchy, Admin dashboard, project coordination               |
| Youssef Hassan  | Account classes, `AuthService`, login flow                          |
| Ibrahim Mostafa | Transaction classes, transaction history                            |
| Zeina Alaaeldin | Capability interfaces, `TransactionManager`, exception design       |
| Zainab Sabit    | `FileManager` persistence, exception classes                        |
| Malak Waleed    | `PremiumAccount`, `TransactionView`, exception classes              |

## 📄 License

This project is developed for educational purposes as part of the Programming in Java course at BUE.

## 📞 Contact

For inquiries, please contact any team member via their BUE email address listed above.

---

*Developed by Team 3 · British University in Egypt · 2026*
