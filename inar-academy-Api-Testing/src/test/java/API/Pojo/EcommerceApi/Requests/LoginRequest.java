package API.Pojo.EcommerceApi.Requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String userEmail;
    private String userPassword;
}
