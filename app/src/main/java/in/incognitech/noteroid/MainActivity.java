package in.incognitech.noteroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.incognitech.noteroid.db.NoteDbHelper;
import in.incognitech.noteroid.model.Note;
import in.incognitech.noteroid.model.NoteAdapter;
import in.incognitech.noteroid.util.MenuController;

public class MainActivity extends AppCompatActivity {

    private File mPhotoFile;

    static final int REQUEST_IMAGE_CAPTURE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Note> noteList = new ArrayList<Note>();
        SQLiteDatabase db = new NoteDbHelper(this).getWritableDatabase();
        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {NoteDbHelper.ID_COLUMN, NoteDbHelper.PHOTO_PATH_COLUMN, NoteDbHelper.CAPTION_COLUMN};
        Cursor cursor = db.query(NoteDbHelper.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        int count = 0;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String photo_path = cursor.getString(1);
            String caption = cursor.getString(2);
            noteList.add(count++, new Note(id, photo_path, caption));
        }

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new NoteAdapter(this, R.layout.note_row, noteList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), NoteActivity.class);
                i.putExtra("note_id", position);
                startActivity(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

//                     Create the File where the photo should go
                    try {
                        mPhotoFile = createImageFile();
                    } catch (IOException e) {
//                         Error occurred while creating the File
                        System.out.println(e);
                    }
                    System.out.println(mPhotoFile);
//                     Continue only if the File was successfully created
                    if (mPhotoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                        setResult(RESULT_OK, takePictureIntent);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            this.galleryAddPic();

            Intent saveNoteIntent = new Intent(getApplicationContext(), SaveNoteActivity.class);
            saveNoteIntent.putExtra("photoFilePath", mPhotoFile.getAbsolutePath());
            startActivity(saveNoteIntent);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(mPhotoFile);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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
