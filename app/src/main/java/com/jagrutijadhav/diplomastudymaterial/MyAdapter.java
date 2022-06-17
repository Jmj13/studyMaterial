package com.jagrutijadhav.diplomastudymaterial;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapter  extends ArrayAdapter<String> {
    ArrayList<String> list_file;
    ArrayList<String> list_date;
    ArrayList<String> list_url;
    String admin;
    DatabaseReference d_ref;
    Activity activity;

    public MyAdapter(@NonNull Activity activity, ArrayList<String> list_file, ArrayList<String> list_date,
                     ArrayList<String> list_url,String admin,DatabaseReference d_ref) {
        super(activity, R.layout.sinlerow, list_file);
        this.activity = activity;
        this.list_date = list_date;
        this.list_file = list_file;
        this.list_url = list_url;
        this.admin=admin;
        this.d_ref=d_ref;
    }

    class ViewHolder {
        TextView list_file, list_Date, delete_tv;
        CardView cardView;

        ViewHolder(View v) {
            list_Date = (TextView) v.findViewById(R.id.id_time);
            list_file = (TextView) v.findViewById(R.id.id_file);
            cardView = (CardView) v.findViewById(R.id.id_date_time_singlerow);
            delete_tv = (TextView) v.findViewById(R.id.id_list_delete);

        }
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=activity.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.sinlerow,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder) r.getTag();
        }
        viewHolder.list_Date.setText(list_date.get(position));
        viewHolder.list_file.setText(list_file.get(position)+".pdf");

       viewHolder. cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),PDFViewActivity.class);

                intent.putExtra("FileUri",list_url.get(position));
                intent.putExtra("FileName",list_file.get(position));
        //Toast.makeText(view.getContext(),""+list_url.get(position),Toast.LENGTH_LONG).show();
                view.getContext().startActivity(intent);

            }
        });



        if(admin.equals("admin")){

            viewHolder.delete_tv.setVisibility(View.VISIBLE);
            viewHolder.delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder a = new AlertDialog.Builder(view.getContext());
                    a.setTitle(R.string.app_name);
                    a.setMessage("Are You Sure,You want to Delete Data of" +list_file.get(position)+"?");
                    a.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                         // String aa[];
                         // aa=list_file.get(position).split(".");
                           // Toast.makeText(view.getContext(),"Delete Successfully"+list_file.get(position),Toast.LENGTH_LONG).show();
                          d_ref.child(list_file.get(position)).removeValue();
                            Toast.makeText(view.getContext(),"Delete Successfully ",Toast.LENGTH_LONG).show();
                        }
                    });
                    a.setNegativeButton("NO",null);
                    a.setIcon(R.mipmap.ic_launcher);
                    a.show();

                }
            });

        }

        return  r;
    }


}
