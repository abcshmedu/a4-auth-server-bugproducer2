package edu.hm.bugproducer;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.*;

/**
 * Start the application without an AppServer like tomcat.
 *
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 */
public class JettyStarter{

    public static final String APP_URL = "/";
    public static final int PORT = 8082;

    private static Server jetty;

    /**
     * Deploy local directories using Jetty without needing a container-based deployment.
     *
     * @throws Exception might throw for several reasons.
     */
    public  void startJetty() throws Exception {

        jetty = new Server(PORT);
        ServletContextHandler handler = new ServletContextHandler(jetty, "/");
        handler.addServlet(ExampleServlet.class, "/");
        jetty.start();
        System.out.println("Jetty listening on port " + PORT);


        //jetty.join();
    }

    public void stopJetty() throws Exception {
        jetty.stop();
    }

}
