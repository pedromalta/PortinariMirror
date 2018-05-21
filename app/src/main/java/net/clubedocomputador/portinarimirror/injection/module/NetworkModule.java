package net.clubedocomputador.portinarimirror.injection.module;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import net.clubedocomputador.portinarimirror.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by shivam on 29/5/17.
 */
@Module
public class NetworkModule {

    private final Context context;
    private final String baseUrl;

    public NetworkModule(final Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    Interceptor provideAuthorizationInterceptor() {
        return (chain) -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.header("app_id",  BuildConfig.APP_ID);
            requestBuilder.header("app_key",  BuildConfig.API_KEY);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                     StethoInterceptor stethoInterceptor,
                                     ChuckInterceptor chuckInterceptor,
                                     Interceptor authorizationInterceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(chuckInterceptor);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
            httpClientBuilder.addNetworkInterceptor(stethoInterceptor);
        }
        httpClientBuilder.addInterceptor(authorizationInterceptor);
        return httpClientBuilder.build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Timber.d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @Singleton
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    ChuckInterceptor provideChuckInterceptor() {
        return new ChuckInterceptor(context);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
}
