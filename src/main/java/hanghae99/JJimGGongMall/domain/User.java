package hanghae99.JJimGGongMall.domain;

import hanghae99.JJimGGongMall.common.BaseEntity;
import hanghae99.JJimGGongMall.dto.request.RequestSignUpDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Slf4j
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String accountName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String birthday;
    private String gender;

    @Override
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 양방향 설정 , 빈열로 초기화
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Builder
    public User(String accountName, String username,String email, String password, String phoneNumber, String birthday, String gender) {
        this.accountName = accountName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        this.role = Role.USER;
    }

    // request를 객체로 만드는 메서드
    public static User of(RequestSignUpDto request){
        User user = User.builder()
                .accountName(request.getAccountName())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .username(request.getUsername())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .build();

        Address address = Address.builder()
                .alias(request.getAddressInfo().getAlias())
                .recipientName(request.getAddressInfo().getRecipientName())
                .addressLine1(request.getAddressInfo().getAddressLine1())
                .addressLine2(request.getAddressInfo().getAddressLine2())
                .city(request.getAddressInfo().getCity())
                .state(request.getAddressInfo().getState())
                .postalCode(request.getAddressInfo().getPostalCode())
                .country(request.getAddressInfo().getCountry())
                .isDefault(request.getAddressInfo().isDefault())
                .user(user)
                .build();

        user.addAddress(address);
        return user;
    }

    // Member <-> Address 연관관계 편의 메서드
    public void addAddress(Address address) {
        if (!this.addresses.contains(address)) {
            this.addresses.add(address);
        }
        address.setUser(this);
    }

}
