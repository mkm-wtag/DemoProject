package cinema.entity.user;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getByUserId(String userId);
    void insert(User user);
    void delete(User user);
    void update(User user);
    User getUserByEmail(String email);
}
