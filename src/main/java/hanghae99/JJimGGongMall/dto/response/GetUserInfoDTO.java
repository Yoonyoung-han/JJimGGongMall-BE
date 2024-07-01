package hanghae99.JJimGGongMall.dto.response;

import hanghae99.JJimGGongMall.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserInfoDTO {
    private Long userId;
    private String accountName;
    private String username;
    private String phoneNumber;
    private String birthday;
    private String email;
    private String gender;

    @Builder
    public GetUserInfoDTO(Long userId, String accountName, String username, String phoneNumber, String birthday, String email, String gender) {
        this.userId = userId;
        this.accountName = accountName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
    }

    public static GetUserInfoDTO of(User user){
        return GetUserInfoDTO.builder()
                .accountName(user.getAccountName())
                .birthday(user.getBirthday())
                .userId(user.getId())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
