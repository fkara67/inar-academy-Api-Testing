package API.Basics;

import API.Files.Payloads;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidationTest {

    @Test
    public void sumOfCoursePrices() {
        JsonPath js = new JsonPath(Payloads.coursePrice());
        int courseCount = js.getInt("courses.size()");

        int actualAmount = js.getInt("dashboard.purchaseAmount");

        int expectedAmount = 0;
        for (int i = 0; i < courseCount; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.get("courses[" + i + "].copies");
            System.out.println(price * copies);
            expectedAmount += price * copies;
        }

        Assert.assertEquals(actualAmount,expectedAmount);
    }

}
