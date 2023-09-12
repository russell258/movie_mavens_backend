package sg.edu.nus.iss.movie_maven_backend.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {
    private String review_id;
    private double rating;
    private String review;
    private String image_url;
    private Date posted_on;
    private String user_id;
}
