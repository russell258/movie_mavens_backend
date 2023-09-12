package sg.edu.nus.iss.movie_maven_backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private String id;
    private String username;
    private String password;
    private String email;
}
