package org.example.marketplace.app.users.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.app.users.service.UserService;
import org.example.marketplace.app.users.dto.UserCreateRequest;
import org.example.marketplace.app.users.dto.UserFullInfo;
import org.example.marketplace.app.users.dto.UserPatchRequest;
import org.example.marketplace.common.dto.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserFullInfo register(@Valid @RequestBody UserCreateRequest request) {
        return service.register(request);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/get")
    public UserFullInfo get(@PathVariable Long id) {
        return service.getUser(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("all")
    public PaginatedResponse<UserFullInfo> getAll(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return service.getAll(limit, offset);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}/edit")
    public UserFullInfo patch(@PathVariable Long id, @Valid @RequestBody UserPatchRequest request) {
        return service.patchUser(id, request);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }
}
