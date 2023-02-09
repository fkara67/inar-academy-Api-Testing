package API.Basics;

import API.Files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        // Add Place -> update place with new address -> get place to validate if new address is present in response
        // validate if Add Place API is working as expected

        /*
        given - all input details
        when  - Submit the API
        Then  - validate the response
         */

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type","application/json").body(Payloads.addPlace())
                .when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
                .body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        // for parsing json
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        String newAddress = "70 winter walk, USA";

        // update place
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "    \"place_id\" : \"" + placeId+ "\",\n" +
                        "    \"address\" : \"" + newAddress + "\",\n" +
                        "    \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json").then().assertThat().log().all()
                .statusCode(200).body("msg", equalTo("Address successfully updated"));


        // get place to validate if new address is present in response
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json").then().assertThat().log().all()
                .statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);

        Assert.assertEquals(actualAddress,newAddress);


    }
}
