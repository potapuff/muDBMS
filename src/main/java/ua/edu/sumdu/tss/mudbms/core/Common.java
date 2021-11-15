package ua.edu.sumdu.tss.mudbms.core;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.Server;
import ua.edu.sumdu.tss.mudbms.utils.Keys;

import java.util.Date;

class Common {

    private static Server app = null;

    @BeforeAll
    static void initServer() {
        app = new Server();
        app.start(Keys.APP_PORT);
    }

    @AfterAll
    static void stopServer() {
        app.stop();
        app = null;
    }

    @Test
    void singleUserEnveronment() {
        String randomValue = (new Date()).toString();
        String randomKey = Integer.toString(randomValue.hashCode());
        var transaction = Unirest.get(Keys.URL + "/").asString();
        String transaction_id = transaction.getBody();
        System.out.println(transaction.getBody());
        var first = Unirest.get(Keys.URL + "/" + transaction_id + "/" + randomKey).asString();
        Unirest.post(Keys.URL + "/" + transaction_id + "/key").field("value", randomValue).asEmpty();
        var second = Unirest.get(Keys.URL + "/" + transaction_id + "/" + randomKey).asString();
        System.out.println(first);
        System.out.println(second);
    }

}