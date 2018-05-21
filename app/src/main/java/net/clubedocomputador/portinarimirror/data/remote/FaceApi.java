package net.clubedocomputador.portinarimirror.data.remote;


import net.clubedocomputador.portinarimirror.data.model.FaceRecognition;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FaceApi {

    @Headers("Content-type: application/json")
    @POST("enroll")
    Single<String> enroll(@Body FaceRecognition faceRecognition);


    @Headers("Content-type: application/json")
    @POST("verify")
    Single<String> verify(@Body FaceRecognition faceRecognition);

    @Headers("Content-type: application/json")
    @POST("recognize")
    Single<String> recognize(@Body FaceRecognition faceRecognition);
}
