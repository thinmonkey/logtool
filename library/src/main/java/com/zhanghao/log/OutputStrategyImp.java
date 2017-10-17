package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-14:09
 */

public abstract class OutputStrategyImp implements OutputStrategy {

    protected FormatStrategy formatStrategy;

    public void setFormatStrategy(FormatStrategy formatStrategy) {
        this.formatStrategy = formatStrategy;
    }

}
