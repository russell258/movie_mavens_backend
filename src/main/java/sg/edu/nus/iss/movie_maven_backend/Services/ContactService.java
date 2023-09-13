package sg.edu.nus.iss.movie_maven_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.movie_maven_backend.Model.Contact;
import sg.edu.nus.iss.movie_maven_backend.Repositories.ContactRepository;

@Service
public class ContactService {
    
    @Autowired
    ContactRepository cRepo;

    public Boolean createContact(Contact c){
        return cRepo.createContact(c);
    }

}
