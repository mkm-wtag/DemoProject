package cinema.entity.user;

import cinema.exception.AccessDeniedException;
import cinema.exception.CustomerLoginException;
import cinema.exception.CustomerLogoutException;
import cinema.exception.ResourceNotFoundException;
import cinema.utility.AuthorizationUtility;
import cinema.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    @Transactional
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        User oldUser = userDAO.getUserByEmail(user.getUserEmail());
        if (oldUser == null) {
            userDAO.insert(user);
        } else {
            throw new DataIntegrityViolationException("A User with " + user.getUserEmail() + " already exists");
        }
    }

    @Override
    @Transactional
    public User getUser(String userId) {
        return userDAO.getByUserId(userId);
    }

    @Override
    @Transactional
    public ResponseObject deleteUser(String userId, HttpSession session) {

        if (!AuthorizationUtility.isCustomerLoggedIn(session)) {
            throw new CustomerLoginException("You are not logged in");
        }

        User user = userDAO.getByUserId(userId);

        if (user == null) {
            throw new ResourceNotFoundException("No User Found with userId : " + userId);
        }

        if (!AuthorizationUtility.isCustomerAuthorized(user.getUserEmail(), session)) {
            throw new AccessDeniedException("You are not supposed to do this.");
        }
        userDAO.delete(user);
        session.invalidate();
        return new ResponseObject("Deleted user : " + user.getUserName());
    }

    @Override
    @Transactional
    public User updateUser(String userId, EditUserDTO editUserDTO, HttpSession session) {
        if (!AuthorizationUtility.isCustomerLoggedIn(session)) {
            throw new CustomerLoginException("You are not logged in.");
        }
        User oldUser = userDAO.getByUserId(userId);
        if (oldUser == null) {
            throw new ResourceNotFoundException("No User Found with userId : " + userId);
        }
        if (!AuthorizationUtility.isCustomerAuthorized(oldUser.getUserEmail(), session)) {
            throw new AccessDeniedException("You are not supposed to do this.");
        }
        oldUser.setUserName(editUserDTO.getUserName());
        oldUser.setUserPassword(editUserDTO.getUserPassword());
        userDAO.update(oldUser);
        return oldUser;
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("No User Found with email : " + email);
        }
        return user;
    }

    @Override
    @Transactional
    public ResponseObject getLoggedIn(LoginDTO login, HttpSession session) {
        User user = userDAO.getUserByEmail(login.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("No User Found with email : " + login.getEmail());
        }
        if (AuthorizationUtility.getLoginToken(session) != null) {
            throw new CustomerLoginException("please log out first");
        }
        if (user.getUserPassword().equals(login.getPassword())) {
            AuthorizationUtility.setLoginToken(session, user);
            return new ResponseObject("Successfully logged in");
        }


        throw new CustomerLoginException("Email or password error!");
    }

    @Override
    @Transactional
    public ResponseObject logout(HttpSession session) {
        if (session.getAttribute("token") == null) {
            throw new CustomerLogoutException("You are not logged in.");
        } else {
            session.invalidate();
            return new ResponseObject("Successfully Logged Out.");
        }
    }
}
