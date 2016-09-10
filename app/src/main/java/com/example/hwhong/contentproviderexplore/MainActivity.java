package com.example.hwhong.contentproviderexplore;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView);

        //NUMBER 1
        /*
        ContentResolver contentResolver = getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.row,
                cursor,
                new String[]{
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.Contacts.Photo.PHOTO},
                new int[] {R.id.name, R.id.number, R.id.photo},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER );

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(view.getId() == R.id.photo) {

                    try {
                        InputStream stream = ContactsContract.Contacts.openContactPhotoInputStream(
                                getContentResolver(),
                                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                        if (stream != null) {
                            Bitmap photo = BitmapFactory.decodeStream(stream);
                            ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(photo, 64, 64, false));
                            stream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                return false;
            }
        });

        listview.setAdapter(adapter);
        */

        showContacts();
    }

    /**
     * Show the contacts in the ListView.
     */


    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

            //NUMBER 2
            /*
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            listview.setAdapter(adapter);
            */

            //NUMBER 3
            ContentResolver contentResolver = getContentResolver();

            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            Cursor cursor = contentResolver.query(uri, null, null, null, null);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                    R.layout.row,
                    cursor,
                    new String[]{
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.Contacts.Photo.PHOTO},
                    new int[] {R.id.name, R.id.number, R.id.photo},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER );

            adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    if(view.getId() == R.id.photo) {

                        try {
                            InputStream stream = ContactsContract.Contacts.openContactPhotoInputStream(
                                    getContentResolver(),
                                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                            if (stream != null) {
                                Bitmap photo = BitmapFactory.decodeStream(stream);
                                ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(photo, 64, 64, false));
                                stream.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return true;
                    }

                    return false;
                }
            });

            listview.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }

}
