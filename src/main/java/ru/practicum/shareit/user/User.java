package ru.practicum.shareit.user;

/**
 * TODO Sprint add-controllers.
 */

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Value
public class User {
    int id;
    @NotNull @NotBlank @Email
    String email;
    @NotNull @NotBlank @Pattern(regexp = "/^\\S\\z/")
    String login;
    String name;
    @PastOrPresent
    LocalDate birthday;

    public String toString(User user) {
        if (user.getName().isBlank()) {
            return user.toBuilder().name(login).build().toString();
        }
        return user.toString();
    }

    public String getName() {
        if (name.isBlank()) {
            return login;
        } else {
            return name;
        }
    }
}
