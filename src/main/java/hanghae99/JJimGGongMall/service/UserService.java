package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.repository.interfaces.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


}
