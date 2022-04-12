package cinema.user;

import org.hibernate.validator.constraints.NotEmpty;

public class EditUser {
    @NotEmpty
    private String newName;
    @NotEmpty
    private String newPassword;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
