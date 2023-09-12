package sg.edu.nus.iss.movie_maven_backend.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movies {
    private String movie_id;
    private String title;
    private Date release_date;
    private String overview;
    private float average_rating;
    private String review_id;
}
