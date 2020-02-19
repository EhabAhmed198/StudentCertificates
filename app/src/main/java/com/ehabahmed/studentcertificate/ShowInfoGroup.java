package com.ehabahmed.studentcertificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowInfoGroup extends AppCompatActivity {
    String GroupName,GroupInfo;
    TextView NameGroup,InfoGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_group);
        NameGroup=findViewById(R.id.NameGroup);
        InfoGroup=findViewById(R.id.GroupInfo);
        GroupName=getIntent().getExtras().getString("GroupName");
        GroupInfo=getIntent().getExtras().getString("GroupInfo");
        String name=GroupName.substring(0,1).toUpperCase()+GroupName.substring(1);
        NameGroup.setText(name);
        InfoGroup.setText(GroupInfo);


    }
}
