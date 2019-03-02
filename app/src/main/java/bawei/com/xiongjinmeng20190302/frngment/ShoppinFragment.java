package bawei.com.xiongjinmeng20190302.frngment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.xiongjinmeng20190302.R;
import bawei.com.xiongjinmeng20190302.adapter.MyShoppinAdapter;
import bawei.com.xiongjinmeng20190302.bean.QueryShoppingCartBean;
import bawei.com.xiongjinmeng20190302.presenter.Presenter;
import bawei.com.xiongjinmeng20190302.util.Api;
import bawei.com.xiongjinmeng20190302.view.IView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppinFragment extends Fragment implements IView {


    @BindView(R.id.rexy_shoppin)
    RecyclerView rexyShoppin;
    @BindView(R.id.check_shoppin_box)
    CheckBox checkShoppinBox;
    @BindView(R.id.text_shoppin_price)
    TextView textShoppinPrice;
    @BindView(R.id.btn_shoppin)
    Button btnShoppin;
    Unbinder unbinder;
    private View view;
    private Presenter presenter;

    public ShoppinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shoppin, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new Presenter(this);
        Map<String,String> map = new HashMap<>();
        presenter.get(Api.FINDSHOPPING_URL,map,QueryShoppingCartBean.class);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    public void dcc(String s){

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.check_shoppin_box, R.id.btn_shoppin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_shoppin_box:

                break;
            case R.id.btn_shoppin:
//                Intent intent = new Intent();
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSuccessful(Object data) {
        if (data instanceof QueryShoppingCartBean);
        QueryShoppingCartBean bean = (QueryShoppingCartBean) data;
        String message = bean.getMessage();
        String status = bean.getStatus();
        if (status.equals("0000")){
            List<QueryShoppingCartBean.ResultBean> list = bean.getResult();
            MyShoppinAdapter adapter = new MyShoppinAdapter(getActivity(),list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rexyShoppin.setLayoutManager(linearLayoutManager);
            rexyShoppin.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onEsert(String e) {
        Log.e("------",e);
    }
}
