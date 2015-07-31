package vk.music.course;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vk.music.course.Entity.Audio;
import vk.music.course.config.AppConfig;
import vk.music.course.retrofit.VkService;
import vk.music.course.service.DownloadService;

import java.util.LinkedList;


public class AppLauncher {
    public static final Logger logger = LoggerFactory.getLogger(AppLauncher.class);
    public static ApplicationContext context;
    public static final LinkedList<Audio> linkedList = new LinkedList<>();
    public static DownloadService downloadService;
    public static String token;
    public static String id;



    public static void main(String[] args) throws InterruptedException {
        init(args);
        VkService vkService = context.getBean(VkService.class);
        downloadService = context.getBean(DownloadService.class);
        final Gson gson = context.getBean(Gson.class);




        vkService.getAudios(token, id, "5.34")
                .forEach(jsonObject -> {
                    final JsonObject response = jsonObject.get("response").getAsJsonObject();
                    final int count = response.get("count").getAsInt();
                    System.out.println(count);
                    response.getAsJsonArray("items")
                            .forEach(jsonElement -> {
                                final Audio audio = gson.fromJson(jsonElement.toString(), Audio.class);
                                if (audio != null)
                                    downloadService.addAudio(audio);
                                else
                                    logger.error("audio == null| json -> {}", jsonElement);
                            });
                });
    }

    public static void init(String[] args){
        if(args.length != 4) {
            System.out.println("Need two arguments token(-t) and idVk(-id)");
            throw new IllegalArgumentException("Need two arguments token(-t) and idVk(-id)");
        }
        Options opt = new Options();
        Option tokenOption = new Option("t", true, "token of vk");
        tokenOption.setLongOpt("token");
        tokenOption.setValueSeparator(' ');
        Option idOption = new Option("id", true, "id user");
        idOption.setValueSeparator(' ');
        opt.addOption(tokenOption);
        opt.addOption(idOption);
        try {
            CommandLine cmd = new DefaultParser().parse(opt, args);

            token = cmd.getOptionValue("t");
            id = cmd.getOptionValue("id");
        } catch (ParseException e) {
            logger.error("parse arguments" , e);
            throw new RuntimeException("parse arguments", e);
            //e.printStackTrace();
        }


        context = new AnnotationConfigApplicationContext(AppConfig.class);


    }
}
