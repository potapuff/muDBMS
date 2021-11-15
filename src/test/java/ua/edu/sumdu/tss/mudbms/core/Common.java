package ua.edu.sumdu.tss.mudbms.core;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.Server;
import ua.edu.sumdu.tss.mudbms.test_utils.DBSession;
import ua.edu.sumdu.tss.mudbms.utils.Keys;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Common {

    private static Server app = null;

    @BeforeAll
    static void initServer() {
        app = new Server();
        app.start(Keys.APP_PORT);


        Unirest.config().enableCookieManagement(false).cacheResponses(false).concurrency(1, 1).defaultBaseUrl(Keys.URL + "/");
    }

    @AfterAll
    static void stopServer() {
        app.stop();
        app = null;
    }

    @Test
    void singleUserEnvironment() {
        String randomValue = (new Date()).toString();
        String randomKey = Integer.toString(Math.abs(randomValue.hashCode()));
        var session = new DBSession();
        var first = session.get(randomKey).asString();
        session.post(randomKey).field("value", randomValue).asEmpty();
        var second = session.get(randomKey).asString();
        assertEquals(first.getBody(), "NULL");
        assertEquals(second.getBody(), randomValue);
    }

    @Test
    void sequencialRead() {
        String randomValue = (new Date()).toString();
        String randomKey = Integer.toString(Math.abs(randomValue.hashCode()));
        var session1 = new DBSession();
        var session2 = new DBSession();
        var first = session1.get(randomKey).asString();
        var second = session2.get(randomKey).asString();
        assertEquals(first.getBody(), "NULL");
        assertEquals(second.getBody(), "NULL");

        session1.post(randomKey).field("value", randomValue).asEmpty();

        first = session1.get(randomKey).asString();
        second = session2.get(randomKey).asString();
        assertEquals(first.getBody(), randomValue);
        assertEquals(second.getBody(), randomValue);
    }

}