package API.Pojo.EcommerceApi;

import API.Pojo.EcommerceApi.Requests.LoginRequest;
import API.Pojo.EcommerceApi.Requests.OrderDetails;
import API.Pojo.EcommerceApi.Requests.Orders;
import API.Pojo.EcommerceApi.Responses.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceApiTest {
    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("fkara1667@gmail.com");
        loginRequest.setUserPassword("5355159Fk@");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);

        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").
                then().log().all().extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println(loginResponse.getToken());
        System.out.println(loginResponse.getUserId());


        // Add Product
        RequestSpecification addProductBaseReq = new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("authorization",token).build();

        RequestSpecification reqAddProduct = given().log().all().
                spec(addProductBaseReq).param("productName","laptop").
                param("productAddedBy",userId).param("productCategory","tech").
                param("productSubCategory","laptop").param("productPrice","10500").
                param("productDescription","Lenovo").param("productFor","men").
                multiPart("productImage",new File("C:\\Users\\fkara\\Desktop\\award.png"));

        String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").
                then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.get("productId");


        // Create Order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("authorization",token).
                setContentType(ContentType.JSON).build();

        OrderDetails orderDetail = new OrderDetails();
        orderDetail.setCountry("Turkey");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);

        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);
        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").
                then().log().all().extract().response().asString();

        System.out.println(responseAddOrder);


        // Delete Product
        RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().
                setBaseUri("https://rahulshettyacademy.com").
                addHeader("authorization",token).
                setContentType(ContentType.JSON).build();

        RequestSpecification deleteProductReq = given().log().all().spec(deleteProductBaseReq).
                pathParam("productId",productId);

        String deleteProductResponse = deleteProductReq.
                when().delete("/api/ecom/product/delete-product/{productId}").
                then().log().all().extract().response().asString();

        JsonPath js1 = new JsonPath(deleteProductResponse);
        String deleteProductMessage = js1.get("message");
        Assert.assertEquals("Product Deleted Successfully",deleteProductMessage);

    }
}
