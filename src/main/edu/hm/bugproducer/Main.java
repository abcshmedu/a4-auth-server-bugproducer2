package edu.hm.bugproducer;

import edu.hm.bugproducer.restAPI.TokenUtils;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Start the application without an AppServer like tomcat.
 *
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 */
public class Main {

    public static final String APP_URL = "/";
    public static final int PORT = 8082;
    public static final String WEBAPP_DIR = "./src/main/webapp/";

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     *
     * @param args unused
     * @throws Exception might throw for several reasons.
     */
    public static void main(String... args) throws Exception {
        JettyStarter jettyStarter = new JettyStarter();
        jettyStarter.startJetty();
        /*String exampleToken = TokenUtils.createToken("Tom", "123456");

        System.err.println("TOKEN: " + exampleToken);
        System.err.println("ISVALID: " + TokenUtils.isNotExpired(exampleToken));

        Server server = new Server(8082);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        handler.addServlet(ExampleServlet.class, "/");
        server.start();*/
    }

}
