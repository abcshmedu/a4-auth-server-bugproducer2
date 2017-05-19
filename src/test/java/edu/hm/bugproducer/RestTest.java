package edu.hm.bugproducer;

import edu.hm.bugproducer.restAPI.TokenUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.assertTrue;

public class RestTest {

    private static final String URL = "http://localhost:8082";


    private JettyStarter jettyStarter;

    @Before
    public void openConnection() throws Exception {

        jettyStarter = new JettyStarter();
        jettyStarter.startJetty();

    }

    @After
    public void closeConnection() throws Exception {
        jettyStarter.stopJetty();
    }

    @Test
    public void testConnection() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
    }

    @Test
    public void checkIfTokenIsValid() {
        String exampleToken = TokenUtils.createToken("Tom", "123456");

        assertTrue(TokenUtils.isValidToken(exampleToken));
    }

    /*@Test
    public void checkIfTokeIsValidAfter5Minutes() throws InterruptedException {
        String exampleToken = TokenUtils.createToken("Tom", "123456");
        Thread.sleep(300000);
        assertFalse(TokenUtils.isValidToken(exampleToken));
    }*/
}
