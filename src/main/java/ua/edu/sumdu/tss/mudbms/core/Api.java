package ua.edu.sumdu.tss.mudbms.core;

import io.javalin.http.Context;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Date;
import java.util.Optional;

public class Api {

    public static void start(Context context) {
        context.sessionAttribute("Valid until", new Date());
        context.result("Session started");
    }


    public static void rollback(Context context) {
        throw new NotImplementedException();
    }

    public static void commit(Context context) {
        throw new NotImplementedException();
    }

    public static void read(Context context) {
        var transaction = Api.getTransaction(context);
        System.out.println("READ");
        var key = context.pathParam("key");
        var value = Optional.ofNullable(Wal.read(key)).orElse(CachedStorage.read(key));
        context.result((value == null) ? "NULL" : value);
    }

    private static Transaction getTransaction(Context context) {
        Date validUntil = context.sessionAttribute("Valid until");
        if (validUntil != null) {
            context.sessionAttribute("Valid until", new Date());
        }
        Transaction trx;
        Integer trx_id = context.sessionAttribute("TRX");

        if (trx_id == null) {
            trx = Transaction.create();
            context.sessionAttribute("TRX", trx.getId());
        } else {
            trx = Transaction.get(trx_id);
        }
        System.out.println("Request TRX:" + trx.getId());
        return trx;
    }

    public static void write(Context context) {
        System.out.println("WRITE");
        var key = context.pathParam("key");
        var trx = getTransaction(context);
        var value = context.formParam("value");
        Wal.write(trx.getId(), key, value);
        context.status(204);
    }

    public static void status(Context context) {
        System.out.println("Status");
        System.out.println(context.req.getSession().getId());
        var transaction_id = context.pathParamAsClass("transaction_id", Integer.class).get();
        var t = Transaction.get(transaction_id);
        context.result(Integer.toString(t.getId()));
    }
}
