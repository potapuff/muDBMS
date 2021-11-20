package ua.edu.sumdu.tss.mudbms.core.transaction_engine;

import ua.edu.sumdu.tss.mudbms.core.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionFactory {

    static private final List<Transaction> transactions = Collections.synchronizedList(new ArrayList<Transaction>());
    static int counter = 0;
    private static TransactionEngine instance;

    public static synchronized Transaction createTransaction() {
        if (instance == null) {
            throw new IllegalArgumentException("You need to run TransactionCoordinator.setup() before TransactionCoordinator.getInstance()");
        }
        var transaction = new Transaction(instance, counter++);
        transactions.add(transaction);
        return transaction;
    }

    public static Transaction get(int trx_id) {
        return transactions.stream().filter(transaction -> transaction.getId() == trx_id).findFirst().orElse(null);
    }

    public static synchronized void setup(TransactionEngine engine) {
        instance = engine;
    }


}
