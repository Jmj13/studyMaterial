package com.jagrutijadhav.diplomastudymaterial;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    String adminbranch,adminsem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
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


    }
    public void DepartmentClick(View view) {
        intent=new Intent(getApplicationContext(),List_Of_Material.class);
        String[] singleChoiceItems = {"SEM 1","SEM 2","SEM 3","SEM 4","SEM 5","SEM 6"};
        final int[] itemSelected = {0};
        String s=((Button)findViewById(view.getId())).getText().toString();
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Semester")
                .setSingleChoiceItems(singleChoiceItems, itemSelected[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        itemSelected[0] = selectedIndex;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (itemSelected[0]) {
                            case 0:
                               // Toast.makeText(getApplicationContext(),"I love"+s,Toast.LENGTH_LONG).show();
                                intent.putExtra("BRANCH",""+s);
                                intent.putExtra("SEM","SEM 1");
                                startActivity(intent);
                                break;
                            case 1:
                                intent.putExtra("BRANCH",""+s);
                                intent.putExtra("SEM","SEM 2");
                                startActivity(intent);
                                break;
                            case 2:
                                intent.putExtra("BRANCH",""+s);
                                intent.putExtra("SEM","SEM 3");
                                startActivity(intent);
                                break;
                            case 3:
                                intent.putExtra("BRANCH",""+s);
                                intent.putExtra("SEM","SEM 4");
                                startActivity(intent);
                                break;
                            case 4:
                                intent.putExtra("BRANCH",""+s);
                                intent.putExtra("SEM","SEM 5");
                                startActivity(intent);
                                break;
                            case 5:
                                intent.putExtra("BRANCH",""+s);
                                intent.putExtra("SEM","SEM 6");
                                startActivity(intent);
                                break;
                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.adminmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.admin:


                final EditText editText = new EditText(getApplicationContext());
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                a.setTitle(R.string.app_name);
                //  a.setCancelable(false);
                a.setMessage("Enter Password:");
                a.setView(editText);
                a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((editText.getText().toString().equals("admin@123"))) {
                            // startActivity(new Intent(getApplicationContext(), Admin_Activity.class));
                            callAdminAsActivity();


                            dialog.cancel();
                        }else{
                            Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                a.setIcon(R.mipmap.ic_launcher);
                a.show();
               // Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callAdminAsActivity (){

        String[] singleChoiceItems = {"COMPUTER","CIVIL","ELECTRICAL","MECHANICAL","E and TC"};
        final int[] itemSelected = {0};
        //String s=((Button)findViewById(view.getId())).getText().toString();
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Branch")
                .setSingleChoiceItems(singleChoiceItems, itemSelected[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        itemSelected[0] = selectedIndex;
                    }
                })
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (itemSelected[0]) {
                            case 0:
                                // Toast.makeText(getApplicationContext(),"I love"+s,Toast.LENGTH_LONG).show();
                                adminbranch=singleChoiceItems[0];
                                selectsem();
                                break;
                            case 1:
                                adminbranch=singleChoiceItems[1];
                                selectsem();
                                break;
                            case 2:
                                adminbranch=singleChoiceItems[2];
                                selectsem();
                                break;
                            case 3:
                                adminbranch=singleChoiceItems[3];
                                selectsem();
                                break;
                            case 4:
                                adminbranch=singleChoiceItems[4];
                                selectsem();
                                break;

                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void  selectsem(){
        intent=new Intent(getApplicationContext(),List_Of_Material.class);
        String[] singleChoiceItems = {"SEM 1","SEM 2","SEM 3","SEM 4","SEM 5","SEM 6"};
        final int[] itemSelected = {0};

        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Semester")
                .setSingleChoiceItems(singleChoiceItems, itemSelected[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        itemSelected[0] = selectedIndex;
                    }
                })
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (itemSelected[0]) {
                            case 0:
                                intent.putExtra("BRANCH",""+adminbranch);
                                intent.putExtra("SEM","SEM 1");
                                intent.putExtra("ADMIN","admin");
                                startActivity(intent);

                                break;
                            case 1:
                                intent.putExtra("BRANCH",""+adminbranch);
                                intent.putExtra("SEM","SEM 2");
                                intent.putExtra("ADMIN","admin");
                                startActivity(intent);
                                break;
                            case 2:
                                intent.putExtra("BRANCH",""+adminbranch);
                                intent.putExtra("SEM","SEM 3");
                                intent.putExtra("ADMIN","admin");
                                startActivity(intent);

                                break;
                            case 3:
                                intent.putExtra("BRANCH",""+adminbranch);
                                intent.putExtra("SEM","SEM 4");
                                intent.putExtra("ADMIN","admin");
                                startActivity(intent);

                                break;
                            case 4:
                                intent.putExtra("BRANCH",""+adminbranch);
                                intent.putExtra("SEM","SEM 5");
                                intent.putExtra("ADMIN","admin");
                                startActivity(intent);

                                break;
                            case 5:
                                intent.putExtra("BRANCH",""+adminbranch);
                                intent.putExtra("SEM","SEM 6");
                                intent.putExtra("ADMIN","admin");
                                startActivity(intent);

                                break;
                        }
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }
}