package ua.edu.sumdu.tss.mudbms.core;

abstract public class Command {
    public final int transaction_id;

    public Command(int transaction_id) {
        this.transaction_id = transaction_id;
    }
}

class CommandStart extends Command {
    public CommandStart(int transaction_id) {
        super(transaction_id);
    }
}

class CommandCommit extends Command {
    public CommandCommit(int transaction_id) {
        super(transaction_id);
    }
}

class CommandWrite extends Command {
    public final String key;
    //public String oldValue;
    public final String value;

    public CommandWrite(int transaction_id, String key, String value) {
        super(transaction_id);
        this.key = key;
        this.value = value;
    }
}

