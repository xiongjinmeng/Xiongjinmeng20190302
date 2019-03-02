package bawei.com.xiongjinmeng20190302.util;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:52
 * @
 */
public interface WayApi {
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String,String> map);
}
