package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Members extends AppCompatActivity implements View.OnClickListener {
    Spinner department,band;
    String[] data_department,data_band;
    ArrayAdapter<String> adapter1,adapter2;
    RecyclerView MemberList;
    LinearLayoutManager manager;
    int NumberDepartment=0,Numberband=0;
    Retrofit retrofit;
    ApiConfig apiConfig;
    ArrayList<member> listitems;
    MemberAdapter adapter;
    Button updateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        department=findViewById(R.id.department);
        MemberList=findViewById(R.id.memberList);
        updateList=findViewById(R.id.updateList);
setTitle(getResources().getString(R.string.Members));
         manager=new LinearLayoutManager(Members.this);
         MemberList.setLayoutManager(manager);
        band=findViewById(R.id.band);
        data_department=getResources().getStringArray(R.array.departments2);
        data_band=getResources().getStringArray(R.array.lans2);
        adapter1=new ArrayAdapter<>(this,R.layout.spinner,R.id.text_spinner,data_department);
        adapter2=new ArrayAdapter<>(this,R.layout.spinner,R.id.text_spinner,data_band);
        department.setAdapter(adapter1);
        MemberList.setNestedScrollingEnabled(false);
        band.setAdapter(adapter2);
        getdata(NumberDepartment,Numberband);
        updateList.setOnClickListener(this);




    }

    private void getdata(int numberDepartment, int numberband) {
        listitems=new ArrayList<>();
        listitems.clear();
        retrofit=new Retrofit.Builder()
                .baseUrl("http://ehab01998.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiConfig=retrofit.create(ApiConfig.class);
        apiConfig.getDataMember(String.valueOf(numberDepartment),String.valueOf(numberband)).enqueue(new Callback<ArrayList<member>>() {
            @Override
            public void onResponse(Call<ArrayList<member>> call, Response<ArrayList<member>> response) {
                if ((NumberDepartment == 0 && Numberband != 0) == false) {
                    listitems = response.body();
                    adapter = new MemberAdapter(Members.this, listitems);
                    MemberList.setAdapter(adapter);

                } else {
                    Toast.makeText(Members.this, getResources().getString(R.string.correctchoose), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ArrayList<member>> call, Throwable t) {
                Toast.makeText(Members.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void getConvertSpinnerData(){
        if(department.getSelectedItem().toString().equals(data_department[0])){
            NumberDepartment=0;
        }
        else   if(department.getSelectedItem().toString().equals(data_department[1])){
            NumberDepartment=1;
        }
        else   if(department.getSelectedItem().toString().equals(data_department[2])){
            NumberDepartment=2;
        }
        else   if(department.getSelectedItem().toString().equals(data_department[3])){

            NumberDepartment=3;
        }


        if(band.getSelectedItem().toString().equals(data_band[0])){
            Numberband=0;
        }
        else if(band.getSelectedItem().toString().equals(data_band[1])){
            Numberband=1;
        }
        else if(band.getSelectedItem().toString().equals(data_band[2])){
            Numberband=2;
        }
        else if(band.getSelectedItem().toString().equals(data_band[3])){
            Numberband=3;
        }
        else if(band.getSelectedItem().toString().equals(data_band[4])){
            Numberband=4;
        }else if(band.getSelectedItem().toString().equals(data_band[5])){
            Numberband=5;
        }


    }


    @Override
    public void onClick(View v) {
        getConvertSpinnerData();

            getdata(NumberDepartment, Numberband);

    }
}
