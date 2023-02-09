package Resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {


        if(req == null) {
            PrintStream log = new PrintStream(Files.newOutputStream(Paths.get("logging.txt")));
            req = new RequestSpecBuilder().
                    setBaseUri(getGlobalValue("baseURI")).
                    addQueryParam("key", "qaclick123").
                    addFilter(RequestLoggingFilter.logRequestTo(log)).
                    addFilter(ResponseLoggingFilter.logResponseTo(log)).
                    setContentType(ContentType.JSON).build();

            return req;
        }
        return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\fkara\\Desktop\\workspace1\\API-testing-java-inar\\inar-academy-Api-Testing\\inar-academy-Api-Testing\\src\\test\\java\\Resources\\Global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static void callsHttpRequest(String method,RequestSpecification res,String resource) {

        if (method.equalsIgnoreCase("POST")) {
            res.when().post(resource);
        }
        else if (method.equalsIgnoreCase("GET")) {
            res.when().get(resource);
        }
    }
}
