package com.han.hanmaxmin.common.greendao1.dao;

import android.content.Context;

import com.han.hanmaxmin.common.greendao.model.DaoSession;
import com.han.hanmaxmin.common.greendao1.helper.DataBaseHelper;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import org.greenrobot.greendao.Property;

import java.util.List;
import java.util.Properties;

/**
 *将所有创建的表格相同的部分封装到这个BaseDao中
 */
public class DataBaseDao<T> {
    public static final String TAG = DataBaseDao.class.getSimpleName();
    public DataBaseHelper helper;
    public DaoSession daoSession;


    public DataBaseDao(Context context) {
        helper = DataBaseHelper.getInstance();
        daoSession = helper.getDaoSession(HanHelper.getInstance().getApplication());
    }


    /**
     * 插入多个对象
     * @param list
     * @return
     */
    public boolean insert(List<T> list){
        if(list != null){
            for (T  t : list){
              return insert(t);
            }
        }
        return false;
    }


    /**
     * 插入单个对象
     */
    public boolean insert(T t){
        boolean flag = false;
        try {
            flag = getDaoSession().insert(t) != -1 ? true:false;
        } catch (Exception e) {
            L.e(TAG, e.toString());
        }
        return flag;
    }

    /**
     * 更新单个对象
     */
    public void update(T t){
        if(null == t){
            return;
        }

        try {
            getDaoSession().update(t);
        } catch (Exception e){
            L.i(TAG,e.getMessage());
        }
    }

    /**
     * 插入数组
     */
    public void update(Class clazz, T... tArr){
        if(null == tArr && tArr.length < 0){
            return;
        }

        try {
            getDaoSession().getDao(clazz).updateInTx(tArr);
        } catch (Exception e){
            L.e(TAG, e.getMessage());
        }
    }

    /**
     * 跟新多个对象
     * @param iterable
     */
    public void update(Iterable<T> iterable, Class clazz){
        if(null == iterable){
            return;
        }

        try {
                getDaoSession().getDao(clazz).updateInTx(iterable);
        } catch (Exception e){
            L.e(TAG, e.getMessage());
        }

    }

    /**
     * 删除单个数据
     * @param t
     */
    public void delete(T t){
        if (null == t){
            return;
        }
       try {
           getDaoSession().delete(t);
       } catch (Exception e){
        L.i(TAG, e.getMessage());
       }

    }

    /**
     * 删除数组数据
     */
    public void delete(Class clazz, T... tArr){
        if(null == tArr && tArr.length < 0){
            return;
        }

        try {
            getDaoSession().getDao(clazz).deleteInTx(tArr);
        } catch (Exception e){
            L.i(TAG, e.getMessage());
        }
    }

    /**
     * 删除集合
     */
    public void delete(Class clazz, List<T> list){
        if(null == list && list.size() < 0){
            return;
        }

        try {
            getDaoSession().getDao(clazz).deleteInTx(list);
        } catch (Exception e){
            L.i(TAG, e.getMessage());
        }

    }

    /**
     * 得到表明
     */
    public String geTabName(Class clazz){
        return getDaoSession().getDao(clazz).getTablename();
    }

    /**
     * 根据主键ID获取数据
     */
    public T queryById(Class clazz, long id){
        return (T) getDaoSession().getDao(clazz).loadByRowId(id);
    }

    /**
     * 根据特定属性的值查询表
     */
    public T queryValue(Class clazz, Property property, String value){
    return (T) getDaoSession().getDao(clazz).queryBuilder().where(property.eq(value)).unique();
    }

    public List<T> queryAll(Class clazz){
        return getDaoSession().getDao(clazz).loadAll();
    }







    private DaoSession getDaoSession(){
       return helper.getDaoSession(HanHelper.getInstance().getApplication());
    }
}
