package sg.edu.nus.iss.movie_maven_backend.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.movie_maven_backend.Model.Movies;
import sg.edu.nus.iss.movie_maven_backend.Model.ReviewedMovies;
import sg.edu.nus.iss.movie_maven_backend.Model.Reviews;

@Repository
public class ReviewRepository {
    
    @Autowired
    JdbcTemplate jdbc;

    private final String CREATE_REVIEW_SQL = "insert into reviews values (?,?,?,?,?,?)";
    private final String CREATE_MOVIE_SQL = "insert into movies values (null, ?,?,?,?,?,?)"; 
    private final String SELECT_ALL_REVIEWED_MOVIES_SQL= "select * from reviews inner join movies on reviews.review_id=movies.review_id left join users on reviews.user_id=users.user_id";
    private final String DELETE_REVIEWED_MOVIE_BY_REVIEW_ID = "delete from movies where review_id = ?";
    private final String DELETE_REVIEW_BY_REVIEW_ID = "delete from reviews where review_id = ?";
    private final String SELECT_REVIEWED_MOVIES_BY_USER_ID = "select * from reviews inner join movies on reviews.review_id = movies.review_id left join users on reviews.user_id = users.user_id where users.user_id=?";

    public Boolean createReview(Reviews r){
        Integer iResult = jdbc.update(CREATE_REVIEW_SQL,r.getReview_id(),r.getRating(),r.getReview(),r.getImage_url(),r.getPosted_on(),r.getUser_id());
        return iResult>0 ? true : false;
    }

    public Boolean createMovie(Movies m){
        Integer iResult = jdbc.update(CREATE_MOVIE_SQL, m.getMovie_id(),m.getTitle(),m.getRelease_date(),m.getOverview(),m.getAverage_rating(),m.getReview_id());
        return iResult>0? true : false;
    }

    public List<ReviewedMovies> findAllReviewedMovies(){
        return jdbc.query(SELECT_ALL_REVIEWED_MOVIES_SQL, BeanPropertyRowMapper.newInstance(ReviewedMovies.class));
    }

    public List<ReviewedMovies> findReviewedMoviesByUser(String user_id){
        return jdbc.query(SELECT_REVIEWED_MOVIES_BY_USER_ID,BeanPropertyRowMapper.newInstance(ReviewedMovies.class),user_id);
    }
    
    public Boolean deleteReviewedMovie(String review_id){
        Integer rowsDeleted = jdbc.update(DELETE_REVIEWED_MOVIE_BY_REVIEW_ID,review_id);
        return rowsDeleted > 0 ? true : false;
    }

    public Boolean deleteReview(String review_id){
        Integer rowsDeleted = jdbc.update(DELETE_REVIEW_BY_REVIEW_ID,review_id);
        return rowsDeleted > 0 ? true : false;
    }

}
