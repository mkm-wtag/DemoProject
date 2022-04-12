package cinema.user;

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
    ResponseObject updateUser(String userId, EditUser editUser, HttpSession session);
    User getUserByEmail(String email);
    ResponseObject getLoggedIn(@Valid @RequestBody Login login,HttpSession session);
    ResponseObject logout(HttpSession session);
}
