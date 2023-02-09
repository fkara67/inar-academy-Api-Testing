package API.Pojo.EcommerceApi.Responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String userId;
    private String message;
}
