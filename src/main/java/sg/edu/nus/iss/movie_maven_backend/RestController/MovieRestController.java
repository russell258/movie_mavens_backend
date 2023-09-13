package sg.edu.nus.iss.movie_maven_backend.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.movie_maven_backend.Model.Movies;
import sg.edu.nus.iss.movie_maven_backend.Model.ReviewedMovies;
import sg.edu.nus.iss.movie_maven_backend.Model.Reviews;
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
    public ResponseEntity<String> getMovies(@PathVariable String firstPath, @PathVariable String secondPath, @PathVariable(required=false) String thirdPath){
        return ResponseEntity.ok(mSvc.getMovies(firstPath, secondPath, thirdPath));
    }

    @GetMapping(path="/reviews")
    public ResponseEntity<List<ReviewedMovies>> getReviewedMovies(){
        List<ReviewedMovies> allReviewedMovies = rSvc.findAllReviewedMovies();
        return ResponseEntity.ok(allReviewedMovies);
    }

    // @GetMapping(path="/movies")
    // public ResponseEntity<List<Movies>> getMovies(){
    //     List<Movies> allMovies = rSvc.findAllMovies();
    //     return ResponseEntity.ok(allMovies);
    // }

    @PostMapping(path = "/receivereview",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postFormToSQL(
        @RequestPart String userId,
        @RequestPart String movieId,
        @RequestPart String title,
        @RequestPart String releaseDate,
        @RequestPart String overview,
        @RequestPart String rating,
        @RequestPart String average_rating,
        @RequestPart String review,
        @RequestPart(required=false) MultipartFile photo
    ) throws IOException, ParseException{
        System.out.println(movieId + " "+ title + " " + releaseDate.toString() + " "+ rating+ " "+ average_rating+" "+review);
        //1. upload photo and get review uuid from S3
        String reviewUUID = rSvc.savePhotoToS3(photo);

        //if kena date error, might be sql.date
        Date date = new Date();
        //2. update reviews table
        Reviews reviewPOJO = new Reviews();
        reviewPOJO.setReview_id(reviewUUID);
        reviewPOJO.setRating(Double.parseDouble(rating));
        reviewPOJO.setReview(review);
        reviewPOJO.setImage_url("https://csfday37.sgp1.cdn.digitaloceanspaces.com/"+reviewUUID);
        reviewPOJO.setPosted_on(date);
        reviewPOJO.setUser_id(userId);
        
        //3. update movies table
        DateFormat releaseD = new SimpleDateFormat("yyyy-MM-dd");
        Movies moviePOJO = new Movies();
        moviePOJO.setMovie_id(movieId);
        moviePOJO.setTitle(title);
        moviePOJO.setRelease_date(releaseD.parse(releaseDate));
        moviePOJO.setOverview(overview);
        moviePOJO.setAverage_rating(Double.parseDouble(average_rating));
        moviePOJO.setReview_id(reviewUUID);

        JsonObject outgoingJo;

        if (rSvc.createReview(reviewPOJO) && rSvc.createMovie(moviePOJO)){
            outgoingJo = Json.createObjectBuilder()
                            .add("reviewId",reviewUUID)
                            .build();
        }else{
            outgoingJo = Json.createObjectBuilder()
                            .add("error", "There was an error creating your review")
                            .build();
        }
        
        return ResponseEntity.ok(outgoingJo.toString());
    }


}
