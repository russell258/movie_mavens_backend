package sg.edu.nus.iss.movie_maven_backend.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.movie_maven_backend.Model.Contact;
import sg.edu.nus.iss.movie_maven_backend.Services.ContactService;
import sg.edu.nus.iss.movie_maven_backend.Services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController()
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class ContactController {
    
    @Autowired
    private EmailService eSvc;

    @Autowired
    private ContactService cSvc;

    @PostMapping(path="/contact", consumes="application/json")
    public ResponseEntity<String> postContactFormToSQL(
        @RequestBody Contact c
    ){

        eSvc.sendEmail(c.getEmail(),"Movie Maven | We have received your message", "Hi "+ c.getFullName() + ", we have received your message and will get back to you promptly.\nThank you!");

        JsonObject outgoingJo;
        if (cSvc.createContact(c)){
            outgoingJo = Json.createObjectBuilder()
                                .add("email",c.getEmail())
                                .build();
        }else{
            outgoingJo = Json.createObjectBuilder()
                                .add("error","Error encountered while saving message")
                                .build();
        }
        return ResponseEntity.ok(outgoingJo.toString());
    }

}
