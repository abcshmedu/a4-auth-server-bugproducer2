package edu.hm.bugproducer;

import edu.hm.bugproducer.restAPI.TokenUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestApiTest {


    //URL
    private static final String URL = "http://localhost:8082";
    private static final String URLLOGIN = "http://localhost:8082/login/";

    //User
    private static final String USER = "John Doe";
    private static final String PASSWORD = "geheim";

    private static final String CORRUPTUSER = "Jane Doe";
    private static final String CORRUPTPASSWORD = "offen";


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
    public void testLoginWithVaildData() throws IOException {
        List<NameValuePair> userData = new ArrayList<>();

        userData.add(new BasicNameValuePair("user",USER));
        userData.add(new BasicNameValuePair("password",PASSWORD));


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost loginToWebsite = new HttpPost(URLLOGIN);

        loginToWebsite.setEntity(new UrlEncodedFormEntity(userData));
        loginToWebsite.addHeader("content-Type", "application/x-www-form-urlencoded");
        HttpResponse response =client.execute(loginToWebsite);

        assertEquals(200,response.getStatusLine().getStatusCode());

        System.err.print("TOKEN: "+response.getEntity().toString());



    }


}
