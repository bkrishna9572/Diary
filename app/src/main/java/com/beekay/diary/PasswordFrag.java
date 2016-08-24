package com.beekay.diary;



import android.database.Cursor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PasswordFrag extends Fragment {


    private TextView b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    private ImageButton back;
    private EditText password;
    private LinearLayout ll;
    private DataOpener opener;
    String first;
    boolean firstChecked;
    public PasswordFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_first,container,false);
        b1=(TextView) view.findViewById(R.id.b1);
        b2=(TextView)view.findViewById(R.id.b2);
        b3=(TextView)view.findViewById(R.id.b3);
        b4=(TextView)view.findViewById(R.id.b4);
        b5=(TextView)view.findViewById(R.id.b5);
        b6=(TextView)view.findViewById(R.id.b6);
        b7=(TextView)view.findViewById(R.id.b7);
        b8=(TextView)view.findViewById(R.id.b8);
        b9=(TextView)view.findViewById(R.id.b9);
        b0=(TextView)view.findViewById(R.id.b0);
        back=(ImageButton)view.findViewById(R.id.backbutton);
        password=(EditText)view.findViewById(R.id.password);
        password.setInputType(InputType.TYPE_CLASS_NUMBER);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ll=(LinearLayout)view.findViewById(R.id.linear);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView b=(TextView)view;
            password.setText(password.getText().toString()+b.getText().toString());
                Log.v("clicked",b.getText().toString());
                Log.v("password",password.getText().toString());
            }
        };
        View.OnClickListener imageListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().length()>0)
          password.setText(password.getText().toString().substring(0,password.getText().toString().length()-1));
            }
        };
        back.setOnClickListener(imageListener);
        b0.setOnClickListener(listener);
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
        b6.setOnClickListener(listener);
        b7.setOnClickListener(listener);
        b8.setOnClickListener(listener);
        b9.setOnClickListener(listener);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==4){
                    opener=new DataOpener(getActivity().getApplicationContext());
                    opener.open();
                    Cursor cursor=opener.retrieve();
                    if(cursor.getCount()==0){
                        if(!firstChecked) {
                            firstChecked=true;
                            Toast.makeText(getActivity(),"Enter the same password again to set",Toast.LENGTH_LONG).show();
                            first=editable.toString();
                            password.setText("");
                        }
                        else {
                            if(first.equals(editable.toString())) {
                                opener.insertData(Integer.parseInt(editable.toString()));
                                opener.close();
                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slie_out_right).replace(R.id.container, new Home()).commit();
                            }
                            else{
                                Toast.makeText(getActivity(),"This password doesn't match with previous one",Toast.LENGTH_LONG).show();
                                password.setText("");
                            }
                        }
                    }
                    else
                    {
                        List<Integer> builder=new ArrayList<Integer>();
                        while(cursor.moveToNext()){
                            builder.add(cursor.getInt(cursor.getColumnIndex("pass")));
                        }
                        if(Integer.parseInt(editable.toString())==builder.get(0)){
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slie_out_right).replace(R.id.container, new Home()).add(R.id.container,new NewFragment()).commit();
                        }
                        else
                        {
                            Log.v("came","shake");
                            Animation shake=AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.wobble);
                            Log.v("start","shake");

                            ll.startAnimation(shake);

                        }
                    }
                }
            }
        });

        return view;
    }


}
