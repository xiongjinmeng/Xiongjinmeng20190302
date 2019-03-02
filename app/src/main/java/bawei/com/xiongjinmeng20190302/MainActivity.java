package bawei.com.xiongjinmeng20190302;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

import bawei.com.xiongjinmeng20190302.adapter.MyAdapter;
import bawei.com.xiongjinmeng20190302.frngment.OrderFragment;
import bawei.com.xiongjinmeng20190302.frngment.ShoppinFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.radio_shoppin)
    RadioButton radioShoppin;
    @BindView(R.id.radio_order)
    RadioButton radioOrder;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ShoppinFragment());
        fragments.add(new OrderFragment());
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        radioShoppin.setChecked(true);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                switch (i) {
                    case 0:
                        radioShoppin.setChecked(true);
                        break;
                    case 1:
                        radioOrder.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.radio_shoppin, R.id.radio_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_shoppin:
                viewpager.setCurrentItem(0);
                break;
            case R.id.radio_order:
                viewpager.setCurrentItem(1);
                break;
        }
    }
}
