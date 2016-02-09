package in.incognitech.noteroid;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import in.incognitech.noteroid.db.NoteDbHelper;
import in.incognitech.noteroid.model.Note;
import in.incognitech.noteroid.model.NoteAdapter;
import in.incognitech.noteroid.util.BitmapOptimizer;
import in.incognitech.noteroid.util.MenuController;

public class NoteActivity extends AppCompatActivity {

    private int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteID = extras.getInt("note_id");
            Note note = NoteAdapter.getNoteList().get(noteID);
            TextView title = (TextView) findViewById(R.id.view_note_caption);
            title.setText(note.getCaption());
            ImageView imageView = (ImageView) findViewById(R.id.view_note_photo);
            imageView.setImageBitmap(BitmapOptimizer.decodeSampledBitmapFromFile(note.getPhotoPath(), 600, 600));
        }

        Button deleteNote = (Button) findViewById(R.id.delete_note);
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = NoteAdapter.getNoteList().get(noteID);
                NoteAdapter.getNoteList().remove(noteID);
                File photo = new File(note.getPhotoPath());
                photo.delete();
                NoteActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photo)));

                SQLiteDatabase db = new NoteDbHelper(NoteActivity.this).getWritableDatabase();
                String whereClause = NoteDbHelper.ID_COLUMN + "=?";
                String[] whereArgs = {String.valueOf(note.getId())};
                db.delete(NoteDbHelper.DATABASE_TABLE, whereClause, whereArgs);

                Toast.makeText(getApplicationContext(), "Note deleted successfully!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
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
