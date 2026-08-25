package data;



public class LoginData {

    public static LoginCredentials validUser() {
        return new LoginCredentials(
                "Admin",
                "admin123"
        );
    }

    public static LoginCredentials invalidUser() {
        return new LoginCredentials(
                "Admin",
                "123"
        );
    }
    public record LoginCredentials(
            String username,
            String password
    ) {}

}
