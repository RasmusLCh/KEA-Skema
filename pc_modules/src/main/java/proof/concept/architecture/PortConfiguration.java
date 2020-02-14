package proof.concept.architecture;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This code is taken from https://stackoverflow.com/questions/26655875/spring-boot-redirect-http-to-https
 * As we have not written this ourselves, we have decided to comment it for clearness of what is actually going on, to ensure our own understanding
 */
@Configuration
public class PortConfiguration {
    //Defined in application.properties file
    @Value("${server.port.http:80}")
    int httpPort;

    //Defined in application.properties file
    @Value("${server.port:443}")
    int httpsPort;

    //Defined in application.properties file
    @Value("${server.port.service:7500}")
    int servicePort;

    /**
     * This bean forces all communication to be secure
     * And setup our redirects (by calling)redirectConnector()
     * Our bean override the default value for the ServletWebServerFactory, so instead of loooking for HTTP on port 8080, we are now listening on
     * Source: https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/web/embedded/tomcat/TomcatServletWebServerFactory.html#addAdditionalTomcatConnectors-org.apache.catalina.connector.Connector...-
     * <p>
     * A Bean is created, overriding the default ServletWebServerFactory
     * From https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/web/embedded/tomcat/TomcatServletWebServerFactory.html#addAdditionalTomcatConnectors-org.apache.catalina.connector.Connector...-  : Unless explicitly configured otherwise this factory will create containers that listen for HTTP requests on port 8080.
     * We set the transport layer to use Confidential (Ref: https://docs.oracle.com/cd/E19798-01/821-1841/bncbk/index.html) which forces HTTPS and make it match on /* which is everything on the server
     * By calling addAdditionalTomcatConnectors, we add one additional connector, which forwards HTTP to HTTPS
     * Redirects can be added if needed.
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        //anonymous class that inherits from TomcatServletWebServerFactory and overrides postProcessContext
        //https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/web/embedded/tomcat/TomcatServletWebServerFactory.html
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(httpsPort) {
            //Post process the Tomcat Context before it's used with the Tomcat Server. Subclasses can override this method to apply additional processing to the Context.

            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                //The choices for transport guarantee are CONFIDENTIAL, INTEGRAL, or NONE. If you specify CONFIDENTIAL or INTEGRAL as a security constraint, it generally means that the use of SSL is required and applies to all requests that match the URL patterns in the web resource collection, not just to the login dialog box.
                //Ref: https://docs.oracle.com/cd/E19798-01/821-1841/bncbk/index.html
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                //This is the URL pattern so /* matches any URL pattern
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }


        };
        //Here we add additional connectors, we can add as many as we want, just use an array
        //If no value is specified in the application.properties we ignore this. Since the forwarding can also be done by firewall forwarding
        if (httpPort != 0) {
            tomcat.addAdditionalTomcatConnectors(getAdditionalConnectors());
        }
        return tomcat;
    }

    /**
     * This metode redirect all request made on server.port.http to server.port, both are defined in application.properties
     */
    private Connector[] getAdditionalConnectors() {
        Connector[] c = new Connector[2];
        //Here we make the connection from calling servletContainer as input to the contructor
        Connector httpcon = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        //https://tomcat.apache.org/tomcat-5.5-doc/config/http.html
        //If this Connector is supporting non-SSL requests, and a request is received for which a matching <security-constraint> requires SSL transport, Catalina will automatically redirect the request to the port number specified here.
        httpcon.setScheme("http");
        System.out.println("httpPort = " + httpPort);
        httpcon.setPort(httpPort);
        httpcon.setSecure(false);
        httpcon.setRedirectPort(httpsPort);
        c[0] = httpcon;

        //Service port
        Connector servicecon = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        servicecon.setScheme("http");
        System.out.println("service port = " + servicePort);
        servicecon.setPort(servicePort);
        //By using secure, we ignore the settings in postProcessContext, that would otherwise force us to redirect to HTTPS (which is secure..)
        servicecon.setSecure(true);
        c[1] = servicecon;
        return c;
    }
}