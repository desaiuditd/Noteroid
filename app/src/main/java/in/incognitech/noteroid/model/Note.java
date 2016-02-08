package in.incognitech.noteroid.model;

/**
 * Created by udit on 06/02/16.
 */
public class Note {

    private int id;
    private String photoPath;
    private String caption;

    public Note(int id, String photoPath, String caption) {
        this.id = id;
        this.photoPath = photoPath;
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
