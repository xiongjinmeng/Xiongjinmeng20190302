package bawei.com.xiongjinmeng20190302.event;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 12:01
 * @
 */
public class DetailsEvemt {
    public String commodityId;

    public DetailsEvemt(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
}
