package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-13:49
 */

public interface FormatStrategy {
    LogBean covertMessage(LogBean logBean);
}
