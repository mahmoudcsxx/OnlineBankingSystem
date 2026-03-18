package banking.service;

import banking.core.account.Account;
import banking.core.transaction.Transaction;
import banking.core.transaction.TransactionHistory;
import banking.core.transaction.Deposit;
import banking.core.transaction.Withdrawal;
import banking.core.transaction.Transfer;

import banking.common.TransactionType;
import banking.exception.InsufficientFundsException;
import banking.exception.BankException;

import java.util.ArrayList;

public class TransactionManager {

 /**
  * Executes a transaction by calling its execute() method.
  * Records the transaction in the source and destination account histories.
  *
  * @param t Transaction object (Deposit, Withdrawal, Transfer)
  * @throws BankException if validation fails
  */
 public void executeTransaction(Transaction t) throws BankException {
  if (validateTransaction(t)) {
   t.execute();

   // FIXED: getSourceAccount() → getAccount()
   // NULL CHECK: getAccount() may return null for Deposit transactions

   if (t.getAccount() != null) {
    t.getAccount().getTransactionHistory().addTransaction(t);
   }

   // FIXED: getDestinationAccount() → check instanceof Transfer first

   if (t instanceof Transfer) {
    ((Transfer) t).getDestinationAccount().getTransactionHistory().addTransaction(t);
   }
  } else {
   throw new BankException("Transaction validation failed");
  }
 }

 //-------------------------------------------------------------------------

 /**
  * Validates whether a transaction can be performed.
  * For withdrawal/transfer, ensures source has enough balance.
  * Deposits are always valid.
  *
  * @param t Transaction to validate
  * @return true if valid, false otherwise
  */
 public boolean validateTransaction(Transaction t) {
  if (t.getType() == TransactionType.WITHDRAWAL ||
          t.getType() == TransactionType.TRANSFER) {

   // FIXED: getSourceAccount() → getAccount()

   Account source = t.getAccount();
   if (source.getBalance() < t.getAmount()) return false;
  }
  return true;
 }

 //--------------------------------------------------------------------------

 /**
  * Deposit money into an account.
  *
  * @param account account to deposit into
  * @param amount  amount to deposit
  */
 public void deposit(Account account, double amount) {
  Deposit t = new Deposit(account, amount);
  try {
   executeTransaction(t);
  } catch (BankException e) {
   e.printStackTrace();
  }
 }

 //--------------------------------------------------------------------------------

 /**
  * Withdraw money from an account.
  *
  * @param account account to withdraw from
  * @param amount  amount to withdraw
  * @throws InsufficientFundsException if balance insufficient
  */
 public void withdraw(Account account, double amount) throws InsufficientFundsException {
  Withdrawal t = new Withdrawal(account, amount);
  try {
   executeTransaction(t);
  } catch (BankException e) {
   throw new InsufficientFundsException("Withdrawal failed: " + e.getMessage());
  }
 }

 //-----------------------------------------------------------------------------------

 /**
  * Transfer money from one account to another.
  *
  * @param from   source account
  * @param to     destination account
  * @param amount amount to transfer
  * @throws InsufficientFundsException if source does not have enough balance
  */
 public void transfer(Account from, Account to, double amount) throws InsufficientFundsException {
  Transfer t = new Transfer("TXN123", amount, from, to);
  try {
   executeTransaction(t);
  } catch (BankException e) {
   throw new InsufficientFundsException("Transfer failed: " + e.getMessage());
  }
 }


 /**
  * Print all transactions of an account.
  *
  * @param account Target account
  */
 public void printTransactionHistory(Account account) {
  TransactionHistory history = account.getTransactionHistory();

  // FIXED: getTransactions() → getHistory(), forEach → regular for loop to avoid ambiguous println

  ArrayList<Transaction> list = history.getHistory();
  for (int i = 0; i < list.size(); i++) {
   System.out.println(list.get(i).toString());
  }
 }
}