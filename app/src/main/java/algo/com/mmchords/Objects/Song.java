package algo.com.mmchords.Objects;

public class Song {
    String id;
    String title;
    String singer;

    public Song(String id, String title, String singer) {
        this.id = id;
        this.title = title;
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }
}
