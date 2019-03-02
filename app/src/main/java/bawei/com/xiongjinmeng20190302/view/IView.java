package bawei.com.xiongjinmeng20190302.view;

/**
 * @作者 熊金梦
 * @时间 2019/3/2 0002 9:42
 * @
 */
public interface IView<T> {
    void onSuccessful(T data);
    void onEsert(String e);
}
