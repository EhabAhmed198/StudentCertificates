package com.ehabahmed.studentcertificate;



import okhttp3.MultipartBody;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;



public interface ApiConfig {


    @POST("uploadVideo.php")
    @Multipart
    Call<ResponseBody>
    upload(@Part("group_id") RequestBody group_id,@Part("text") RequestBody name,@Part("type") RequestBody type, @Part MultipartBody.Part file);


    @POST("uploadVideo.php")
     @Multipart
     Call<ResponseBody>
    uploadtext(@Part("group_id") RequestBody group_id,@Part("text") RequestBody name,@Part("type") RequestBody type, @Part("file") RequestBody noth);

    @POST("uploadnew.php")
    @Multipart
    Call<ResponseBody>
    uploadnew(@Part("news_text") RequestBody name,@Part("news_type") RequestBody type, @Part MultipartBody.Part file);


    @POST("uploadnew.php")
    @Multipart
    Call<ResponseBody>
    uploadtextnew(@Part("news_text") RequestBody name,@Part("news_type") RequestBody type, @Part("file") RequestBody noth);


@POST("GraduationCertificate.php")
    @Multipart
    Call<ResponseBody>
    uploadImage(@Part("types") RequestBody types,
            @Part("typeGraduation") RequestBody typeGraduation
        ,@Part("name") RequestBody name,
                @Part("department") RequestBody department,
                @Part("code") RequestBody code,@Part MultipartBody.Part image1,@Part MultipartBody.Part image2,@Part MultipartBody.Part image3,
@Part("yearGraduation") RequestBody yearGraduation,@Part("monthGraduation") RequestBody monthGraduation);

    @POST("GraduationCertificate.php")
    @Multipart
    Call<ResponseBody>
    uploadImage1(@Part("types") RequestBody types,
                @Part("typeGraduation") RequestBody typeGraduation
            ,@Part("name") RequestBody name,
                @Part("department") RequestBody department,
                @Part("code") RequestBody code,@Part MultipartBody.Part image1,@Part("image2") RequestBody image2,@Part("image3") RequestBody image3,
                @Part("yearGraduation") RequestBody yearGraduation,@Part("monthGraduation") RequestBody monthGraduation);



    @POST("GraduationCertificate.php")
    @Multipart
    Call<ResponseBody>
    uploadImage2(@Part("types") RequestBody types,
                @Part("typeGraduation") RequestBody typeGraduation
            ,@Part("name") RequestBody name,
                @Part("department") RequestBody department,
                @Part("code") RequestBody code,@Part("image1") RequestBody image1,@Part MultipartBody.Part image2,@Part MultipartBody.Part image3,
                @Part("yearGraduation") RequestBody yearGraduation,@Part("monthGraduation") RequestBody monthGraduation);

}
