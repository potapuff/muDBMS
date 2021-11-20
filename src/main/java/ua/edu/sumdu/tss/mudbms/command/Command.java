package ua.edu.sumdu.tss.mudbms.command;

abstract public class Command {
    public final int trx_id;

    public Command(int transaction_id) {
        this.trx_id = transaction_id;
    }
}

