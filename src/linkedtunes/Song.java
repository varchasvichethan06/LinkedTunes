package linkedtunes;

public class Song {
    private int songId;
    private String songName;
    private String songDuration;

    public Song(int songId, String songName, String songDuration) {
        this.songId = songId;
        this.songName = songName;
        this.songDuration = songDuration;
    }

    public int getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongDuration() {
        return songDuration;
    }
}
