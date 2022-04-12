package cinema.user;

import cinema.exception.InvalidRequestBodyException;
import cinema.exception.ResourceNotFoundException;
import cinema.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestBodyException(bindingResult);
        }
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getUsers();
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new ResourceNotFoundException("No User Found with userId : "+userId);
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("{userid}")
    public ResponseObject updateUser(@PathVariable("userid") String userId, @Valid @RequestBody EditUser editUser , BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestBodyException(bindingResult);
        }
        return userService.updateUser(userId, editUser, session);
    }

    @DeleteMapping("{userid}")
    public ResponseObject deleteUser(@PathVariable("userid") String userid, HttpSession session) {
        return userService.deleteUser(userid,session);
    }




}
