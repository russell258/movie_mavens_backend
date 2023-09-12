package sg.edu.nus.iss.movie_maven_backend.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieService {
    
    private RestTemplate rest = new RestTemplate();

    @Value("${tmdb.api.key}")
    private String TMDB_API_KEY;

    private String TMDB_URL = "https://api.themoviedb.org/3";

    // "https://api.themoviedb.org/3/movie/upcoming?api_key="
    // "https://api.themoviedb.org/3/trending/movie/week?api_key="
    // "https://api.themoviedb.org/3/genre/movie/list?api_key="

    public String getMovies(@PathVariable String firstPath, @PathVariable String secondPath, @PathVariable(required=false) String thirdPath){
        String url = UriComponentsBuilder.fromUriString(TMDB_URL)
                                        .pathSegment(firstPath,secondPath,thirdPath)
                                        .queryParam("api_key", TMDB_API_KEY)
                                        .toUriString();
        System.out.println(TMDB_API_KEY);
        return rest.getForEntity(url, String.class).getBody();
    }

}
