package cinema.interceptor;

import cinema.utility.AuthorizationUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (! AuthorizationUtility.isCustomerLoggedIn(session)){
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return false;
        }

        if (! AuthorizationUtility.isCustomerAuthorized(request.getParameter("email"), session)){
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return false;
        }

        return true;
    }
}