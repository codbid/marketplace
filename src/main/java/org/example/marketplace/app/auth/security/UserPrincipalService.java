package org.example.marketplace.app.auth.security;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new ApiException(UserException.user_not_found));
        return UserPrincipal.fromUser(user);

    }
}
