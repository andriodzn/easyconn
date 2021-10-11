package org.androidzn.easyconn;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;

import org.androidzn.easyconn.impl.EasyFacade;


/**
 * Easy Singleton
 */
public class Easy {

    private static final EasyFacade facade = new EasyFacade();

    /**
     * 添加 EasyFeature 实现
     */
    public static void use(EasyFeature feature) {
        facade.use(feature);
    }

    /**
     * 在 Application#onCreate() 这里调用 [Optional]
     */
    public static void startup(Application app) {
        facade.startup(app);
    }

    /**
     * 在 Activity#onCreate() 调用
     */
    public static void startup(Activity activity) {
        facade.startup(activity);
    }

    /**
     * 在 Activity#onDestroy() 调用
     * @param activity
     */
    public static void shutdown(Activity activity) {
        facade.shutdown(activity);
    }

    /**
     * 在 Service#onCreate() 调用
     */
    public static void startup(Service service) {
        facade.startup(service);
    }

    /**
     * 在 Service#onDestroy() 调用
     */
    public static void shutdown(Service service) {
        facade.shutdown(service);
    }

    /**
     * 在已调用过 Easy#startup() 的组件内部使用该方法
     * @param context
     * @return
     */
    public static EasyResolver from(Context context) {
        return facade.from(context);
    }
}
