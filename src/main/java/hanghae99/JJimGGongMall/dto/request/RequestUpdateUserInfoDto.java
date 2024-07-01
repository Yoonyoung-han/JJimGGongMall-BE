package hanghae99.JJimGGongMall.dto.request;

import hanghae99.JJimGGongMall.domain.Role;
import lombok.Data;

@Data
public class RequestUpdateUserInfoDto {
    private Long userId;
    private String accountName;
    private String username;
    private String phoneNumber;
    private String birthday;
    private String email;
    private String gender;
    private String password;
    private String role;
}
