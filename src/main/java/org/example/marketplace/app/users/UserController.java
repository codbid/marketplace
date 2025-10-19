package org.example.marketplace.app.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.users.DTO.UserCreateRequest;
import org.example.marketplace.app.users.DTO.UserFullInfo;
import org.example.marketplace.app.users.DTO.UserPatchRequest;
import org.example.marketplace.common.DTO.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserFullInfo create(@Valid @RequestBody UserCreateRequest request) {
        return service.createUser(request);
    }

    @GetMapping("/{id}/get")
    public UserFullInfo get(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping("all")
    public PaginatedResponse<UserFullInfo> getAll(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return service.getAll(limit, offset);
    }

    @PatchMapping("/{id}/edit")
    public UserFullInfo patch(@PathVariable Long id, @Valid @RequestBody UserPatchRequest request) {
        return service.patchUser(id, request);
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
