# Online Banking System
A comprehensive Java-based Online Banking System developed as part of the Programming in Java course (25CSCI04C) at the British University in Egypt (BUE). This system provides a robust platform for managing bank accounts, transactions, and user operations with support for multiple account types and client tiers.
## 📋 Project Info
| Field            | Details                         |
|------------------|---------------------------------|
| Course           | Programming in Java — 25CSCI04C |
| University       | British University in Egypt     |
| Phase            | Phase 1                         |
| Deadline         | 19 March 2026                   |
## 👥 Team Members
| ID     | Name             | Email                        | Group |
|--------|------------------|------------------------------|-------|
| 257678 | Mahmoud Samir    | mahmoud257678@bue.edu.eg     | A-14  |
| 250498 | Youssef Hassan   | youssef250498@bue.edu.eg     | A-12  |
| 257511 | Ibrahim Mostafa  | ibrahim257511@bue.edu.eg     | A-8   |
| 254588 | Zeina Alaaeldin  | zeina254588@bue.edu.eg       | A-14  |
| 257156 | Zainab Sabit     | zainab257156@bue.edu.eg      | A-14  |
| 252304 | Malak Waleed     | malak252304@bue.edu.eg       | A-7   |
## ✨ Features
### Account Management
- **Multiple Account Types**: Support for Savings, Current, Business, and Premium accounts
- **Account Status Tracking**: Active, frozen, or closed account states
- **Tiered Client System**: Standard, Premium, and First-Class client categories
### Transaction Operations
- **Deposits & Withdrawals**: Secure money-in and money-out operations
- **Fund Transfers**: Transfer between accounts with validation
- **Transaction History**: Complete audit trail of all account activities
### Security & Validation
- **Exception Handling**: Comprehensive error management for invalid operations
- **Login Authentication**: Secure user authentication system
- **Insufficient Funds Protection**: Prevents overdrafts beyond allowed limits
### Data Persistence
- **File-Based Storage**: Persistent storage using text files
- **Automatic Data Loading**: Seamless data restoration on startup
## 🏗️ Project Structure
```
OnlineBankingSystem/
├── src/banking/
│   ├── api/                    # Interface definitions
│   │   ├── Insurable.java
│   │   ├── Transferable.java
│   │   └── LoanEligible.java
│   ├── app/                    # Main application entry point
│   │   └── BankSystem.java
│   ├── common/                 # Enums and constants
│   │   ├── AccountType.java
│   │   ├── AccountStatus.java
│   │   ├── ClientType.java
│   │   └── TransactionType.java
│   ├── core/                   # Core business logic
│   │   ├── account/           # Account implementations
│   │   ├── transaction/       # Transaction classes
│   │   └── user/              # User hierarchy
│   ├── exception/             # Custom exceptions
│   ├── persistence/           # File I/O operations
│   ├── presentation/          # UI components
│   └── service/               # Business services
├── data/                       # Data storage files
│   ├── bank_accounts.txt
│   ├── bank_transactions.txt
│   └── bank_users.txt
└── README.md
```
## 🚀 Getting Started
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)
### Compilation
Compile all Java source files:
```bash
javac -d out src/banking/**/*.java
```
Or compile individual modules:
```bash
mkdir -p out
javac -d out src/banking/app/BankSystem.java src/banking/**/*.java
```
### Running the Application
```bash
java -cp out banking.app.BankSystem
```
## 📦 Core Components
### Account Hierarchy
- `Account` (Abstract Base)
  - `SavingsAccount` - Interest-bearing personal accounts
  - `CurrentAccount` - Day-to-day banking
  - `BusinessAccount` - Corporate banking solutions
  - `PremiumAccount` - High-value client accounts
### User Hierarchy
- `User` (Abstract Base)
  - `Admin` - System administrators
  - `Client` (Abstract)
    - `StandardClient` - Basic tier clients
    - `PremiumClient` - Enhanced benefits
    - `FirstClassClient` - Top-tier privileges
### Transaction Types
- `Deposit` - Add funds to account
- `Withdrawal` - Remove funds from account
- `Transfer` - Move funds between accounts
### Key Interfaces
- `Insurable` - Accounts eligible for insurance
- `Transferable` - Accounts supporting transfers
- `LoanEligible` - Accounts qualifying for loans
## 🛠️ Technologies Used
- **Language**: Java
- **Architecture**: Object-Oriented Design
- **Design Patterns**: Inheritance, Polymorphism, Encapsulation
- **Data Storage**: File-based persistence (TXT files)
- **Exception Handling**: Custom exception hierarchy
## 📝 Usage Examples
### Creating an Account
```java
Account savings = new SavingsAccount("12345", initialBalance);
```
### Making a Deposit
```java
Transaction deposit = new Deposit(account, amount);
transactionManager.execute(deposit);
```
### Transferring Funds
```java
Transfer transfer = new Transfer(sourceAccount, targetAccount, amount);
transactionManager.execute(transfer);
```
## 🔒 Exception Handling
The system includes comprehensive exception handling:
- `InvalidAmountException` - For negative or zero amounts
- `InsufficientFundsException` - When balance is inadequate
- `AccountNotFoundException` - For non-existent accounts
- `TransferFailedException` - When transfers cannot complete
- `InvalidLoginException` - Authentication failures
- `UserAlreadyExistsException` - Duplicate user registration
## 📄 License
This project is developed for educational purposes as part of the Programming in Java course at BUE.
## 📞 Contact
For inquiries, please contact any team member via their BUE email address listed above.
---
*Developed by Team 3 | British University in Egypt | 2026*
