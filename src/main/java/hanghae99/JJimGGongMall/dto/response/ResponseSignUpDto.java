package hanghae99.JJimGGongMall.dto.response;

import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.dto.request.RequestSignUpDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSignUpDto {
    private String accountName;
    private String email;
    private String username;

    @Builder
    public ResponseSignUpDto(String accountName, String email, String username) {
        this.accountName = accountName;
        this.email = email;
        this.username = username;
    }
}
