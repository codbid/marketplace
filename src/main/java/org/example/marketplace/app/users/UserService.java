package org.example.marketplace.app.users;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.marketplace.app.users.DTO.UserCreateRequest;
import org.example.marketplace.app.users.DTO.UserFullInfo;
import org.example.marketplace.app.users.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserFullInfo createUser(UserCreateRequest request) throws BadRequestException {
        if (userRepository.existsByEmailIgnoreCase(request.email()))
            throw new BadRequestException("User with this email already exists");

        UserEntity entity = request.toEntity();
        userRepository.save(entity);
        return new UserFullInfo(entity);

    }
}
