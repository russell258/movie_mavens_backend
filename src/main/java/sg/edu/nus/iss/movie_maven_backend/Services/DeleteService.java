package sg.edu.nus.iss.movie_maven_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.movie_maven_backend.Repositories.ReviewRepository;

@Service
public class DeleteService {
    
    @Autowired
    ReviewRepository ReviewRepo;

    @Transactional(rollbackFor = DeleteException.class)
    public Boolean deleteReviewMovieRecordTransaction(String review_id) throws DeleteException{
        try{
            Boolean firstDelete = ReviewRepo.deleteReviewedMovie(review_id);
            if (firstDelete == false){
                throw new DeleteException("Record not found in movies table");
            }
            ReviewRepo.deleteReview(review_id);
            }catch (DataAccessException ex){
                throw new DeleteException(ex.getMessage());
            }
            return true;
        }        
    }
    


