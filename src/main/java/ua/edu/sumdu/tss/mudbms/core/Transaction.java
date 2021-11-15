package ua.edu.sumdu.tss.mudbms.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transaction {
    static private final List<Transaction> transactions = Collections.synchronizedList(new ArrayList<Transaction>());
    static int counter = 0;
    private final int id;

    private Transaction(int id) {
        this.id = id;
    }

    static public Transaction create() {
        var t = new Transaction(counter++);
        transactions.add(t);
        return t;
    }

    public static Transaction get(int id) {
        return transactions.get(id);
    }

    public int getId() {
        return id;
    }

}
