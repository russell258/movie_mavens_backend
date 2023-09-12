package sg.edu.nus.iss.movie_maven_backend.RestController;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.movie_maven_backend.Services.MovieService;
import sg.edu.nus.iss.movie_maven_backend.Services.ReviewService;

@RestController()
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class MovieRestController {
    
    @Autowired
    MovieService mSvc;

    @Autowired
    ReviewService rSvc;
    
    @GetMapping({"/home/{firstPath}/{secondPath}/{thirdPath}", "/home/{firstPath}/{secondPath}"})
    @CrossOrigin()
    public ResponseEntity<String> getMovies(@PathVariable String firstPath, @PathVariable String secondPath, @PathVariable(required=false) String thirdPath){
        return ResponseEntity.ok(mSvc.getMovies(firstPath, secondPath, thirdPath));
    }

    @PostMapping(path = "/receivereview",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postFormToSQL(
        @RequestPart String movieId,
        @RequestPart String title,
        @RequestPart Date releaseDate,
        @RequestPart double rating,
        @RequestPart double average_rating,
        @RequestPart String review,
        @RequestPart MultipartFile photo
    ) throws IOException{
        System.out.println(movieId + " "+ title + " " + releaseDate.toString() + " "+ Double.toString(rating)+ " "+Double.toString(average_rating)+" "+review);
            
        String uuid = rSvc.savePhotoToS3(photo);

        //if kena date error, might be sql.date
        Date date = new Date();

        JsonObject reviewJsonObject = Json.createObjectBuilder()
                            .add("review_id", uuid)
                            .add("rating",rating)
                            .add("review",review)
                            .add("image_url", "https://csfday37.sgp1.cdn.digitaloceanspaces.com/"+uuid)
                            .add("posted_on", date.getTime())
                            .build();
        
        

        return ResponseEntity.ok(reviewJsonObject.toString());
    }


}
