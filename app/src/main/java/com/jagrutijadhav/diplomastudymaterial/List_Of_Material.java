package com.jagrutijadhav.diplomastudymaterial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class List_Of_Material extends AppCompatActivity implements ValueEventListener {
    ProgressDialog dialog,d1;
    String admin;
    DatabaseReference d_ref,reference;
    StorageReference storageReference;
    StorageReference filepath;
    Uri imageuri;
    MyAdapter myAdapter;
    ListView listView;
    ArrayList<String> file;
    ArrayList<String> date;
    ArrayList<String> listuri;
    PDFData pdfData;
    TextView t;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__of__material);
        //network checking
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(List_Of_Material.this);
            a.setTitle(R.string.app_name);
            a.setMessage("Please Check Your Internet Connection");
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                }
            });
            a.setIcon(R.mipmap.ic_launcher);
            a.show();
        }





        admin=getIntent().getStringExtra("ADMIN")+"";
        t=findViewById(R.id.text);
        b=findViewById(R.id.uploadbutton);
        if(admin.equals("admin")){
            t.setVisibility(TextView.VISIBLE);
            b.setVisibility(Button.VISIBLE);
            t.setText("BRANCH:  "+getIntent().getStringExtra("BRANCH")+"\n\n"+"SEMESTER:  "+getIntent().getStringExtra("SEM"));
        }else {
            t.setVisibility(TextView.GONE);
            b.setVisibility(Button.GONE);
        }
        d1=new ProgressDialog(this);
        d1.setMessage("Loading...");
        d1.show();
        d_ref = FirebaseDatabase.getInstance().getReference().child("" + getIntent().getStringExtra("BRANCH"))
                .child("" + getIntent().getStringExtra("SEM"));
        reference = FirebaseDatabase.getInstance().getReference().child("" + getIntent().getStringExtra("BRANCH"))
                .child("" + getIntent().getStringExtra("SEM"));
        listView = findViewById(R.id.listview);
        date = new ArrayList<>();
        file = new ArrayList<>();
        listuri=new ArrayList<>();
        pdfData=new PDFData();
        myAdapter = new MyAdapter(this, file, date,listuri,admin,d_ref);
        d_ref.addValueEventListener(this);
    }
    public void UploadFile(View view) {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        // We will be redirected to choose pdf
        galleryIntent.setType("application/pdf");
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageuri = data.getData();


            final EditText editText = new EditText(getApplicationContext());
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            AlertDialog.Builder a = new AlertDialog.Builder(List_Of_Material.this);
            a.setTitle("SAVE AS");
          //  a.setCancelable(false);
            a.setMessage("(Mandatory) Enter File Name You Want To Save As...");
            a.setView(editText);
            a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!(editText.getText().toString().equals(""))) {
                        // startActivity(new Intent(getApplicationContext(), Admin_Activity.class));
                        storefileInDatabase(editText.getText().toString().trim());
                        dialog.cancel();
                    }else{
                        Toast.makeText(getApplicationContext(),"Mandatory: Plz Enter File Name",Toast.LENGTH_LONG).show();
                    }
                }
            });
            a.setIcon(R.mipmap.ic_launcher);
            a.show();
        }
    }

    public void storefileInDatabase(String fileText) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading");
        dialog.show();
        final String timestamp = "" + System.currentTimeMillis();
        storageReference = FirebaseStorage.getInstance().getReference();
        final String messagePushID = timestamp;
        // Toast.makeText(List_Of_Material.this, imageuri.toString(), Toast.LENGTH_SHORT).show();
        // Here we are uploading the pdf in firebase storage with the name of current time
        filepath = storageReference.child(messagePushID + "." + "pdf");
        filepath.putFile(imageuri).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Uri uri = task.getResult();
                    String myurl;
                    myurl = uri.toString();
                    pdfData=new PDFData(fileText,myurl,getCurrentDate()+" - "+getCurrentTime());

                    reference.child(fileText).setValue(pdfData);
                    //reference.child(fileText).child("FileURI").setValue(myurl);
                    //reference.child(fileText).child("FileDate").setValue(getCurrentDate()+" - "+getCurrentTime());
                    Toast.makeText(List_Of_Material.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(List_Of_Material.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
    private String getCurrentDate() {
       Calendar calendar = Calendar.getInstance();
       SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(calendar.getTime());
        //return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        date.clear();
        file.clear();
        listuri.clear();
        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
            pdfData=childSnapshot.getValue(PDFData.class);

           file.add(pdfData.getFileName());
           listuri.add(pdfData.getFileURI());
           date.add(pdfData.getFileDate());


        }
        // Toast.makeText(getApplicationContext(),""+time.toString(),Toast.LENGTH_LONG).show();
        listView.setAdapter(myAdapter);
        d1.dismiss();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
