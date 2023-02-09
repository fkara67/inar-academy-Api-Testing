package API.Jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import java.io.File;

import static io.restassured.RestAssured.*;

public class JiraTest {
    public static void main(String[] args) {
        RestAssured.baseURI = "http://localhost:8080/";

        // Login Scenario
        SessionFilter session = new SessionFilter();
        String response = given().relaxedHTTPSValidation().header("Content-Type","application/json").
                body("{ \"username\": \"fkara67\", \"password\": \"5355159Fk@\" }").log().all().
                filter(session).when().post("rest/auth/1/session").
                then().log().all().extract().response().asString();

        String expectedMessage = "Hi, How are you?";
        // Add comment
        String addCommentResponse = given().pathParam("id","10000").log().all().
                header("Content-Type","application/json").
                body("{\n" +
                        "    \"body\": \""+ expectedMessage + "\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session).when().post("rest/api/2/issue/{id}/comment").
                then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(addCommentResponse);
        String commentId = js.get("id");


        // Add Attachment
        given().header("X-Atlassian-Token","no-check").
                filter(session).pathParam("id","10000").
                header("Content-Type","multipart/form-data").
                multiPart("file",new File("jira.txt")).
                when().post("rest/api/2/issue/{id}/attachments").
                then().log().all().assertThat().statusCode(200);


        // Get Issue
        String issueDetails = given().filter(session).pathParam("id","10000").
                queryParam("fields","comment").
                get("rest/api/2/issue/{id}").then().
                log().all().extract().response().asString();
        System.out.println(issueDetails);

        JsonPath js1 = new JsonPath(issueDetails);
        int commentsCount = js1.get("fields.comment.comments.size()");

        for (int i = 0; i < commentsCount; i++) {
            String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();

            if (commentIdIssue.equalsIgnoreCase(commentId)) {
                String message = js1.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println(message);
                Assert.assertEquals(message,expectedMessage);
                break;
            }
        }

    }
}
