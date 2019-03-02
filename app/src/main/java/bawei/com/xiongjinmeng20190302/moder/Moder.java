package bawei.com.xiongjinmeng20190302.moder;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import bawei.com.xiongjinmeng20190302.util.HttpRtrofitUtil;
import bawei.com.xiongjinmeng20190302.util.WayApi;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:42
 * @
 */
public class Moder {
    public void get(String url, Map<String, String> map, Class aClass, IModer iModer) {
        HttpRtrofitUtil.get().create(WayApi.class).get(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
//                            Log.e("-----",string);
                            Gson gson = new Gson();
                            Object o = gson.fromJson(string, aClass);
                            iModer.onSuccessful(o);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        iModer.onEsert(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
