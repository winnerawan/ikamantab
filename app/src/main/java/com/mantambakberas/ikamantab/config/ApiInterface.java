/*
 * Copyright (c) 2016. Ikamantab (Ikatan Keluarga Alumni Man Tambakberas).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file   except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License..
 */

package com.mantambakberas.ikamantab.config;

import com.mantambakberas.ikamantab.model.ID;
import com.mantambakberas.ikamantab.model.Me;
import com.mantambakberas.ikamantab.model.ShareInfo;
import com.mantambakberas.ikamantab.response.AsramaResponse;
import com.mantambakberas.ikamantab.response.BaseResponse;
import com.mantambakberas.ikamantab.response.CR_Response;
import com.mantambakberas.ikamantab.response.FriendsResponse;
import com.mantambakberas.ikamantab.response.HasAddedResponse;
import com.mantambakberas.ikamantab.response.IsFriendResponse;
import com.mantambakberas.ikamantab.response.JurusanResponse;
import com.mantambakberas.ikamantab.response.LoginResponse;
import com.mantambakberas.ikamantab.response.MsgResponse;
import com.mantambakberas.ikamantab.response.NotifResponse;
import com.mantambakberas.ikamantab.response.PendingFriendResponse;
import com.mantambakberas.ikamantab.response.RegisterResponse;
import com.mantambakberas.ikamantab.response.UpdateGCMResponse;
import com.mantambakberas.ikamantab.response.UsersResponse;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by winnerawan on 11/11/16.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/v1/register")
    void register(@Field("name") String name, @Field("email") String email, @Field("password") String password, Callback<RegisterResponse> cb);

    @FormUrlEncoded
    @POST("/api/v1/registerInfo")
    void registerinfo(@Field("jenis_kelamin") String jenis_kelamin, @Field("angkatan_lulus") String angkatan_lulus,
                      @Field("jurusan_id") String jurusan_id, @Field("asrama_id") String asrama_id, Callback<RegisterResponse> cb);

    @FormUrlEncoded
    @POST("/api/v1/login")
    void login(@Field("email") String email, @Field("password") String password, Callback<LoginResponse> cb);

    @GET("/api/v1/listJurusan")
    void listjurusan(Callback<JurusanResponse> cb);

    @GET("/api/v1/listAsrama")
    void listasrama(Callback<AsramaResponse> cb);

    @GET("/api/v1/listUsers")
    void getlistusers(Callback<UsersResponse> cb);

    @GET("/api/v1/myInformation")
    void getmyinfo(Callback<Me> cb);

    @GET("/api/v1/chat_rooms")
    void getallchatrooms(Callback<CR_Response> cb);

    @GET("/api/v1/chat_rooms/{id}")
    void fetchChatThread(@Path("id") String id, Callback<MsgResponse> cb);

    @GET("/api/v1/user/{id}")
    void getshareinfo(@Path("id") String user_id, Callback<ShareInfo> cb);

    @GET("/api/v1/userid/{email}")
    void getidbyemail(@Path("email") String email, Callback<ID> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updategcmUser/{id}")
    void updateGCMID(@Path("id") String user_id, @Field("gcm_registration_id") String gcm_registration_id, Callback<UpdateGCMResponse> cb);

    @GET("/api/v1/listAllFriends")
    void getListAllFriends(Callback<FriendsResponse> cb);

    @GET("/api/v1/user/{user_id}/friend/{friend_id}")
    void isfriend(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<IsFriendResponse> cb);

    @GET("/api/v1/hasadded/{user_id}/friend/{friend_id}")
    void hasadded(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<HasAddedResponse> cb);

    @POST("/api/v1/add/{user_id}/friend/{friend_id}")
    void addFriend(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<BaseResponse> cb);

    @POST("/api/v1/accept/{user_id}/friend/{friend_id}")
    void acceptFriend(@Path("user_id") String user_id, @Path("friend_id") String friend_id, Callback<BaseResponse> cb);

    @GET("/api/v1/pendingRequest")
    void pendingFriendRequest(Callback<PendingFriendResponse> cb);

    @GET("/api/v1/friendRequest")
    void getFriendRequest(Callback<FriendsResponse> cb);

    @GET("/api/v1/friendSuggestion")
    void getFriendSuggestion(Callback<FriendsResponse> cb);

    @POST("/api/v1/notif")
    void sendNotif(@Query("id") String id, @Query("title") String title, @Query("body") String body, Callback<NotifResponse> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updateBio")
    void updateBio(@Field("bio") String bio, Callback<BaseResponse> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updateProfesi")
    void updateProfesi(@Field("profesi") String profesi, Callback<BaseResponse> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updateKeahlian")
    void updateKeahlian(@Field("keahlian") String keahlian, Callback<BaseResponse> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updatePenghargaan")
    void updatePenghargaan(@Field("penghargaan") String penghargaan, Callback<BaseResponse> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updateReferensi")
    void updateReferensi(@Field("referensi_rekomendasi") String referensi_rekomendasi, Callback<BaseResponse> cb);

    @FormUrlEncoded
    @PUT("/api/v1/updateMinat")
    void updateMinat(@Field("minat_profesi") String minat_profesi, Callback<BaseResponse> cb);

    @FormUrlEncoded
    @POST("/api/v1/upload")
    void uploadFoto(@Field("foto") String foto, @Field("api_key") String api_key, Callback<Response> cb);

}
