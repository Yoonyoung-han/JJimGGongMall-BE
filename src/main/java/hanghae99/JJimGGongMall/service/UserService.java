package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.domain.constant.Role;
import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.dto.request.RequestCheckDuplicateDto;
import hanghae99.JJimGGongMall.dto.request.RequestUpdateUserInfoDto;
import hanghae99.JJimGGongMall.dto.response.GetUserInfoDTO;
import hanghae99.JJimGGongMall.repository.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GetUserInfoDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not Found " + userId));
        return GetUserInfoDTO.of(user);
    }

    public boolean isDuplicate(RequestCheckDuplicateDto request) {
        String target = request.getValue();
        Optional<User> userOptional;
        if (request.getCheckType().equals("Email")){
            userOptional = userRepository.findByEmail(target);
        }else {
            userOptional = userRepository.findByAccountName(target);
        }
        return userOptional.isPresent();
    }

    @Transactional
    public GetUserInfoDTO updateUserInfo(Long userId, RequestUpdateUserInfoDto request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not Found " + userId));
        user.setBirthday(request.getBirthday());
        user.setGender(request.getGender());
        try {
            Role role = Role.valueOf(request.getRole().toUpperCase());
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + request.getRole());
        }
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());

        User saved = userRepository.save(user);
        return GetUserInfoDTO.of(saved);
    }
}
