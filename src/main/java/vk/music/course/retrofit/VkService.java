package vk.music.course.retrofit;

import com.google.gson.JsonObject;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;



/**
 * Created by Alex on 10.07.2015.
 */
public interface VkService {
    @GET("/method/audio.get")
    Observable<JsonObject> getAudios(@Query("access_token") String accessToken, @Query("owner_id") String owner, @Query("v") String version);

}
