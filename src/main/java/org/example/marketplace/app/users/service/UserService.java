package org.example.marketplace.app.users.service;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.users.dto.UserCreateRequest;
import org.example.marketplace.app.users.dto.UserFullInfo;
import org.example.marketplace.app.users.dto.UserPatchRequest;
import org.example.marketplace.app.users.exception.UserException;
import org.example.marketplace.app.users.mapper.UserMapper;
import org.example.marketplace.app.users.model.UserEntity;
import org.example.marketplace.app.users.repository.UserRepository;
import org.example.marketplace.common.dto.PaginatedResponse;
import org.example.marketplace.common.exception.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserFullInfo register(UserCreateRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email()))
            throw new ApiException(UserException.email_already_exists);

        UserEntity entity = userMapper.toUserEntity(request);
        entity.setPasswordHash(passwordEncoder.encode(request.password()));
        userRepository.save(entity);
        return userMapper.toFullInfo(entity);

    }

    public UserFullInfo getUser(Long id) {
        return userMapper.toFullInfo(userRepository.findById(id)
                .orElseThrow(() -> new ApiException(UserException.user_not_found)));
    }

    public PaginatedResponse<UserFullInfo> getAll(int limit, int offset) {
        long total = userRepository.count();
        List<UserFullInfo> users = userRepository.findAllPaginated(limit, offset).stream()
                .map(userMapper::toFullInfo)
                .toList();

        return new PaginatedResponse<>(
                users,
                limit,
                offset,
                total
        );
    }

    public UserFullInfo patchUser(Long id, UserPatchRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(UserException.user_not_found));

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getRole() != null)
            user.setRole(request.getRole());

        return userMapper.toFullInfo(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(UserException.user_not_found));

        userRepository.delete(user);
    }
}
