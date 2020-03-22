package com.test.launch;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * @author taojiaen
 * @date 2020-03-22 下午11:13
 */
public class Application {

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8089);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, "hello", new HttpServlet() {

            private static final long serialVersionUID = -8721082143781719844L;

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
                resp.setStatus(200);
                resp.setHeader("Transfer-Encoding", "chunked");
                resp.setHeader("Connection", "close");
                resp.getOutputStream().write('{');
                resp.getOutputStream().flush();
            }
        });
        ctx.addServletMappingDecoded("/*", "hello");

        tomcat.start();
        tomcat.getServer().await();
    }
}
