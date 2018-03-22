package com.han.hanmaxmin.common.greendao.helper;

import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.utils.HanHelper;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ptxy on 2018/3/21.
 */

public class DataBaseUserInfoHelper {
    private static DataBaseUserInfoHelper helper;

    private DataBaseUserInfoHelper() {

    }

    public void insert(UserInfo... info){
        if(info != null && info.length > 0){
            insert(Arrays.asList(info));
        }
    }

    public void insert(List<UserInfo> list) {
        if(list != null){
            for (UserInfo model : list){
                insert(model);
            }
        }
    }

    public void insert(UserInfo modelProduct){
        if(modelProduct != null) getDao().insert(modelProduct);
    }

    public void insertOrReplace(UserInfo ... info){
        if(info != null && info.length > 0 ){
            insertOrReplace(Arrays.asList(info));
        }
    }

    public void insertOrReplace(List<UserInfo> list) {
        if(list != null && !list.isEmpty()){
            for (UserInfo info : list){
                insertOrReplace(info);
            }
        }
    }

    public void insertOrReplace(UserInfo info){
        if(info != null)getDao().insertOrReplace(info);
    }

    public void delete(UserInfo info){
        if(info != null)getDao().delete(info);
    }

    public void delete(UserInfo ... info){
        if(info != null && info.length > 0){
            getDao().deleteInTx(info);
        }
    }

    public void delete(List<UserInfo> list){
        if(list != null && !list.isEmpty()){
            getDao().deleteInTx(list);
        }
    }

    public void update(UserInfo userInfo){
        if(userInfo != null)getDao().update(userInfo);
    }

    public void update(UserInfo... infoArr ){
        if (infoArr != null && infoArr.length > 0) {
            getDao().updateInTx(infoArr);
        }
    }

    public void update(Iterable<UserInfo> iterable) {
        getDao().updateInTx(iterable);
    }


    public UserInfo queryByCurrentUser(Property property, Object value) {
                return getDao().queryBuilder().where(property.eq(value)).unique();//
    }

    public List<UserInfo> queryUser (Property property, Object value){
        return getDao().queryBuilder().where(UserInfoDao.Properties.Age.eq("18")).list();
    }







    private void queryAllData() {

    }

    public static DataBaseUserInfoHelper getInstance(){
        if(helper == null){
            synchronized (DataBaseUserInfoHelper.class){
                if(helper == null){
                    helper = new DataBaseUserInfoHelper();
                }
            }
        }
        return helper;
    }


    private UserInfoDao getDao(){
        return DataBaseHelper.getInstance().getDaoSession(HanHelper.getInstance().getApplication()).getUserInfoDao();
    }
}
