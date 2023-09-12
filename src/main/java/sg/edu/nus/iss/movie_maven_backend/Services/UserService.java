package sg.edu.nus.iss.movie_maven_backend.Services;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sg.edu.nus.iss.movie_maven_backend.Model.Users;
import sg.edu.nus.iss.movie_maven_backend.Repositories.UserRepository;
import sg.edu.nus.iss.movie_maven_backend.exceptions.AppException;
import sg.edu.nus.iss.movie_maven_backend.records.LoginDto;
import sg.edu.nus.iss.movie_maven_backend.records.RegisterDto;
import sg.edu.nus.iss.movie_maven_backend.records.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {
   
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto login(LoginDto loginDto) {
        Users user = userRepository.findUserByUsername(loginDto.username())
                                    .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        
        if (passwordEncoder.matches(CharBuffer.wrap(loginDto.password()),
            user.getPassword())) {
                return new UserDto(user.getId(), user.getUsername(), "");
        } 
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public Users register(RegisterDto registerDto) {
        Optional<Users> optionalUser = userRepository.findUserByUsername(registerDto.username());

        // You were implement the above repo's method
        if (optionalUser.isPresent()) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        Users user = new Users(registerDto.id(), registerDto.username(), registerDto.password().toString(), registerDto.email());
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.password())));

        if (userRepository.insertNewUser(user) > 0) {
            return user;
        }
        else {
            throw new AppException("User " + user.getUsername() + " was not successfully created", HttpStatus.BAD_REQUEST);
        }
    }
}
