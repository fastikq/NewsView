package dudukov.andrii.newsview.model.api;

import dudukov.andrii.newsview.model.api.responses.ArticlesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApplicationAPI {

    @GET("v2/top-headlines")
    Observable<ArticlesResponse> getArticles(@Query("country") String country,
                                                    @Query("category") String category,
                                                    @Query("apiKey") String apiKey);

}
