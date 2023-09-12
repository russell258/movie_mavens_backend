package sg.edu.nus.iss.movie_maven_backend.records;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String username;
    private String token;
    //set token in localStorage, send user_id to other pages where u want to use it
}
