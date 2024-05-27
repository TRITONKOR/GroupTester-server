package com.tritonkor.net.response;

import com.tritonkor.persistence.entity.User;
import com.tritonkor.persistence.entity.User.Role;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private LocalDate birthday;
    private byte[] avatar;
    private Role role;

    public UserResponse(User user) {
        this.id = user.getId().toString();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.avatar = user.getAvatar();
        this.role = user.getRole();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"username\":\"" + username + '\"' +
                ", \"email\":\"" + email + '\"' +
                ", \"birthday\":\"" + birthday + '\"' +
                ", \"avatar\":" + Arrays.toString(avatar) +
                ", \"role\":\"" + role + '\"' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(birthday, that.birthday) &&
                Arrays.equals(avatar, that.avatar) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, email, birthday, role);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }
}
