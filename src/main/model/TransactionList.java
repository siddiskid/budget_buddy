package model;

import com.sun.source.tree.Tree;

import java.util.*;

// The TransactionList class represents a list of Transactions.
public class TransactionList {
    private ArrayList<Transaction> listOfTransaction;

    // EFFECTS: initializes a new PurchaseList Object with essentials added
    public TransactionList(ArrayList<Transaction> listOfTransaction) {
        this.listOfTransaction = listOfTransaction;
    }

    // MODIFIES: this
    // EFFECTS: adds Transaction to listOfTransaction
    public void addTransaction(Transaction transaction) {
        this.listOfTransaction.add(transaction);
    }

    // REQUIRES: index < listOfTransaction.size()
    // MODIFIES: this
    // EFFECTS: removes Transaction from listOfTransaction
    public void removeTransaction(int index) {
        this.listOfTransaction.remove(index);
    }

    // EFFECTS: returns a TreeMap with Transactions' dates as keys and ArrayList of Transactions with same dates
    //          as values
    public TreeMap<String, ArrayList<Transaction>> sortByDate() {
        TreeMap<String, ArrayList<Transaction>> sortedByDate = new TreeMap<>(Collections.reverseOrder());

        for (Transaction t : listOfTransaction) {
            if (!(sortedByDate.containsKey(changeDateFormat(t)))) {
                ArrayList<Transaction> dummy = new ArrayList<>();
                dummy.add(t);
                sortedByDate.put(changeDateFormat(t), dummy);
            } else {
                ArrayList<Transaction> newListOfTransac = sortedByDate.get(changeDateFormat(t));
                newListOfTransac.add(t);
                sortedByDate.put(changeDateFormat(t), newListOfTransac);
            }
        }

        return sortedByDate;
    }

    // EFFECTS: returns the list of transactions
    public ArrayList<Transaction> getListOfTransaction() {
        return this.listOfTransaction;
    }

    // EFFECTS: returns Transaction with index i from listOfTransaction
    public Transaction getTransaction(int i) {
        return this.listOfTransaction.get(i);
    }

    // EFFECTS: returns size of listOfTransaction
    public int getSize() {
        return this.listOfTransaction.size();
    }

    // EFFECTS: returns total value of all transactions that happened in the current month in listOfTransaction
    public int getMonthlyTotal(String monthYear) {
        int total = 0;

        for (Transaction t : this.listOfTransaction) {
            if (t.getDate().substring(3).equals(monthYear)) {
                total += t.getCost();
            }
        }

        return total;
    }

    // EFFECTS: returns the total value of all transactions in listOfTransaction
    public int getAllTimeTotal() {
        int total = 0;

        for (Transaction t : this.listOfTransaction) {
            total += t.getCost();
        }

        return total;
    }

    // REQUIRES: lowerDate <= upperDate
    // EFFECTS: returns the total value of all transactions that occurred between lowerDate and upperDate
    public int getBetweenDatesTotal(String lowerDate, String upperDate) {
        int total = 0;

        for (Transaction t : listOfTransaction) {
            if (dateComparer(lowerDate, t.getDate(), upperDate)) {
                total += t.getCost();
            }
        }

        return total;
    }

    // EFFECTS: returns true if date is in between lowerDate and upperDate
    private boolean dateComparer(String lowerDate, String date, String upperDate) {
        if ((getYear(lowerDate) <= getYear(date)) && (getYear(date) <= getYear(upperDate))) {
            if ((getMonth(lowerDate) <= getMonth(date)) && (getMonth(date) <= getMonth(upperDate))) {
                return (getDay(lowerDate) <= getDay(date)) && (getDay(date) <= getDay(upperDate));
            }
        }

        return false;
    }

    // EFFECTS: returns int value of day in date
    private int getDay(String date) {
        return Integer.parseInt(date.substring(0, 2));
    }

    // EFFECTS: returns int value of month in date
    private int getMonth(String date) {
        return Integer.parseInt(date.substring(3, 5));
    }

    // EFFECTS: returns int value of year in date
    private int getYear(String date) {
        return Integer.parseInt(date.substring(6));
    }

    // REQUIRES: Transaction have date in the format "DD/MM/YYYY"
    // EFFECTS: returns date in the format "MM/DD/YYYY"
    private String changeDateFormat(Transaction t) {
        return t.getDate().substring(3, 6) + t.getDate().substring(0, 3) + t.getDate().substring(6);
    }
}
