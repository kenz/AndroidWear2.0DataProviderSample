package jp.co.sample.wear2;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 天気予報を取得する
 * Created by kenz on 12/6/16.
 */
class Network {
    interface Api {
        @GET("json/v1")
        Observable<ResultWeather> getWeather(@Query("city") String city);
    }
    static final Api api;
    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d("network", message));
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://weather.livedoor.com/forecast/webservice/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }
}
