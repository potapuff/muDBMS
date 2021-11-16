package ua.edu.sumdu.tss.mudbms.core.transaction_engine;

import ua.edu.sumdu.tss.mudbms.core.storage_engine.StorageEngine;

public class NoTransactionEngine extends TransactionEngine {

    public NoTransactionEngine(StorageEngine storage) {
        super(storage);
    }

    @Override
    public String read(String key) {
        return storage.read(key);
    }

    @Override
    public void write(String key, String value) {
        storage.write(key, value);
    }

    @Override
    public boolean commit() {
        return true;
    }

    @Override
    public boolean rollback() {
        return true;
    }
}
