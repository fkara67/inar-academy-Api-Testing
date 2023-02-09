package API.Pojo.GoogleMapsApi;

import API.Pojo.GoogleMapsApi.Requests.AddPlace;
import API.Pojo.GoogleMapsApi.Requests.Location;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddPlaceTest_WithBuild {
    public static void main(String[] args) throws JsonProcessingException {

        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");

        ObjectMapper objectMapper = new ObjectMapper();

        AddPlace.AddPlaceBuilder builder = AddPlace.builder();

        builder.accuracy(50).address("29, side layout, cohen 09").name("Frontline house").
                language("French-IN").phone_number("235460").
                website("https://rahulshettyacademy.com").types(myList).
                location(Location.builder().lat(-38.383494).lng(33.427362).build()).build();

        AddPlace p = builder.build();
        objectMapper.writeValueAsString(p);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addQueryParam("key","qaclick123").
                setContentType(ContentType.JSON).build();

        ResponseSpecification resSpec = new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req).body(p);
        Response response = res.when().post("/maps/api/place/add/json").
                then().spec(resSpec).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);

    }
}
