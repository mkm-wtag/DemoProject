package cinema.entity.user;

import cinema.utility.ResponseObject;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(String userId);

    void saveUser(User user);

    ResponseObject deleteUser(String userId, HttpSession session);

    User updateUser(String userId, EditUserDTO editUserDTO, HttpSession session);

    User getUserByEmail(String email);

    ResponseObject getLoggedIn(@Valid @RequestBody LoginDTO login, HttpSession session);

    ResponseObject logout(HttpSession session);
}
