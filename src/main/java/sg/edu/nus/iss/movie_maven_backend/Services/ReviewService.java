package sg.edu.nus.iss.movie_maven_backend.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.iss.movie_maven_backend.Repositories.PhotoRepository;

@Service
public class ReviewService {
    
    @Autowired
    PhotoRepository pRepo;

    public String savePhotoToS3(MultipartFile photo) throws IOException{
        return pRepo.uploadFile(photo);
    }

    


}
