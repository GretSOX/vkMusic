package vk.music.course.Entity;

/**
 * Created by Alex on 10.07.2015.
 */
public class Audio {
    private Long id;
    private String artist;
    private String title;
    private String url;
    private long duration;

    public Long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public long getDuration() {
        return duration;
    }
}
