package ua.edu.sumdu.tss.mudbms;

abstract public class Command {
    public final int transaction_id;

    public Command(int transaction_id) {
        this.transaction_id = transaction_id;
    }
}

