package edu.hm.bugproducer;

import edu.hm.bugproducer.restAPI.TokenUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestApiTest {


    //URL
    private static final String URL = "http://localhost:8082/auth/";
    private static final String URLLOGIN = "http://localhost:8082/auth/login/";
    private static final String URLVERIFY = "http://localhost:8082/auth/verify/";

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
        HttpResponse response = client.execute(loginToWebsite);
        writeContent(response);

        assertEquals(200,response.getStatusLine().getStatusCode());

    }

    @Test
    public void testLoginWithCurruptData() throws IOException {
        List<NameValuePair> userData = new ArrayList<>();
        userData.add(new BasicNameValuePair("user",CORRUPTUSER));
        userData.add(new BasicNameValuePair("password",CORRUPTPASSWORD));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost loginToWebsite = new HttpPost(URLLOGIN);
        loginToWebsite.setEntity(new UrlEncodedFormEntity(userData));
        loginToWebsite.addHeader("content-Type", "application/x-www-form-urlencoded");
        HttpResponse response =client.execute(loginToWebsite);
        assertEquals(401,response.getStatusLine().getStatusCode());
        writeContent(response);

    }

    @Test
    public void testValidWithVaildData() throws IOException {
        List<NameValuePair> userData = new ArrayList<>();
        List<NameValuePair> tokenData = new ArrayList<>();

        userData.add(new BasicNameValuePair("user",USER));
        userData.add(new BasicNameValuePair("password",PASSWORD));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost loginToWebsite = new HttpPost(URLLOGIN);
        loginToWebsite.setEntity(new UrlEncodedFormEntity(userData));
        loginToWebsite.addHeader("content-Type", "application/x-www-form-urlencoded");
        HttpResponse response = client.execute(loginToWebsite);

        String token = IOUtils.toString(response.getEntity().getContent());
        tokenData.add(new BasicNameValuePair("token",token));
        HttpGet verify = new HttpGet(URLVERIFY+token);


        HttpResponse response2 = client.execute(verify);
        assertEquals(200,response2.getStatusLine().getStatusCode());






    }

    @Test
    public void testValidWithCorruptData() throws IOException {
        List<NameValuePair> userData = new ArrayList<>();
        List<NameValuePair> tokenData = new ArrayList<>();

        userData.add(new BasicNameValuePair("user", CORRUPTUSER));
        userData.add(new BasicNameValuePair("password", CORRUPTPASSWORD));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost loginToWebsite = new HttpPost(URLLOGIN);
        loginToWebsite.setEntity(new UrlEncodedFormEntity(userData));
        loginToWebsite.addHeader("content-Type", "application/x-www-form-urlencoded");
        HttpResponse response = client.execute(loginToWebsite);

        String token = IOUtils.toString(response.getEntity().getContent());
        tokenData.add(new BasicNameValuePair("token", token));
        HttpGet verify = new HttpGet(URLVERIFY + token);

        HttpResponse response2 = client.execute(verify);
        assertEquals(401, response2.getStatusLine().getStatusCode());

    }




    private void writeContent(HttpResponse response) throws IOException {
        OutputStream out = System.out;
        InputStream input = response.getEntity().getContent();
        for(int i=input.read(); i>=0; i=input.read()){
            out.write(i);
            out.flush();
        }
    }


}
