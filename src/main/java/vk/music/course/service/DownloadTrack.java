package vk.music.course.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vk.music.course.Entity.Audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Alex on 10.07.2015.
 */
public class DownloadTrack implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(DownloadTrack.class);

    private final Audio audio;
    private final File folder;

    public DownloadTrack(Audio audio, File folder) {
        this.audio = audio;
        this.folder = folder;
    }


    @Override
    public void run() {
        final String s = fixWndowsFileName(audio.getArtist().trim() + "-" + audio.getTitle().trim() + "::" + audio.getDuration() + "s.mp3");

        File file = new File(folder, s);
        if(!file.exists()){
            try {
                FileUtils.copyURLToFile(new URL(audio.getUrl()), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else logger.info("трек: {} уже скачан", s);
    }

    private static String fixWndowsFileName(String pathname) {
        String[] forbiddenSymbols = new String[] {"<", ">", ":", "\"", "/", "\\", "|", "?", "*"};
        String result = pathname;
        for (String forbiddenSymbol: forbiddenSymbols) {
            result = StringUtils.replace(result, forbiddenSymbol, "");
        }
        // амперсанд в названиях передаётся как '& amp', приводим его к читаемому виду
        return StringEscapeUtils.unescapeXml(result);
    }
}
