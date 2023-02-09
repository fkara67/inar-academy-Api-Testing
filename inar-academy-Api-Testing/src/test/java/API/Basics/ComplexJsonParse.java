package API.Basics;

import API.Files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payloads.coursePrice());

        // print number of courses returned by API
        int courseCount = js.getInt("courses.size()");
        System.out.println(courseCount);

        // print purchase amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        // print title of first course
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println(titleFirstCourse);
        System.out.println("-----------------------------------");

        // print all course titles and their respective titles
        for (int i = 0; i < courseCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int coursePrices = js.getInt("courses[" + i + "].price");

            System.out.println(courseTitle + " --> " + coursePrices);
        }
        System.out.println("----------------------------------------");

        // print number of copies sold by RPA Course
        for (int i = 0; i < courseCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equals("RPA")) {
                int copies = js.getInt("courses[" + i + "].copies");
                System.out.println("number of copies sold by RPA Course " + copies);
                break;
            }
        }
        System.out.println("----------------------------------------------");


    }
}
