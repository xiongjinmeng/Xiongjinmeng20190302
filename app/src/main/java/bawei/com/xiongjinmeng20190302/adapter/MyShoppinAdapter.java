package bawei.com.xiongjinmeng20190302.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bawei.com.xiongjinmeng20190302.R;
import bawei.com.xiongjinmeng20190302.activity.DetailsActivity;
import bawei.com.xiongjinmeng20190302.bean.QueryShoppingCartBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 10:10
 * @
 */
public class MyShoppinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<QueryShoppingCartBean.ResultBean> list;
    private final Context context;

    public MyShoppinAdapter(Context context, List<QueryShoppingCartBean.ResultBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shoppin_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int commodityId = list.get(i).getCommodityId();
        String commodityName = list.get(i).getCommodityName();
        int count = list.get(i).getCount();
        String pic = list.get(i).getPic();
        double price = list.get(i).getPrice();
        if (viewHolder instanceof Homder){
            Homder homder = (Homder) viewHolder;
            homder.simpImage.setImageURI(Uri.parse(pic));
            homder.textListTitel.setText(commodityName);
            homder.textListPrice.setText("￥"+price);
            homder.textListNum.setText(""+count);
            homder.simpImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(""+commodityId);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Homder extends RecyclerView.ViewHolder {
        @BindView(R.id.check_shoppin_list)
        CheckBox checkShoppinList;
        @BindView(R.id.simp_image)
        SimpleDraweeView simpImage;
        @BindView(R.id.text_list_titel)
        TextView textListTitel;
        @BindView(R.id.text_list_price)
        TextView textListPrice;
        @BindView(R.id.text_list_jian)
        TextView textListJian;
        @BindView(R.id.text_list_num)
        TextView textListNum;
        @BindView(R.id.text_list_jia)
        TextView textListJia;
        public Homder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
