package in.incognitech.noteroid.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import in.incognitech.noteroid.R;

/**
 * Created by udit on 30/01/16.
 */
public class MenuController {

    public MenuController() {}

    public static boolean handleMenuItemSelection( boolean returnFlag, MenuItem item, Activity parent ) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_uninstall:
                Uri packageURI = Uri.parse("package:in.incognitech.noteroid");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                parent.startActivity(uninstallIntent);
                returnFlag = true;
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(parent);
                returnFlag = true;
        }

        return returnFlag;
    }
}
