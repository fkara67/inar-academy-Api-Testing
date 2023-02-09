package Resources;

import API.Pojo.GoogleMapsApi.Requests.AddPlace;
import API.Pojo.GoogleMapsApi.Requests.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayload(String name,String language,String address) {
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");

        AddPlace.AddPlaceBuilder builder = AddPlace.builder();
        builder.accuracy(50).address(address).name(name).
                language(language).phone_number("235460").
                website("https://rahulshettyacademy.com").types(myList).
                location(Location.builder().lat(-38.383494).lng(33.427362).build()).build();

        return builder.build();
    }
}
