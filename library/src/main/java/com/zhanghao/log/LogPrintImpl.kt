package com.zhanghao.log

import android.os.Handler
import android.os.Looper
import java.io.FileNotFoundException
import java.lang.Exception


/**
 * Created by zhanghao.
 * 2017/10/8-13:52
 */

class LogPrintImpl private constructor() : LogPrint {

    private var isLogToAndroid = true
    private var isLogToDisk: Boolean = false

    private var outputAndroidLogcat: OutputAndroidLogcat? = null
    private var outputDisk: OutputDisk? = null

    private var diskFilePath: String? = null
    private var diskFileMaxSize: Int = 0
    private var outputAnyAdapter: OutputAnyAdapter? = null

    private val handler = Handler(Looper.getMainLooper())

    init {
        outputAndroidLogcat = OutputAndroidLogcat()
    }

    fun init(builder: Builder) {
        this.isLogToAndroid = builder.isLogToAndroid
        this.isLogToDisk = builder.isLogToDisk
        this.diskFilePath = builder.diskFilePath
        this.diskFileMaxSize = builder.diskFileMaxSize
        //文件目录不存在，则不会创建输出到sd卡的日志
        if (diskFilePath != null) {
            this.outputDisk = OutputDisk(diskFilePath, diskFileMaxSize)
        }
        this.outputAnyAdapter = builder.outputAdapter
    }

    override fun d(tag: String?, message: String?, keyword: String?) {
        val logBean = createLogBean(DEBUG, tag, message, keyword)
        printLog(logBean)
    }

    override fun e(tag: String?, message: String?, keyword: String?) {
        val logBean = createLogBean(ERROR, tag, message, keyword)
        printLog(logBean)
    }

    override fun e(tag: String?, throwable: Throwable?) {
        e(tag, throwable, null)
    }

    override fun e(tag: String?, message: String?, throwable: Throwable?) {
        e(tag, message, throwable, null)
    }


    override fun e(tag: String?, throwable: Throwable?, keyword: String??) {
        e(tag, null, throwable, keyword)
    }

    override fun e(tag: String?, message: String?, throwable: Throwable?, keyword: String?) {
        var message = message
        if (throwable != null && message != null) {
            message += " : " + Utils.getStackTraceString(throwable)
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable)
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL log message"
        }
        val logBean = createLogBean(ERROR, tag, message, keyword)
        printLog(logBean)
    }

    override fun i(tag: String?, message: String?, keyword: String?) {
        val logBean = createLogBean(INFO, tag, message, keyword)
        printLog(logBean)
    }

    override fun w(tag: String?, message: String?, keyword: String?) {
        val logBean = createLogBean(WARN, tag, message, keyword)
        printLog(logBean)
    }

    override fun v(tag: String?, message: String?, keyword: String?) {
        val logBean = createLogBean(VERBOSE, tag, message, keyword)
        printLog(logBean)
    }

    override fun d(tag: String??, message: String?) {
        val logBean = createLogBean(DEBUG, tag, message, null)
        printLog(logBean)
    }

    override fun e(tag: String??, message: String?) {
        val logBean = createLogBean(ERROR, tag, message, null)
        printLog(logBean)
    }

    override fun i(tag: String??, message: String?) {
        val logBean = createLogBean(INFO, tag, message, null)
        printLog(logBean)
    }

    override fun w(tag: String??, message: String?) {
        val logBean = createLogBean(WARN, tag, message, null)
        printLog(logBean)
    }

    override fun v(tag: String?, message: String?) {
        val logBean = createLogBean(VERBOSE, tag, message, null)
        printLog(logBean)
    }

    override fun w(message: String?) {
        w(null, message)
    }

    override fun v(message: String?) {
        v(null, message)
    }

    private fun createLogBean(debug: Int, tag: String?, message: String?, keyword: String?): LogBean {
        val logBean = LogBean()
        logBean.priority = debug
        logBean.tag = tag
        logBean.stackTraceElement = Thread.currentThread().stackTrace
        logBean.time = System.currentTimeMillis()
        logBean.content = message
        logBean.keyword = keyword
        return logBean
    }

    fun setOutputAndroidLogcat(outputAndroidLogcat: OutputAndroidLogcat) {
        this.outputAndroidLogcat = outputAndroidLogcat
    }

    fun setOutputDisk(outputDisk: OutputDisk) {
        this.outputDisk = outputDisk
    }

    fun setOutputAdapter(logAdapter: OutputAnyAdapter) {
        this.outputAnyAdapter = logAdapter
    }

    fun setLogToAndroid(logToAndroid: Boolean) {
        isLogToAndroid = logToAndroid
    }

    fun setLogToDisk(logToDisk: Boolean) {
        isLogToDisk = logToDisk
    }

    private fun printLog(logBean: LogBean) {
        handler.post {
            try {
                if (isLogToAndroid) {
                    outputAndroidLogcat?.log(logBean.copy())
                }
                if (isLogToDisk) {
                    outputDisk?.log(logBean.copy())
                }
                outputAnyAdapter?.log(logBean.copy())
            } catch (e: CloneNotSupportedException) {
                e.printStackTrace()
            }
        }
    }

    override fun d(message: String?) {
        d(null, message)
    }

    override fun e(message: String?) {
        e(null, message)
    }

    override fun i(message: String?) {
        i(null, message)
    }

    class Builder {

        var diskFilePath: String? = null
        var diskFileMaxSize: Int = 0
        var isLogToAndroid = true
        var isLogToDisk: Boolean = false

        var outputAdapter: OutputAnyAdapter? = null

        fun setDiskFilePath(diskFilePath: String): Builder {
            this.diskFilePath = diskFilePath
            return this
        }

        fun setLogOpen(isOpen: Boolean): Builder {
            isLogToAndroid = isOpen
            isLogToDisk = isOpen
            return this
        }

        fun setDiskFileMaxSize(diskFileMaxSize: Int): Builder {
            this.diskFileMaxSize = diskFileMaxSize
            return this
        }

        fun setLogToAndroid(logToAndroid: Boolean): Builder {
            isLogToAndroid = logToAndroid
            return this
        }

        fun setLogToDisk(logToDisk: Boolean): Builder {
            isLogToDisk = logToDisk
            return this
        }

        fun setOutputAdapter(outputAdapter: OutputAnyAdapter): Builder {
            this.outputAdapter = outputAdapter
            return this
        }

    }

    companion object {

        private var logPrintImpl: LogPrintImpl? = null
        /**
         * Priority constant for the println method; use Log.v.
         */
        const val VERBOSE = 2

        /**
         * Priority constant for the println method; use Log.d.
         */
        const val  DEBUG = 3

        /**
         * Priority constant for the println method; use Log.i.
         */
        const val  INFO = 4

        /**
         * Priority constant for the println method; use Log.w.
         */
        const val  WARN = 5

        /**
         * Priority constant for the println method; use Log.e.
         */
        const val  ERROR = 6

        /**
         * Priority constant for the println method.
         */
        const val  ASSERT = 7

        val instance: LogPrintImpl?
            get() {
                if (logPrintImpl == null) {
                    synchronized(LogPrintImpl::class.java) {
                        if (logPrintImpl == null) {
                            logPrintImpl = LogPrintImpl()
                        }
                    }
                }
                return logPrintImpl
            }
    }
}
