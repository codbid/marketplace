package org.example.marketplace.app.users;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.marketplace.app.users.DTO.UserCreateRequest;
import org.example.marketplace.app.users.DTO.UserFullInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public UserFullInfo create(UserCreateRequest request) throws BadRequestException {
        return service.createUser(request);
    }
}
