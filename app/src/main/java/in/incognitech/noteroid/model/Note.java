package in.incognitech.noteroid.model;

/**
 * Created by udit on 06/02/16.
 */
public class Note {

    private String photoPath;
    private String caption;

    public Note(String photoPath, String caption) {
        this.photoPath = photoPath;
        this.caption = caption;
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
