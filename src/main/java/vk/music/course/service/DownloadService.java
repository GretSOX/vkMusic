package vk.music.course.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vk.music.course.Entity.Audio;

import java.io.File;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alex on 10.07.2015.
 */
@Component
public class DownloadService {

    public static final Logger logger = LoggerFactory.getLogger(DownloadService.class);
    private ExecutorService execS = Executors.newFixedThreadPool(5);
    private File musicFolder = null;
    private static final String PATH_MUSIC_FOLDER = "./vkMusic/";
    private final String pathMusic;



    public DownloadService() {
        this(PATH_MUSIC_FOLDER);
    }

    public DownloadService(String pathMusicFolder) {
        pathMusic = pathMusicFolder;
        musicFolder = new File(pathMusicFolder);
        if(!musicFolder.exists()){
            musicFolder.mkdirs();
        }
    }

    public synchronized void addAudio(Audio a){
        execS.submit(new DownloadTrack(a, musicFolder));
    }











}
