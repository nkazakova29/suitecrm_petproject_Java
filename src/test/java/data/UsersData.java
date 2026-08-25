package data;

public class UsersData {
    public static UsersDataCreation userForCreating() {
        return new UsersDataCreation(
                "Mefodiy",
                "Netu",
                "Buslaev"
        );
    }
    public record UsersDataCreation(
   String firstName,
   String middleName,
   String lastName)
    {}

    }

