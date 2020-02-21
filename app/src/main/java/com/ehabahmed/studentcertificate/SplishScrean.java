package com.ehabahmed.studentcertificate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


public class SplishScrean extends AppCompatActivity implements Runnable {
    SharedPreferences preferences, sharedPreferences2;
    SharedPreferences.Editor editor, editor2;
    String type, username, password;
    String url;
    RequestQueue requestQueue;
    Handler handler;
    Context context;
    TextView welcome, andwelcome;
    Intent intent;
    String id, name, pass, department, level, photo;
    String doctor_id, doctor_name, doctor_password, doctor_photo, mobile, email;
    Thread thread;
    Info info;
    JSONArray jsonArray;
    JSONObject current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splish_screan);
        update();
        info = (Info) getApplicationContext();
        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences("Notification", Context.MODE_PRIVATE);

        requestQueue = Volley.newRequestQueue(this);


        welcome = findViewById(R.id.welcome);

        andwelcome = findViewById(R.id.andwelocome);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splish2);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.splish);
        welcome.setAnimation(animation);
        andwelcome.setAnimation(animation1);


        type = preferences.getString("type", "NoData");
        username = preferences.getString("username", "NoData");
        password = preferences.getString("password", "NoData");
        handler = new Handler(getMainLooper());
        this.context = this;
        setAlarm(this);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setAlarm(Context context) {
        Calendar cal = Calendar.getInstance();
        // add 30 seconds to the calendar object
        cal.add(Calendar.SECOND, 10);
        Intent intent = new Intent(context, StudentBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        if (am != null) {
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getStudentNumbersRowNotification() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                getNumsNews();
                getNumsgroupsAndPost();
                getNumsCourse();
                getNumsBookLibrary();
                getNumsExams();
                getNumsCompition();
                getNumsProgrems();
                getNumArticles();
            }
        }).start();


    }


    private void getCertificateNumbersRowNotification() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getNumsNews();
                getNumsCourse();
                getNumsBookLibrary();
                getNumsExams();
                getNumsCompition();
                getNumsProgrems();
                getNumArticles();

            }
        });
        thread.start();

    }


    private void getNodataRowNotification() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getNumsNews();
            }
        });
        thread.start();


    }


    private void getDoctrdataRowNotification() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getDoctorNumsNews();
            }
        });
        thread.start();


    }


    private void getNumsNews() {
        SharedPreferences sharedPreferences = getSharedPreferences("number_news", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/ShowNewsArray.php?page=" + 0;
        } else {
            url = "http://ehab01998.com/ShowNewsArray.php?page=" + 0;

        }

        final ArrayList<object_News> listitems = new ArrayList<object_News>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    jsonArray = response.getJSONArray("News");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int news_id = object.getInt("news_id");
                        String news_text = object.getString("news_text");
                        String news_detals = object.getString("news_detals");
                        String news_ivname = object.getString("news_ivname");
                        String news_type = object.getString("news_type");
                        String data_time = object.getString("data_time");

                        listitems.add(new object_News(news_id, news_text, news_detals, news_ivname, news_type, data_time));
                    }

                    editor.putInt("News", listitems.size());
                    editor.apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    private void getDataStudnt() {
        info.setType("student");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("student_info_name", username.trim());
        params.put("student_info_password", password.trim());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/ustudentInfo.php";
        } else {
            url = "http://ehab01998.com/ustudentInfo.php";
        }

        CustomRequest request = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("student");


                    JSONObject current = jsonArray.getJSONObject(0);
                    id = current.getString("student_info_id");
                    name = current.getString("student_info_name");
                    pass = current.getString("student_info_password");
                    photo = current.getString("student_info_photo");
                    level = current.getString("student_info_level");
                    department = current.getString("department_id");
                    preferences = getSharedPreferences("student", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("id", id);
                    editor.putString("name", name);
                    editor.putString("pass", pass);
                    editor.putString("photo", photo);
                    editor.putString("department", department);
                    editor.putString("level", level);
                    editor.apply();

                    intent = new Intent(getApplicationContext(), Student.class);
                    intent.putExtra("Notificationsplish", "ok");
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("pass", pass);
                    intent.putExtra("photo", photo);
                    intent.putExtra("department", department);
                    intent.putExtra("level", level);


                    getStudentNumbersRowNotification();

                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SharedPreferences preferences = getSharedPreferences("student", Context.MODE_PRIVATE);
                intent = new Intent(getApplicationContext(), Student.class);
                intent.putExtra("id", preferences.getString("id", "No"));
                intent.putExtra("name", preferences.getString("name", "No"));
                intent.putExtra("pass", preferences.getString("pass", "No"));
                intent.putExtra("photo", preferences.getString("photo", "NO"));
                intent.putExtra("department", preferences.getString("department", "No"));
                intent.putExtra("level", preferences.getString("level", "No"));
                startActivity(intent);
                finish();

            }
        });
        requestQueue.add(request);
    }

    private void getDataCertificate() {
        info.setType("certificate");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("student_info_name", username);
        params.put("student_info_password", password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/ustudentInfo.php";
        } else {
            url = "http://ehab01998.com/ustudentInfo.php";
        }

        CustomRequest request = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    jsonArray = response.getJSONArray("student");
                    JSONObject current = jsonArray.getJSONObject(0);
                    id = current.getString("student_info_id");
                    name = current.getString("student_info_name");
                    pass = current.getString("student_info_password");
                    photo = current.getString("student_info_photo");
                    level = current.getString("student_info_level");
                    department = current.getString("department_id");
                    preferences = getSharedPreferences("Certificate", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("id", id);
                    editor.putString("name", name);
                    editor.putString("pass", pass);
                    editor.putString("photo", photo);
                    editor.putString("department", department);
                    editor.putString("level", level);
                    editor.apply();
                    intent = new Intent(context, Certificate.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("pass", pass);
                    intent.putExtra("photo", photo);
                    intent.putExtra("department", department);
                    intent.putExtra("level", level);
                    getCertificateNumbersRowNotification();
                    startActivity(intent);
                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SharedPreferences preferences = getSharedPreferences("Certificate", Context.MODE_PRIVATE);
                intent = new Intent(getApplicationContext(), Certificate.class);
                intent.putExtra("id", preferences.getString("id", "No"));
                intent.putExtra("name", preferences.getString("name", "No"));
                intent.putExtra("pass", preferences.getString("pass", "No"));
                intent.putExtra("photo", preferences.getString("photo", "NO"));
                intent.putExtra("department", preferences.getString("department", "No"));
                intent.putExtra("level", preferences.getString("level", "No"));
                startActivity(intent);
                finish();


            }
        });
        requestQueue.add(request);
    }


    private void getDataDoctor() {
        info.setType("doctor");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("doctor_name", username);
        params.put("doctor_password", password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/udoctorInfo.php";
        } else {
            url = "http://ehab01998.com/udoctorInfo.php";
        }

        CustomRequest request = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Doctor");
                    JSONObject current = array.getJSONObject(0);
                    doctor_id = current.getString("doctor_id");
                    doctor_name = current.getString("doctor_name");
                    doctor_photo = current.getString("doctor_photo");
                    doctor_password = current.getString("doctor_password");
                    mobile = current.getString("mobile");
                    email = current.getString("email");

                    preferences = getSharedPreferences("doctor", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("id", doctor_id);
                    editor.putString("name", doctor_name);
                    editor.putString("pass", doctor_password);
                    editor.putString("photo", doctor_photo);
                    editor.putString("mobile", mobile);
                    editor.putString("email", email);
                    editor.apply();
                    intent = new Intent(context, Doctor.class);
                    intent.putExtra("doctor_id", doctor_id);
                    intent.putExtra("doctor_name", doctor_name);
                    intent.putExtra("doctor_photo", doctor_photo);
                    intent.putExtra("doctor_password", doctor_password);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("email", email);
                    getDoctrdataRowNotification();
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                intent = new Intent(context, Doctor.class);
                SharedPreferences sharedPreferences = getSharedPreferences("doctor", Context.MODE_PRIVATE);
                intent.putExtra("doctor_id", sharedPreferences.getString("id", "NO"));
                intent.putExtra("doctor_name", sharedPreferences.getString("name", "NO"));
                intent.putExtra("doctor_photo", sharedPreferences.getString("pass", "NO"));
                intent.putExtra("doctor_password", sharedPreferences.getString("photo", "NO"));
                intent.putExtra("mobile", sharedPreferences.getString("mobile", "NO"));
                intent.putExtra("email", sharedPreferences.getString("email", "NO"));
                startActivity(intent);
                finish();


            }
        });
        requestQueue.add(request);

    }


    @Override
    public void run() {
        switch (type) {
            case "NoData":
                if (sharedPreferences2.getString("type", "-1").equals("-1")) {
                    editor2 = sharedPreferences2.edit();
                    editor2.putString("type", "0");
                    editor2.apply();
                    getNodataRowNotification();

                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case "student":
                getDataStudnt();

                break;
            case "doctor":
                getDataDoctor();
                break;
            case "certificate":
                getDataCertificate();
                break;
            default:
                editor2 = sharedPreferences2.edit();
                editor2.putString("type", "0");
                editor2.apply();
                getNodataRowNotification();

        }

    }

    private void getNumsGroupPost(final int n, ArrayList<Ngroups> listitems) {
        SharedPreferences sharedPreferences2 = getSharedPreferences("Numberpost", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + listitems.get(n).iddoctor;
        } else {
            url = "http://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + listitems.get(n).iddoctor;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                editor2.putString("NPost" + n, response);
                editor2.apply();

            }
        }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        requestQueue.add(stringRequest);


    }


    private void getNumsgroupsAndPost() {
        SharedPreferences sharedPreferences = getSharedPreferences("Groups", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("NameGroups", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final ArrayList<Ngroups> listitems = new ArrayList<Ngroups>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/groups.php?department_id=" + department + "&groups_level=" + level;
        } else {
            url = "http://ehab01998.com/groups.php?department_id=" + department + "&groups_level=" + level;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("groups");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String id_g = object.getString("groups_id");
                        String id_d = object.getString("doctor_id");
                        String name = object.getString("groups_name");
                        editor1.putString("Ngroup" + i, id_g);
                        listitems.add(new Ngroups(id_g, id_d, name));
                    }
                    editor1.apply();
                    for (int i = 0; i < listitems.size(); i++) {
                        getNumsGroupPosts(i, listitems);
                    }
                    editor.putInt("NumsGroups", listitems.size());
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);


    }


    private void getNumsGroupPosts(final int n, ArrayList<Ngroups> listitem) {

        SharedPreferences sharedPreferences2 = getSharedPreferences("Numberpost", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + listitem.get(n).idgroup;
        } else {
            url = "http://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + listitem.get(n).idgroup;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                editor2.putString("NPost" + n, response);
                editor2.apply();

            }
        }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        requestQueue.add(stringRequest);
    }


    private void getNumsCourse() {
        SharedPreferences sharedPreferences = getSharedPreferences("course", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final ArrayList<Courses_object> listitems = new ArrayList<Courses_object>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/courses.php?department_id=" + department + "&page=" + 0;
        } else {
            url = "http://ehab01998.com/courses.php?department_id=" + department + "&page=" + 0;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Courses");


                    editor.putString("Ncourse", String.valueOf(array.length()));
                    editor.apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }


    private void getNumsBookLibrary() {
        SharedPreferences sharedPreferences = getSharedPreferences("library", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final ArrayList<Library_object> listitems = new ArrayList<Library_object>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/library.php?department_id=" + department + "&page=" + 0;
        } else {
            url = "http://ehab01998.com/library.php?department_id=" + department + "&page=" + 0;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Books");

                    editor.putString("Nbook", String.valueOf(array.length()));
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private void getNumsExams() {
        SharedPreferences sharedPreferences = getSharedPreferences("exams", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final ArrayList<Exama_Object> listitems = new ArrayList<Exama_Object>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/exams.php?department=" + department + "&level=" + level + "&page=" + 0;
        } else {
            url = "http://ehab01998.com/exams.php?department=" + department + "&level=" + level + "&page=" + 0;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Exams");

                    editor.putString("Nexams", String.valueOf(array.length()));
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);

    }


    private void getNumsCompition() {
        SharedPreferences sharedPreferences = getSharedPreferences("compition", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final ArrayList<Compition_Object> listitems = new ArrayList<Compition_Object>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/compition.php?department=" + department + "&page=" + 0;
        } else {
            url = "http://ehab01998.com/compition.php?department=" + department + "&page=" + 0;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Compition");

                    editor.putString("Ncompition", String.valueOf(array.length()));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }


    private void getDoctorNumsNews() {
        SharedPreferences sharedPreferences = getSharedPreferences("number_news", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/ShowNewsArray.php";
        } else {
            url = "http://ehab01998.com/ShowNewsArray.php";
        }

        final ArrayList<object_News> listitems = new ArrayList<object_News>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayRequest = response.getJSONArray("News");


                    editor.putInt("News", (arrayRequest.length()));

                    editor.apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private void getNumsProgrems() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/progrems.php?page=" + 0;
        } else {
            url = "http://ehab01998.com/progrems.php?page=" + 0;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("number_programs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Programs");

                    editor.putString("programs", String.valueOf(array.length()));
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue.add(request);


    }


    public void update() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/checkUpdate.php";
        } else {
            url = "http://ehab01998.com/checkUpdate.php";
        }


        StringRequest requests = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("update7")) {
                    Intent intent = new Intent(SplishScrean.this, UpdateApp.class);
                    startActivity(intent);
                    finish();
                } else {
                    handler.postDelayed(SplishScrean.this, 2000);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.postDelayed(SplishScrean.this, 2000);
            }
        });
        Volley.newRequestQueue(SplishScrean.this).add(requests);
    }

    private void getNumArticles() {
        String url;
        String protocal;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/Articles.php?page=0";
            protocal = "https://";
        } else {
            url = "http://ehab01998.com/Articles.php?page=0";
            protocal = "http://";

        }
        SharedPreferences sharedPreferences = getSharedPreferences("number_ariticles", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Articles");
                    editor.putString("ariticles", String.valueOf(array.length()));
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(SplishScrean.this).add(request);

    }

}
