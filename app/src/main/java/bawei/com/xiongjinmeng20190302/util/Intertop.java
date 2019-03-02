package bawei.com.xiongjinmeng20190302.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:49
 * @
 */
public class Intertop implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder()
                .addHeader("userId", "195")
                .addHeader("sessionId", "1551496229014195")
                .build();
        return chain.proceed(build);
    }
}
