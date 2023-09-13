package sg.edu.nus.iss.movie_maven_backend.Services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.movie_maven_backend.Model.Movies;
import sg.edu.nus.iss.movie_maven_backend.Model.ReviewedMovies;
import sg.edu.nus.iss.movie_maven_backend.Model.Reviews;
import sg.edu.nus.iss.movie_maven_backend.Repositories.PhotoRepository;
import sg.edu.nus.iss.movie_maven_backend.Repositories.ReviewRepository;

@Service
public class ReviewService {
    
    @Autowired
    PhotoRepository pRepo;

    @Autowired
    ReviewRepository ReviewRepo;

    public String savePhotoToS3(MultipartFile photo) throws IOException{
        String uuid = UUID.randomUUID().toString().substring(0,8);
        if (photo == null){
            return uuid;
        }else{
            String reviewUUID = pRepo.uploadFile(photo, uuid);
            return reviewUUID;
        }
    }

    public Boolean createReview(Reviews r){
        return ReviewRepo.createReview(r);
    }

    public Boolean createMovie(Movies m){
        return ReviewRepo.createMovie(m);
    }

    public List<ReviewedMovies> findAllReviewedMovies(){
        return ReviewRepo.findAllReviewedMovies();
    }

    // public List<Movies> findAllMovies(){
    //     return ReviewRepo.findAllMovies();
    // }

}
