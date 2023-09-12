package sg.edu.nus.iss.movie_maven_backend.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.movie_maven_backend.Model.Movies;
import sg.edu.nus.iss.movie_maven_backend.Model.Reviews;

@Repository
public class ReviewRepository {
    
    @Autowired
    JdbcTemplate jdbc;

    private final String CREATE_REVIEW_SQL = "insert into reviews values (?,?,?,?,?,?)";
    private final String CREATE_MOVIE_SQL = "insert into movies values (?,?,?,?,?,?)"; 

    public Boolean createReview(Reviews r){
        Integer iResult = jdbc.update(CREATE_REVIEW_SQL,r.getReview_id(),r.getRating(),r.getReview(),r.getImage_url(),r.getPosted_on(),r.getUser_id());
        return iResult>0 ? true : false;
    }

    public Boolean createMovie(Movies m){
        Integer iResult = jdbc.update(CREATE_MOVIE_SQL, m.getMovie_id(),m.getTitle(),m.getRelease_date(),m.getOverview(),m.getAverage_rating(),m.getReview_id());
        return iResult>0? true : false;
    }

}
