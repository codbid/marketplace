package org.example.marketplace.app.users.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.example.marketplace.app.users.model.UserRole;

@Data
public class UserPatchRequest {
    @Email private String email;
    private String name;
    private UserRole role;
}
