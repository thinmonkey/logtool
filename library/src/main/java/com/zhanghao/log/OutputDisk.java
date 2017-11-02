package com.zhanghao.log;

/**
 * Created by zhanghao.
 * 2017/10/8-14:17
 */

public class OutputDisk extends OutputStrategyImp {

    private DiskWriteUtil diskWriteUtil;

    public OutputDisk(String filePath, int maxSize) {
        formatStrategy = CsvFormatStrategy.newBuilder().build();
        diskWriteUtil = new DiskWriteUtil(filePath, maxSize);
    }

    public void setDiskWriteUtil(DiskWriteUtil diskWriteUtil) {
        this.diskWriteUtil = diskWriteUtil;
    }

    @Override
    public void log(LogBean logBean) {
        LogBean output = formatStrategy.covertMessage(logBean);
        diskWriteUtil.log(output.getPriority(), output.getContent());
    }
}
