package com.ehabahmed.studentcertificate;



import java.util.ArrayList;

import okhttp3.MultipartBody;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


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



    @POST("uploadPostSGroup.php")
    @Multipart
    Call<ResponseBody>
    uploadtextPostGroup(@Part("post_text") RequestBody name,@Part("post_type") RequestBody type,@Part("group_Id") RequestBody groupId,@Part("code") RequestBody code,@Part("file") RequestBody noth);


    @POST("uploadPostSGroup.php")
    @Multipart
    Call<ResponseBody>
    uploadPostSGroup(@Part("post_text") RequestBody name,@Part("post_type") RequestBody type,@Part("group_Id") RequestBody groupId,@Part("code") RequestBody code, @Part MultipartBody.Part file);




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


    @GET("GetCPI.php")
    Call<String>getdata(@Query("code") String code);

    @GET("getCPIDoctor.php")
    Call<String>getDoctordata(@Query("code") String code);


    @POST("updataData.php")
    @Multipart
    Call<String>
     changeData1(
             @Part("check") RequestBody check,
             @Part("OldCode") RequestBody OldCode
             ,@Part("OldImg") RequestBody OldImg,
                 @Part("Code") RequestBody Code,
                 @Part("Name") RequestBody Name,
                @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email
            ,
    @Part MultipartBody.Part NewImg
                 );



    @POST("updataData.php")
    @Multipart
    Call<String>
    changeData2(
            @Part("check") RequestBody check,
            @Part("OldCode") RequestBody OldCode
            ,@Part("OldImg") RequestBody OldImg,
            @Part("Code") RequestBody Code,
            @Part("Name") RequestBody Name,
            @Part("mobile") RequestBody mobile,
            @Part("email") RequestBody email


    );

@GET("GetMembers.php")
    Call<ArrayList<member>> getDataMember(@Query("NumberDepartment") String NumberDepartment, @Query("NumberBand") String NumberBand,@Query("code") String code) ;

@GET("getMemberToAddGroup.php")
Call<ArrayList<member>> getDataMemberGroup(@Query("NumberDepartment") String NumberDepartment, @Query("NumberBand") String NumberBand,@Query("code") String code,@Query("Group_id") String Group_ID) ;


@GET("getCountArticles.php")
    Call<String> getNumArticles();


@GET("getStudentGroup.php")
    Call<ArrayList<DataGroup>> getDataGroup(@Query("code") String code);

@POST("CreateStudentGroup.php")
@Multipart
    Call<String> CreateGroup(@Part("group_name") RequestBody name,@Part("group_info") RequestBody infogroup,@Part MultipartBody.Part photo);


    @GET("SetMemberGroup.php")
    Call<String> SetMember(@Query("code") String code,@Query("Group_id") String Group_id,@Query("type") String type,@Query("state") String state);


    @GET("ShowStudentGroupPost.php")
    Call<ArrayList<objectPostGroup>> getposts(@Query("group_Id") String group_Id);


    @GET("getGroupMember.php")
    Call<ArrayList<member>> getGroupMember(@Query("Group_id") String GroupId) ;


    @GET("checkExitMember.php")
    Call<ArrayList<NewGroupAdd>> checkIfAdd(@Query("code") String code);

   // http://ehab01998.com/?Id=72&state=YES

    @GET("changeStudentStateGroup.php")
    Call<String> changeState(@Query("Id") String IdExsitGroup,@Query("state") String state);



    @GET("getStateWaitStudentGroup.php")
    Call<ArrayList<NewGroupAdd>> getWaitStateStudentGroup(@Query("code") String code);
//http://ehab01998.com/?=ehab&=uy.jpg&=616006&=1
    @GET("addLikePostGroup.php")
    Call<String> addLikeStudentGroup(@Query("name") String name,@Query("photo") String photo,@Query("code") String code,@Query("postid") String postid);


    @GET("checkcolorLike.php")
    Call<String> checkColorLikeButton(@Query("code") String code,@Query("postid") String postid);
// http://ehab01998.com/?postid=15


    @GET("getNumberLikeInGroup.php")
    Call<String> getNumberLike(@Query("postid") String postid);



    @GET("getMemberLikeInGroup.php")
    Call<ArrayList<member>> getMemberLike(@Query("postid") String postid);
}
