package hanghae99.JJimGGongMall.common.security;

import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        User user = userRepository.findByAccountName(accountName)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + accountName));

        return new UserDetailsImpl(user);
    }
}