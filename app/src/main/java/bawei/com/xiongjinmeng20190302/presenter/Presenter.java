package bawei.com.xiongjinmeng20190302.presenter;

import java.util.Map;

import bawei.com.xiongjinmeng20190302.bean.QueryShoppingCartBean;
import bawei.com.xiongjinmeng20190302.moder.IModer;
import bawei.com.xiongjinmeng20190302.moder.Moder;
import bawei.com.xiongjinmeng20190302.view.IView;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:41
 * @
 */
public class Presenter {

    private final Moder moder;
    private  IView iView;

    public Presenter(IView iView){
        this.iView=iView;
        moder = new Moder();
    }



    public void get(String url, Map<String, String> map, Class aClass) {
        moder.get(url,map,aClass, new IModer() {
            @Override
            public void onSuccessful(Object data) {
                iView.onSuccessful(data);
            }

            @Override
            public void onEsert(String e) {
                iView.onEsert(e);
            }
        });
    }

    public void onDestroy() {
        if (iView!=null){
            iView=null;
        }
    }
}
