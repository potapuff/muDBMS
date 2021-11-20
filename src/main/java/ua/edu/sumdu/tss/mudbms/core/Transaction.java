package ua.edu.sumdu.tss.mudbms.core;

import ua.edu.sumdu.tss.mudbms.core.transaction_engine.TransactionEngine;

public class Transaction {

    private final int id;
    TransactionEngine engine;

    public Transaction(TransactionEngine engine, int trx_id) {
        this.engine = engine;
        this.id = trx_id;
    }

    public int getId() {
        return id;
    }

    public String read(String key) {
        return engine.read(this, key);
    }

    public void write(String key, String value) {
        engine.write(this, key, value);
    }

    public boolean commit() {
        return engine.commit(this);
    }

    public boolean rollback() {
        return engine.rollback(this);
    }

}
