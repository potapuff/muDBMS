package ua.edu.sumdu.tss.mudbms.core;

import ua.edu.sumdu.tss.mudbms.command.Command;
import ua.edu.sumdu.tss.mudbms.command.CommandWrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wal {

    static private final List<Command> entry = Collections.synchronizedList(new ArrayList<Command>());

    public static String read(String key) {
        System.out.println("WAL read");
        System.out.println(entry.size());

        for (Command command : entry) {
            System.out.println(command);
            if (!(command instanceof CommandWrite)) {
                continue;
            }
            System.out.println(((CommandWrite) command).key);
            System.out.println(key);
            if (((CommandWrite) command).key.equals(key)) {
                return ((CommandWrite) command).value;
            }
        }
        return null;
    }

    public static void write(int transactionId, String key, String value) {
        var command = new CommandWrite(transactionId, key, value);
        entry.add(command);
    }
}
