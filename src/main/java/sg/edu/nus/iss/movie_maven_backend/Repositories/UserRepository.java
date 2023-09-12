package sg.edu.nus.iss.movie_maven_backend.Repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.movie_maven_backend.Model.Users;

@Repository
public class UserRepository {
    private String SQL_FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?;";
    private String SQL_INSERT_NEW_USER = """
                INSERT INTO users (user_id, username, email, password)
                VALUES (?,?,?,?);
            """;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Optional<Users> findUserByUsername(String username) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_FIND_BY_USERNAME, username);
        if (rs.next()) {
            Users user = new Users();
            user.setId(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            
            return Optional.of(user);
        }
        else {
            return Optional.empty();
        }

    }

    public Integer insertNewUser(Users user) {
        return jdbcTemplate.update(SQL_INSERT_NEW_USER, 
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword());
    }
}
