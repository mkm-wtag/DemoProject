package cinema.user;

import cinema.exception.InvalidRequestBodyException;
import cinema.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/session")
public class UserAuthenticationController {
    private final UserService userService;

    @Autowired
    public UserAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseObject userLogin(@Valid @RequestBody Login login, BindingResult bindingResult, HttpSession session){
        if (bindingResult.hasErrors()){
            throw new InvalidRequestBodyException(bindingResult);
        }
        return userService.getLoggedIn(login,session);
    }

    @DeleteMapping
    public ResponseObject userLogOut(HttpSession session){
        return userService.logout(session);
    }

}
