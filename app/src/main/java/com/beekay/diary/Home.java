package com.beekay.diary;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Home extends Fragment {

    private DataOpener opener;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager manager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        DataClass[] data = {new DataClass(new Date(),"Shit","Day 1"),new DataClass(new Date(),"BullShit","Day 2")};
        RecyclerAdapter adapter = new RecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
//        opener=new DataOpener(getActivity().getApplicationContext());
//        opener.open();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.first,menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v("came","settings");
        switch (item.getItemId()){
            case R.id.action_settings: {
                Log.v("came","settings");
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(Html.fromHtml("<font color='#3399ff'>Change Password</font>"));
                alert.setInverseBackgroundForced(true);
                LayoutInflater inflater=getActivity().getLayoutInflater();
                alert.setView(inflater.inflate(R.layout.dialog_layout,null));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog=(Dialog)dialogInterface;
                        EditText current=(EditText)dialog.findViewById(R.id.oldpass);
                        EditText newer=(EditText)dialog.findViewById(R.id.newpass);
                        EditText confirm=(EditText)dialog.findViewById(R.id.matchnew);
                        int currentText=Integer.parseInt(current.getText().toString());
                        int newerText=Integer.parseInt(newer.getText().toString());
                        int confirmText=Integer.parseInt(confirm.getText().toString());
                        Log.v("You entered",""+currentText+""+newerText+""+confirmText);
                        opener=new DataOpener(getActivity().getApplicationContext());
                        opener.open();
                        Cursor cursor=opener.retrieve();
                        List<Integer> builder=new ArrayList<Integer>();
                        while(cursor.moveToNext()){
                            builder.add(cursor.getInt(cursor.getColumnIndex("pass")));
                            Log.v("password",""+cursor.getInt(cursor.getColumnIndex("pass")));
                        }
                        if(cursor.getCount()==0 && newer.getText().toString().length()==4 && newer.getText().toString().equals(confirm.getText().toString())){
                            Log.v("New Password","set");
                            opener.insertData(Integer.parseInt(newer.getText().toString()));
                            opener.close();
                        }
                        if(currentText==builder.get(0) && newerText==confirmText && newerText>=0 && newerText<=9999 ){
                            opener.delete();
                            Log.v("deleted","table");
                            Log.v("Trying to","insert");
                            opener.insertData(newerText);
                            Toast.makeText(getActivity().getApplicationContext(),"Password Changed!",Toast.LENGTH_LONG).show();
                        }
                        else if(currentText!=builder.get(0)){
                            Toast.makeText(getActivity().getApplicationContext(),"Old Password doesnot match",Toast.LENGTH_LONG).show();
                        }
                        else if (currentText==builder.get(0) && newerText!=confirmText){
                            Toast.makeText(getActivity().getApplicationContext(),"New Passwords Doesn't Match",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alert.show();
                return true;
            }

            default:return super.onOptionsItemSelected(item);
        }

    }
}
