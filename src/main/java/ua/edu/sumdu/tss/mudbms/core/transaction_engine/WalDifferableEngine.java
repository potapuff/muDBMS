package ua.edu.sumdu.tss.mudbms.core.transaction_engine;

import ua.edu.sumdu.tss.mudbms.command.Command;
import ua.edu.sumdu.tss.mudbms.command.CommandCommit;
import ua.edu.sumdu.tss.mudbms.command.CommandWrite;
import ua.edu.sumdu.tss.mudbms.core.Transaction;
import ua.edu.sumdu.tss.mudbms.core.storage_engine.StorageEngine;

import java.util.LinkedList;

//TODO: add syncronization pimitives
public class WalDifferableEngine extends TransactionEngine {

    private static final LinkedList<Command> wal = new LinkedList<Command>();
    public static long opCouter = 0;

    public WalDifferableEngine(StorageEngine storage) {
        super(storage);
    }

    @Override
    public String read(Transaction transaction, String key) {
        //Step 1 try to find at WAL
        int trx_id = transaction.getId();
        var iterator = wal.descendingIterator();
        while (iterator.hasNext()) {
            var command = iterator.next();
            if (command instanceof CommandWrite c) {
                if (c.trx_id == trx_id && c.key.equals(key)) {
                    return c.value;
                }
            }
        }
        return storage.read(key);
    }

    @Override
    public void write(Transaction transaction, String key, String value) {
        wal.add(new CommandWrite(transaction.getId(), key, value));
        opCouter++;
    }

    @Override
    //TODO: persist WAL
    public boolean commit(Transaction transaction) {
        wal.addLast(new CommandCommit(transaction.getId()));
        opCouter++;
        return true;
    }

    @Override
    public boolean rollback(Transaction transaction) {
        opCouter++;
        var iterator = wal.iterator();
        while (iterator.hasNext()) {
            var command = iterator.next();
            if (command.trx_id == transaction.getId()) {
                iterator.remove();
            }
        }
        opCouter++;
        return true;
    }
}
