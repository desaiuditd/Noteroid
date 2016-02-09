package in.incognitech.noteroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import in.incognitech.noteroid.db.NoteDbHelper;
import in.incognitech.noteroid.util.BitmapOptimizer;
import in.incognitech.noteroid.util.MenuController;

public class SaveNoteActivity extends AppCompatActivity {


    private String imagePath;
    private String imageCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imagePath = extras.getString("photoFilePath");
            if (imagePath != null) {
                ImageView imageView = (ImageView) findViewById(R.id.note_photo);
                imageView.setImageBitmap(BitmapOptimizer.decodeSampledBitmapFromFile(imagePath, 600, 600));
            } else {
                Toast.makeText(getApplicationContext(),"Could not save the image. Please try again.",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        }

        Button btnSaveNote = (Button) findViewById(R.id.btn_save_note);
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText captionView = (EditText) findViewById(R.id.note_caption);
                imageCaption = captionView.getText().toString();

                SQLiteDatabase db = new NoteDbHelper(SaveNoteActivity.this).getWritableDatabase();
                ContentValues newValues = new ContentValues();
                newValues.put(NoteDbHelper.PHOTO_PATH_COLUMN, imagePath);
                newValues.put(NoteDbHelper.CAPTION_COLUMN, imageCaption);
                db.insert(NoteDbHelper.DATABASE_TABLE, null, newValues);

                Toast.makeText(getApplicationContext(),"Note saved successfully!",Toast.LENGTH_LONG).show();
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
