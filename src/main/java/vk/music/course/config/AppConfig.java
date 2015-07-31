package vk.music.course.config;

import com.google.gson.Gson;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import vk.music.course.retrofit.VkService;

/**
 * Created by Alex on 10.07.2015.
 */
@Configuration
@ComponentScan("vk.music.course")
public class AppConfig {
    @Autowired
    Environment environment;

    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean
    public RestAdapter restAdapter(){
        return new RestAdapter.Builder()
                .setEndpoint("https://api.vk.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson()))
                .setErrorHandler(cause -> {
                    Response r = cause.getResponse();
                    if (r != null && r.getStatus() == 401)
                        return new RuntimeException(cause);
                    return cause;
                })
                .build();
    }

    @Bean
    public VkService vkService(){
        return restAdapter().create(VkService.class);
    }





}
