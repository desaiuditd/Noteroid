package in.incognitech.noteroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import in.incognitech.noteroid.util.MenuController;

public class SaveNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imagePath = extras.getString("photoFilePath");
            if (imagePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                ImageView imageView = (ImageView) findViewById(R.id.note_photo);
                imageView.setImageBitmap(bitmap);
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
                Toast.makeText(getApplicationContext(),"Button clicked.",Toast.LENGTH_LONG).show();
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
