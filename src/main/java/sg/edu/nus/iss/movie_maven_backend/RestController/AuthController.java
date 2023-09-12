package sg.edu.nus.iss.movie_maven_backend.RestController;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.movie_maven_backend.Model.Users;
import sg.edu.nus.iss.movie_maven_backend.Services.EmailService;
import sg.edu.nus.iss.movie_maven_backend.Services.UserService;
import sg.edu.nus.iss.movie_maven_backend.config.UserAuthProvider;
import sg.edu.nus.iss.movie_maven_backend.records.LoginDto;
import sg.edu.nus.iss.movie_maven_backend.records.RegisterDto;
import sg.edu.nus.iss.movie_maven_backend.records.UserDto;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailSvc;

    @Autowired
    private UserAuthProvider userAuthProvider;
    
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {
        System.out.println("IS THIS BEING CALLED????");
        UserDto user = userService.login(loginDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) {
        Users user = userService.register(registerDto);
        System.out.println("user model:" + user);
        UserDto createdUser = new UserDto();
        createdUser.setToken(userAuthProvider.createToken(new UserDto(user.getId(), user.getUsername(), "")));
        createdUser.setUsername(user.getUsername());
        System.out.println("EVERYTHING GOING OKAY UNTIL EMAIL");
        emailSvc.sendEmail(user.getEmail(), "Movie Maven | Account Created" , "Hi " + user.getUsername() +", your account has been created successfully! \n\nHave a good time here.\nFrom the Movie Maven Team");
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

}
