package websocket.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

/** author: Ranjith Manickam @ 30 May' 2019 */
public class Driver {

    public static void main(String[] args) {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.setWelcomeFiles(new String[]{"index.html"});
        context.setBaseResource(Resource.newClassPathResource("index.html"));

        ServletHolder holder = new ServletHolder("default", DefaultServlet.class);
        context.addServlet(holder, "/");

        try {
            ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);
            container.addEndpoint(Service.class);

            server.start();
            server.dump(System.err);
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
