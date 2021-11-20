package ua.edu.sumdu.tss.mudbms.core.transaction_engine;

import ua.edu.sumdu.tss.mudbms.core.Transaction;
import ua.edu.sumdu.tss.mudbms.core.storage_engine.StorageEngine;

public abstract class TransactionEngine {

    StorageEngine storage;

    public TransactionEngine(StorageEngine storage) {
        this.storage = storage;
    }

    public abstract String read(Transaction transaction, String key);

    public abstract void write(Transaction transaction, String key, String value);

    public abstract boolean commit(Transaction transaction);

    public abstract boolean rollback(Transaction transaction);
}
