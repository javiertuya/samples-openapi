package giis.samples.openapi.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
 
public class RestServer {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
 
        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);
 
        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
 
        //jerseyServlet.setInitParameter(
        //        "jersey.config.server.provider.classnames",
        //        giis.samples.openapi.api.PetsApi.class.getCanonicalName());
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "giis.samples.openapi.api");
        jerseyServlet.setInitParameter(
                "PetsApi.implementation",
                "giis.samples.openapi.server.PetsServiceImpl");
 
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}