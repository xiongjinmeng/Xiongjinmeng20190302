package bawei.com.xiongjinmeng20190302.frngment;


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
import bawei.com.xiongjinmeng20190302.event.ShoppinEvent;
import bawei.com.xiongjinmeng20190302.presenter.Presenter;
import bawei.com.xiongjinmeng20190302.util.Api;
import bawei.com.xiongjinmeng20190302.view.IView;
import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.text1)
    TextView text1;
    private View view;
    private Presenter presenter;
    private double pter = 0.00;
    private List<QueryShoppingCartBean.ResultBean> list;
    private MyShoppinAdapter adapter;

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
        Map<String, String> map = new HashMap<>();
        presenter.get(Api.FINDSHOPPING_URL, map, QueryShoppingCartBean.class);
        EventBus.getDefault().register(this);
        checkShoppinBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pter = 0.00;
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setIscik(checkShoppinBox.isChecked());
                        double price = list.get(i).getPrice();
                        int count = list.get(i).getCount();
                        double p = price * count;
                        pter += p;
                    }
                    if (checkShoppinBox.isChecked()) {
                        textShoppinPrice.setText("￥" + pter);
                    } else {
                        pter = 0.00;
                        textShoppinPrice.setText("￥" + pter);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btnShoppin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "去结算", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void dccd(ShoppinEvent event) {
        int st = event.st;
        if (st == 1) {
            getPrice();
        } else if (st == 2) {
            checkShoppinBox.setChecked(false);
            getPrice();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    private void getPrice() {
        pter = 0.00;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                boolean check = list.get(i).isIscik();
                if (check) {
                    double price = list.get(i).getPrice();
                    int count = list.get(i).getCount();
                    double p = price * count;
                    pter += p;
                }
//                Log.e("----------","12345+6879"+check);
            }

            textShoppinPrice.setText("￥" + pter);
            adapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onSuccessful(Object data) {
        if (data instanceof QueryShoppingCartBean) ;
        QueryShoppingCartBean bean = (QueryShoppingCartBean) data;
        String message = bean.getMessage();
        String status = bean.getStatus();
        if (status.equals("0000")) {
            list = bean.getResult();
            adapter = new MyShoppinAdapter(getActivity(), list);
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
        Log.e("------", e);
    }
}
