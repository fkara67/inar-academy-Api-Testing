package API.Pojo.GoogleMapsApi;

import API.Pojo.GoogleMapsApi.Requests.AddPlace;
import API.Pojo.GoogleMapsApi.Requests.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AddPlaceTest_SpecBuilder {

    public static void main(String[] args) {
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setName("Frontline house");
        p.setLanguage("French-IN");
        p.setPhone_number("0546");
        p.setWebsite("https://rahulshettyacademy.com");

        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);


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
