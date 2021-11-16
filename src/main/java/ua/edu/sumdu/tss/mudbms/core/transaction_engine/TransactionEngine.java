package ua.edu.sumdu.tss.mudbms.core.transaction_engine;

import ua.edu.sumdu.tss.mudbms.core.storage_engine.StorageEngine;

public abstract class TransactionEngine {

    StorageEngine storage;

    public TransactionEngine(StorageEngine storage) {
        this.storage = storage;
    }

    public abstract String read(String key);

    public abstract void write(String key, String value);

    public abstract boolean commit();

    public abstract boolean rollback();
}
