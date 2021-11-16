package ua.edu.sumdu.tss.mudbms.command;

public class CommandWrite extends Command {
    public final String key;
    //public String oldValue;
    public final String value;

    public CommandWrite(int transaction_id, String key, String value) {
        super(transaction_id);
        this.key = key;
        this.value = value;
    }
}
