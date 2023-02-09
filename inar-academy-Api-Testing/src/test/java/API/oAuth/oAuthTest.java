package API.oAuth;

import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class oAuthTest {
    public static void main(String[] args) throws InterruptedException {

        // Get Code
        /*WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("fkara1667@gmail.com");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("554555@");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);*/
        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];

        //Get Access Token
        String accesstokenResponse = given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type","authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js = new JsonPath(accesstokenResponse);
        String accessToken = js.getString("access_token");


        // Actual Request
        String response = given().queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response);

    }
}
