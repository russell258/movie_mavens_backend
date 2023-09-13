package sg.edu.nus.iss.movie_maven_backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String user_id;
    private String fullName;
    private String email;
    private String message;
}
