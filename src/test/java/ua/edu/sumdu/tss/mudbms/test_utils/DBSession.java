package ua.edu.sumdu.tss.mudbms.test_utils;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class DBSession {

    public static final String JSESSIONID = "JSESSIONID";
    private final String session_id;

    public DBSession() {
        var response = Unirest.get("").asString();
        if (response.getStatus() != 200) {
            throw new RuntimeException(response.getBody());
        }
        this.session_id = response.getCookies().getNamed(JSESSIONID).getValue();
        if (this.session_id == null) {
            throw new RuntimeException("No session");
        }
    }

    public GetRequest get(String url) {
        return Unirest.get(url).cookie(JSESSIONID, session_id);
    }

    public HttpRequestWithBody post(String url) {
        return Unirest.post(url).cookie(JSESSIONID, session_id);
    }
}
