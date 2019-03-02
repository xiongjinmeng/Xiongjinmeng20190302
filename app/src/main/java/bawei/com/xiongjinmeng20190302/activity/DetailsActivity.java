package bawei.com.xiongjinmeng20190302.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bawei.com.xiongjinmeng20190302.R;
import bawei.com.xiongjinmeng20190302.bean.DetailsBean;
import bawei.com.xiongjinmeng20190302.presenter.Presenter;
import bawei.com.xiongjinmeng20190302.util.Api;
import bawei.com.xiongjinmeng20190302.view.IView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.lin)
    LinearLayout lin;
    private Unbinder bind;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }
    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    public void dcc2(String s){
        presenter = new Presenter(this);
        Map<String, String> map = new HashMap<>();
        map.put("commodityId",s);
        presenter.get(Api.FINDCOMMODITY_URL, map, DetailsBean.class);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }

    @Override
    public void onSuccessful(Object data) {
        if (data instanceof DetailsBean) ;
        DetailsBean bean = (DetailsBean) data;
        String message = bean.getMessage();
        String status = bean.getStatus();
        if (status.equals("0000")) {
            String picture = bean.getResult().getPicture();
            String[] split = picture.split(",");
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            xbanner.setData(list, null);
            xbanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(DetailsActivity.this).load(list.get(position)).into((ImageView) view);
                }
            });
            String categoryName = bean.getResult().getCategoryName();
            double price = bean.getResult().getPrice();
            int saleNum = bean.getResult().getSaleNum();
            String describe = bean.getResult().getDescribe();
            textName.setText(categoryName+"  "+describe);
            textPrice.setText("￥"+price);
            textNum.setText("已售"+saleNum+"件");
            String details = bean.getResult().getDetails();
            webView.loadDataWithBaseURL(null,details,"text/html" , "utf-8", null);

        } else {
            Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEsert(String e) {
        Log.e("------", e);
    }
}
