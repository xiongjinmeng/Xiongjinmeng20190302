package bawei.com.xiongjinmeng20190302.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:43
 * @
 */
public class HttpRtrofitUtil {

    private final Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static HttpRtrofitUtil util;

    private OkHttpClient okHttpClient(){

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10,TimeUnit.MINUTES)
                .writeTimeout(10,TimeUnit.MINUTES)
                .readTimeout(10,TimeUnit.MINUTES)
                .addInterceptor(new Intertop())
                .build();
    }

    private HttpRtrofitUtil(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.SMALL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient())
                .build();
    }
    public static HttpRtrofitUtil get(){
        if (util==null){
            synchronized (HttpRtrofitUtil.class){
                if (util==null){
                    util = new HttpRtrofitUtil();
                }
            }
        }

        return util;
    }
    public <T>T create(Class<T> aClass){
        return retrofit.create(aClass);
    }
}
