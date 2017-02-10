package com.ivt.android.me.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

//import static com.shanjian.AFiyFrame.utils.app.ActivityManageUtil.ACTIVITY_START_TYPE.SingleTop;

/**
 * 用于管理所有的Activity的加载和退出
 * Created by zhangshuai on 2017-01-11.
 */
public class ActivityManageUtil {
    private static ActivityManageUtil instance = null;
    protected List<Activity_Item> activity_list = new LinkedList<>();

    private ActivityManageUtil() { }

    public static ActivityManageUtil getInstance() {
        if (instance == null) {
            synchronized (ActivityManageUtil.class) {
                if (instance == null) {
                    instance = new ActivityManageUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 将一个activity放入栈内,默认标准启动模式
     *
     * @param activity
     */
    public void pushToStatic(Class activity) { pushToStatic(activity, ACTIVITY_START_TYPE.Standard); }

    /**
     * 将一个activity放入栈内,自行制定启动模式
     *
     * @param activity
     * @param type
     */
    public void pushToStatic(Class activity, ACTIVITY_START_TYPE type) {
        LogUtils.LOGD("当前的模式==" + activity.toString());
        Activity_Item activity_item = new Activity_Item(activity, type);
        int length = instance.activity_list.size();
        boolean isHas = instance.activity_list.contains(activity_item);
        switch (activity_item.type) {
            /**
             * 如果是SingleTop类型，并且添加之前栈顶内已经是同一个Activity，这个时候不需要重复添加该Activity
             */
            case SingleTop:
                if (length > 0) {
                    Activity_Item forward_activity_item = instance.activity_list.get(length - 1);
                    if (forward_activity_item.type == ACTIVITY_START_TYPE.SingleTop && forward_activity_item.acitivity.getSimpleName().equals(activity_item.acitivity.getSimpleName())) {
                        return;
                    }
                }
                break;
            /**
             * 如果是SingleTask类型，如果已经存在该Activity，则把已经存在的Activity在list的后面的Activity全部清除
             */
            case SingleTask:
                if (isHas) {
                    int position = instance.activity_list.indexOf(activity_item);//获取当前位置
                    for (int i = position; i < length; i++) {
                        instance.activity_list.remove(i);
                    }
                    return;
                }
                break;
            /**
             * 如果是SingleInstance类型，如果已经存在该Activity，则把已经存在的Activity在list的位置移到最后一个位置，否则直接添加
             * 采用的是移除List中已经存在的，然后把当前的这个放入
             */
            case SingleInstance:
                if (isHas) {
                    int position = instance.activity_list.indexOf(activity_item);//获取当前位置
                    instance.activity_list.remove(position);
                }
                break;
        }
        instance.activity_list.add(activity_item);
    }

    /**
     * 将一个activity从自定义的栈内activity_list中移除,从栈顶开始查找，只能移除一个activity
     *
     * @param activity
     * @return true :是SingleInstance类型
     */
    private boolean quitFromStack(Class activity) {
        if (activity_list == null || activity_list.size() == 0) return false;
        for (int i = activity_list.size() - 1; i >= 0; i--) {
            if (activity.getSimpleName().equals(activity_list.get(i).acitivity.getSimpleName())) {
                LogUtils.LOGD("移除的==" + activity_list.get(i).acitivity);
                if (activity_list.get(i).type == ACTIVITY_START_TYPE.SingleInstance) {
                    activity_list.remove(i);
                    return true;
                } else {
                    activity_list.remove(i);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 退出当前的activity
     */
    public void finishActivity(Activity now_activity) {
        boolean isSingleInstance = quitFromStack(now_activity.getClass());
        if (isSingleInstance) {

        } else {
            /**
             *  获取当前自定义栈顶的activity
             *  如果是SingleInstance模式，则进行代码启动，
             *  如果不是，则执行now_activity的finish()
             */
            int now_length = activity_list.size();
            int last_position = now_length - 1;
            if (now_length > 0) {
                Activity_Item activity_item = instance.activity_list.get(last_position);
                if (activity_item.type == ACTIVITY_START_TYPE.SingleInstance) {
                    Intent intent = new Intent(now_activity, activity_item.acitivity);
                    now_activity.startActivity(intent);
                }
            }
        }


    }

    /**
     * 清除所有存放的数据
     */
    public void finishAllActivity() {
        int lentgh = instance.activity_list.size();
        if (instance.activity_list != null && lentgh > 0) {
            instance.activity_list.clear();
            instance.activity_list = null;
        }
    }

    /**
     * 判断当前是否还有activity
     *
     * @return
     */
    public boolean isTaskNull() {
        if (instance.activity_list == null || instance.activity_list.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断传入的Activity在堆栈里是否已经存在
     *
     * @param ActivityCls
     * @return
     */
    public boolean isHasActivity(Class ActivityCls) {
        if (activity_list == null) {
            return false;
        }
        String clasName = ActivityCls.getName();
        for (int i = 0; i < activity_list.size(); i++) {
            if (activity_list.get(i).acitivity.getName().equals(clasName)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 当前存放的activity的实体类
     */
    class Activity_Item {
        Class acitivity;
        ACTIVITY_START_TYPE type;

        public Activity_Item(Class acitivity, ACTIVITY_START_TYPE type) {
            this.acitivity = acitivity;
            this.type = type;
        }

        @Override
        public boolean equals(Object object) {
            if (object != null && object instanceof Activity_Item) {
                boolean isClassSame = this.acitivity.getName().equals(((Activity_Item) object).acitivity.getName());
                boolean isTypeSame = (this.type == ((Activity_Item) object).type);
                if (isClassSame && isTypeSame) {
                    return true;
                }
            }

            return false;
        }
    }

    public enum ACTIVITY_START_TYPE {
        Standard,
        SingleTop,
        SingleTask,
        SingleInstance,
    }
}
