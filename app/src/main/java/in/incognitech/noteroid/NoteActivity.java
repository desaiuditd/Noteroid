package in.incognitech.noteroid;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import in.incognitech.noteroid.model.Note;
import in.incognitech.noteroid.model.NoteAdapter;
import in.incognitech.noteroid.util.MenuController;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int noteID = extras.getInt("note_id");
            Note note = NoteAdapter.getNoteList().get(noteID);
            TextView title = (TextView) findViewById(R.id.view_note_caption);
            title.setText(note.getCaption());
            ImageView imageView = (ImageView) findViewById(R.id.view_note_photo);
            imageView.setImageBitmap(new BitmapFactory().decodeFile(note.getPhotoPath()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuController.handleMenuItemSelection(super.onOptionsItemSelected(item), item, this);
    }

}
