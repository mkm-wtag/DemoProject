package cinema.utility;

import cinema.entity.user.User;

import javax.servlet.http.HttpSession;

public class AuthorizationUtility {

    public static String getLoginToken(HttpSession session){
        return (String) session.getAttribute("token");
    }

    public static void setLoginToken(HttpSession session, User user){
        String token = user.getUserEmail();
        session.setAttribute("token", token);
    }

    public static boolean isCustomerAuthorized(String email, HttpSession session){

        String token = email;

        return token.equals(getLoginToken(session));
    }

    public static boolean isCustomerLoggedIn(HttpSession session){
        return getLoginToken(session) != null;
    }

}