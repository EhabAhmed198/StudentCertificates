package com.ehabahmed.studentcertificate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public final class StudentBroadcastReceiver extends BroadcastReceiver {
    Context context;
    SharedPreferences sharedPreferences, sharedPreferences1, sharedPreferences2,
            sharedPreferences5, sharedPreferences6, sharedPreferences7, sharedPreferences8, sharedPreferences9, sharedPreferences10, sharedPreferences11, sharedPreferences12;
    SharedPreferences.Editor editor, editor1, editor10, editor11, editor12;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    Info info;
    String url, stu_title = "Studennt Certificate";
    PendingIntent pendingIntent;
    String id, name, pass, photo, level, department;
    Intent intent;

    private boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (manager != null) {
            info = manager.getActiveNetworkInfo();
            if (info != null && info.isAvailable() && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(final Context cont, Intent intent) {
        this.context = cont.getApplicationContext();
        if (isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    info = (Info) context.getApplicationContext();
                    requestQueue = Volley.newRequestQueue(context);


                    sharedPreferences = context.getSharedPreferences("student", Context.MODE_PRIVATE);
                    id = sharedPreferences.getString("id", "-1");
                    name = sharedPreferences.getString("name", "-1");
                    pass = sharedPreferences.getString("pass", "-1");
                    photo = sharedPreferences.getString("photo", "-1");
                    department = sharedPreferences.getString("department", "-1");
                    level = sharedPreferences.getString("level", "-1");


                    checkgroup(context);
                    checkgrouppost(context);
                    checktable(context);
                    checkcourse(context);
                    checklibraryBook(context);
                    checkexams(context);
                    checkcompition(context);
                    checknews(context);
                    checkprograms(context);
                    checkcomments();

                }
            }).start();

        }
        setAlarm(context);
    }

    public void setAlarm(Context context) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add 30 seconds to the calendar object
        cal.add(Calendar.SECOND, 30);
        Intent intent = new Intent(context, StudentBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        if (am != null) {
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
        }
    }

    private void checkcomments() {
        final ArrayList<String> listitems = new ArrayList<>();
        listitems.clear();
        SharedPreferences shared = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        final String type = shared.getString("type", "NoData");
        if (type.equals("student")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                url = "https://ehab01998.com/checkComments.php?code=" + id;
            } else {
                url = "http://ehab01998.com/checkComments.php?code=" + id;
            }

        } else if (type.equals("doctor")) {
            SharedPreferences sh = context.getSharedPreferences("doctor", Context.MODE_PRIVATE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                url = "https://ehab01998.com/checkComments.php?code=" + sh.getString("id", "-1");
            } else {
                url = "http://ehab01998.com/checkComments.php?code=" + sh.getString("id", "-1");
            }

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("post_id");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String post_id = object.getString("post_id");
                        listitems.add(post_id + "total");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < listitems.size(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        url = "https://ehab01998.com/checknumsComments.php?post_id=" + listitems.get(i);
                    } else {
                        url = "http://ehab01998.com/checknumsComments.php?post_id=" + listitems.get(i);
                    }
                    final int finalI = i;
                    StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SharedPreferences sharedcomment = context.getSharedPreferences(listitems.get(finalI), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedcomment.edit();
                            if (Integer.parseInt(sharedcomment.getString(listitems.get(finalI), "1000000000")) < Integer.parseInt(response)) {

                                Intent intent = new Intent(context, Comments.class);
                                intent.putExtra("postid", listitems.get(finalI).substring(0, listitems.get(finalI).indexOf("t")));
                                intent.putExtra("type", type);
                                intent.putExtra("fav", "fav");
                                if (type.equals("student")) {
                                    intent.putExtra("SName", name);
                                    intent.putExtra("SId", id);
                                } else if (type.equals("doctor")) {
                                    SharedPreferences doctordata = context.getSharedPreferences("doctor", Context.MODE_PRIVATE);
                                    intent.putExtra("DName", doctordata.getString("name", "-1"));
                                    intent.putExtra("DId", doctordata.getString("id", "-1"));

                                }
                                pendingIntent = PendingIntent.getActivity(context, 35, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                StudentNotification.showNotification(context, 40, context.getResources().getString(R.string.comment), pendingIntent, stu_title, context.getResources().getString(R.string.newcomment), context.getResources().getString(R.string.newcomment));
                                editor.putString(listitems.get(finalI), response);
                                editor.apply();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(request1);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }

    private void checkcompition(final Context context) {
        sharedPreferences12 = context.getSharedPreferences("compition", Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=5&department=" + department;
        } else {
            url = "http://ehab01998.com/getcount.php?type=5&department=" + department;
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Integer.parseInt(response) > Integer.parseInt(sharedPreferences12.getString("Ncompition", "200"))) {
                    editor12 = sharedPreferences12.edit();
                    editor12.putString("Ncompition", response);
                    editor12.apply();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        url = "https://ehab01998.com/getLastCompition.php?department=" + department;
                    } else {
                        url = "http://ehab01998.com/getLastCompition.php?department=" + department;
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("compition");
                                JSONObject object = array.getJSONObject(0);
                                String name_compition = object.getString("name");
                                intent = new Intent(context, Compition.class);
                                intent.putExtra("type", "1");
                                intent.putExtra("department", department);
                                pendingIntent = PendingIntent.getActivity(context, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                String text = name_compition + " compition has been added to Compition";
                                StudentNotification.showNotification(context, 26, "Compitiion", pendingIntent, stu_title, text, text);
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

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }

    private void checkexams(final Context context) {
        sharedPreferences11 = context.getSharedPreferences("exams", Context.MODE_PRIVATE);
        editor11 = sharedPreferences11.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=4&department=" + department + "&level=" + level;
        } else {
            url = "http://ehab01998.com/getcount.php?type=4&department=" + department + "&level=" + level;
        }


        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Integer.parseInt(response) > Integer.parseInt(sharedPreferences11.getString("Nexams", "300"))) {
                    editor11.putString("Nexams", response);
                    editor11.apply();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        url = "https://ehab01998.com/getLastExam.php?department=" + department + "&level=" + level;
                    } else {
                        url = "http://ehab01998.com/getLastExam.php?department=" + department + "&level=" + level;
                    }


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("exam");
                                JSONObject object = array.getJSONObject(0);
                                String name_exam = object.getString("name");
                                intent = new Intent(context, Exams.class);
                                intent.putExtra("type", "1");
                                intent.putExtra("department", department);
                                intent.putExtra("level", level);
                                pendingIntent = PendingIntent.getActivity(context, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                String text = name_exam + " has been added to Exams";
                                StudentNotification.showNotification(context, 25, "Exams", pendingIntent, stu_title, text, text);

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

    }

    private void checklibraryBook(final Context context) {
        sharedPreferences10 = context.getSharedPreferences("library", Context.MODE_PRIVATE);
        editor10 = sharedPreferences10.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=3&department_id=" + department;
        } else {
            url = "http://ehab01998.com/getcount.php?type=3&department_id=" + department;
        }
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Integer.parseInt(response) > Integer.parseInt(sharedPreferences10.getString("Nbook", "200"))) {
                    editor10.putString("Nbook", response);
                    editor10.apply();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        url = "https://ehab01998.com/getLastBook.php?department_id=" + department;
                    } else {
                        url = "http://ehab01998.com/getLastBook.php?department_id=" + department;
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("book");
                                JSONObject object = array.getJSONObject(0);
                                String name_book = object.getString("books_name");
                                intent = new Intent(context, Library.class);
                                intent.putExtra("type", "1");
                                intent.putExtra("department", department);
                                pendingIntent = PendingIntent.getActivity(context, 9, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                String text = name_book + " book has been added to Library";
                                StudentNotification.showNotification(context, 24, "Libarary", pendingIntent, stu_title, text, text);

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

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(request);
    }

    private void checkcourse(final Context context) {
        sharedPreferences8 = context.getSharedPreferences("course", Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=" + 2;
        } else {
            url = "http://ehab01998.com/getcount.php?type=" + 2;
        }

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (Integer.parseInt(response) > Integer.parseInt(sharedPreferences8.getString("Ncourse", "200"))) {

                    SharedPreferences.Editor editor8 = sharedPreferences8.edit();
                    editor8.putString("Ncourse", response);
                    editor8.apply();


                    intent = new Intent(context, Courses.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("department", department);
                    pendingIntent = PendingIntent.getActivity(context, 8, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    StudentNotification.showNotification(context, 23, "courses", pendingIntent, stu_title, "A new course has been added", "A new course has been added");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(request);

    }

    private void checktable(final Context context) {

        sharedPreferences9 = context.getSharedPreferences("enter", Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/checktable.php?department_id=" + department + "&table_level=" + level;
        } else {
            url = "http://ehab01998.com/checktable.php?department_id=" + department + "&table_level=" + level;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1") && sharedPreferences9.getString("type", "no").equals("no")) {
                    SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                    editor9.putString("type", "yes");
                    editor9.apply();
                    intent = new Intent(context, Table_student.class);
                    intent.putExtra("notiftable", "1");
                    intent.putExtra("department", department);
                    intent.putExtra("level", level);
                    pendingIntent = PendingIntent.getActivity(context, 13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    StudentNotification.showNotification(context, 22, "table", pendingIntent, stu_title, "تم أضافة جدول المحاضرات", "تم أضافة جدول المحاضرات");


                } else if (response.equals("2") && sharedPreferences9.getString("type", "no").equals("no")) {

                    SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                    editor9.putString("type", "yes");
                    editor9.apply();
                    intent = new Intent(context, Table_student.class);
                    intent.putExtra("notiftable", "1");
                    intent.putExtra("department", department);
                    intent.putExtra("level", level);

                    pendingIntent = PendingIntent.getActivity(context, 17, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    StudentNotification.showNotification(context, 22, "table", pendingIntent, stu_title, "تم أضافة جدول الامتحانات", "تم أضافة جدول المحاضرات");


                } else if (response.equals("0")) {

                    SharedPreferences.Editor editor9 = sharedPreferences9.edit();
                    editor9.putString("type", "no");
                    editor9.apply();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        requestQueue.add(stringRequest);

    }


    private void checkgroup(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getGroupCount.php?department_id=" + department + "&groups_level=" + level;
        } else {
            url = "http://ehab01998.com/getGroupCount.php?department_id=" + department + "&groups_level=" + level;
        }


        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sharedPreferences1 = context.getSharedPreferences("Groups", Context.MODE_PRIVATE);
                if (Integer.parseInt(response) > sharedPreferences1.getInt("NumsGroups", 100)) {

                    sharedPreferences1 = context.getSharedPreferences("Groups", Context.MODE_PRIVATE);
                    editor1 = sharedPreferences1.edit();
                    editor1.putInt("NumsGroups", Integer.parseInt(response));
                    editor1.apply();

                    intent = new Intent(context, Student.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("pass", pass);
                    intent.putExtra("photo", photo);
                    intent.putExtra("department", department);
                    intent.putExtra("level", level);
                    intent.putExtra("fragment_id", "1");
                    pendingIntent = PendingIntent.getActivity(context, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    StudentNotification.showNotification(context, 2, "Groups", pendingIntent, "Student Certificate", "New Group", "New Group");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);


    }

    private void checknews(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/getcount.php?type=" + 0;
        } else {
            url = "http://ehab01998.com/getcount.php?type=" + 0;
        }

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sharedPreferences = context.getSharedPreferences("number_news", Context.MODE_PRIVATE);
                if (Integer.parseInt(response) > sharedPreferences.getInt("News", 100)) {
                    sharedPreferences = context.getSharedPreferences("number_news", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putInt("News", Integer.parseInt(response));
                    editor.apply();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        url = "https://ehab01998.com/getLastNew.php";
                    } else {
                        url = "http://ehab01998.com/getLastNew.php";
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                            String type = sharedPreferences.getString("type", "NoData");
                            try {
                                JSONArray array = response.getJSONArray("new");
                                JSONObject jsonObject = array.getJSONObject(0);
                                String text = jsonObject.getString("news_text");
                                String title = "Student Certificate";
                                switch (type) {
                                    case "NoData":

                                        intent = new Intent(context, MainActivity.class);
                                        pendingIntent = PendingIntent.getActivity(context, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        StudentNotification.showNotification(context, 1, "NEWS", pendingIntent, title, text, text);
                                        break;
                                    case "student":

                                        intent = new Intent(context, Student.class);
                                        intent.putExtra("id", id);
                                        intent.putExtra("name", name);
                                        intent.putExtra("pass", pass);
                                        intent.putExtra("photo", photo);
                                        intent.putExtra("department", department);
                                        intent.putExtra("level", level);
                                        pendingIntent = PendingIntent.getActivity(context, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        StudentNotification.showNotification(context, 1, "NEWS", pendingIntent, title, text, text);

                                        break;
                                    case "doctor":
                                        sharedPreferences2 = context.getSharedPreferences("doctor", Context.MODE_PRIVATE);
                                        intent = new Intent(context, Doctor.class);
                                        intent.putExtra("doctor_id", sharedPreferences2.getString("id", "-1"));
                                        intent.putExtra("doctor_name", sharedPreferences2.getString("name", "-1"));
                                        intent.putExtra("doctor_password", sharedPreferences2.getString("pass", "-1"));
                                        intent.putExtra("doctor_photo", sharedPreferences2.getString("photo", "-1"));
                                        intent.putExtra("mobile", sharedPreferences2.getString("mobile", "-1"));
                                        intent.putExtra("email", sharedPreferences2.getString("email", "-1"));
                                        pendingIntent = PendingIntent.getActivity(context, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        StudentNotification.showNotification(context, 1, "NEWS", pendingIntent, title, text, text);
                                        break;
                                    case "certificate":

                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    try {
                        requestQueue.add(jsonObjectRequest);
                    } catch (Exception e) {
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);


    }

    private void checkgrouppost(final Context context) {
        try {
            sharedPreferences5 = context.getSharedPreferences("NameGroups", Context.MODE_PRIVATE);
            sharedPreferences6 = context.getSharedPreferences("Groups", Context.MODE_PRIVATE);
            sharedPreferences7 = context.getSharedPreferences("Numberpost", Context.MODE_PRIVATE);
        } catch (Exception e) {
        }
        final SharedPreferences.Editor editor7 = sharedPreferences7.edit();
        int nums = sharedPreferences6.getInt("NumsGroups", 0);
        for (int i = 0; i < nums; i++) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                url = "https://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + sharedPreferences5.getString("Ngroup" + i, "Nodata");
            } else {
                url = "http://ehab01998.com/getcount.php?type=" + 1 + "&group_id=" + sharedPreferences5.getString("Ngroup" + i, "Nodata");
            }

            final int finalI = i;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int old_num = Integer.parseInt(sharedPreferences7.getString("NPost" + finalI, "100000"));
                    if (Integer.parseInt(response) > old_num) {
                        editor7.putString("NPost" + finalI, response);
                        editor7.apply();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            url = "https://ehab01998.com/getLastPost.php?group_id=" + sharedPreferences5.getString("Ngroup" + finalI, "Nodata");
                        } else {
                            url = "http://ehab01998.com/getLastPost.php?group_id=" + sharedPreferences5.getString("Ngroup" + finalI, "Nodata");
                        }

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONArray jsonArray = response.getJSONArray("post");
                                    JSONObject current = jsonArray.getJSONObject(0);
                                    final String text = current.getString("text");
                                    final String type = current.getString("type");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                        url = "https://ehab01998.com/getdoctorId.php?groups_id=" + sharedPreferences5.getString("Ngroup" + finalI, "Nodata");
                                    } else {
                                        url = "http://ehab01998.com/getdoctorId.php?groups_id=" + sharedPreferences5.getString("Ngroup" + finalI, "Nodata");
                                    }

                                    StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            intent = new Intent(context, Group_post.class);
                                            intent.putExtra("group_id", sharedPreferences5.getString("Ngroup" + finalI, "Nodata"));
                                            intent.putExtra("doctor_id", response);
                                            pendingIntent = PendingIntent.getActivity(context, 6, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                            switch (type) {
                                                case "timage":
                                                    StudentNotification.showNotification(context, finalI, "post" + finalI, pendingIntent, "Student Certificate", text, text);
                                                    break;
                                                case "video":
                                                    StudentNotification.showNotification(context, finalI, "post" + finalI, pendingIntent, "Student Certificate", "تم أضافة فديو", "تم أضافة فديو");
                                                    break;
                                                case "file":
                                                    StudentNotification.showNotification(context, finalI, "post" + finalI, pendingIntent, "Student Certificate", text, text);
                                                    break;
                                                case "image":
                                                    StudentNotification.showNotification(context, finalI, "post" + finalI, pendingIntent, "Student Certificate", "تم أضافة صورة", "تم أضافة صورة");
                                                    break;
                                                case "none":
                                                    StudentNotification.showNotification(context, finalI, "post" + finalI, pendingIntent, "Student Certificate", text, text);
                                                    break;
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, "faild1", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                    requestQueue.add(request1);


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


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });

            requestQueue.add(stringRequest);


        }


    }

    public void checkprograms(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            url = "https://ehab01998.com/progrems.php?page=" + 0;
        } else {
            url = "http://ehab01998.com/progrems.php?page=" + 0;
        }


        final SharedPreferences sharedPreferences = context.getSharedPreferences("number_programs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("Programs");
                    if ((array.length()) > Integer.parseInt(sharedPreferences.getString("programs", "300"))) {

                        Intent intent = new Intent(context, Programs.class);
                        pendingIntent = PendingIntent.getActivity(context, 7, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        StudentNotification.showNotification(context, 44, "programs", pendingIntent, "Student Certificate", "SoftWare", "New Software has been added");
                        editor.putString("programs", String.valueOf(array.length()));
                        editor.apply();
                    }


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


}
