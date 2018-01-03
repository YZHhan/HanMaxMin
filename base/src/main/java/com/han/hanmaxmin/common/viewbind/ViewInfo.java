package com.han.hanmaxmin.common.viewbind;

/**
 * @CreateBy Administrator
 * @Date 2017/12/24  22:54
 * @Description   ViewBind 的详情
 *  初步认知 ，这是在区分 findViewById 的 唯一性。
 */

final class ViewInfo {
    public int value;
    public int parentId;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() !=o.getClass()) return false;
        ViewInfo viewInfo= (ViewInfo) o;
        return value == viewInfo.value && parentId ==  viewInfo.parentId;
    }

    @Override public int hashCode() {
        int result =value;
        result = 31*result + parentId;
        return result;
    }


}
