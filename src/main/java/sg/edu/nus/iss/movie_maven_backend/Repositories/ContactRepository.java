package sg.edu.nus.iss.movie_maven_backend.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.movie_maven_backend.Model.Contact;

@Repository
public class ContactRepository {
    
    @Autowired
    JdbcTemplate jdbc;

    private final String CREATE_CONTACT_SQL = "insert into contacts values (null, ?,?,?,?)";

    public Boolean createContact(Contact c){
        Integer iResult = jdbc.update(CREATE_CONTACT_SQL,c.getUser_id(),c.getFullName(),c.getEmail(),c.getMessage());
        return iResult>0 ? true : false;
    }

}
