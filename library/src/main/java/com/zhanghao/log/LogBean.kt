package com.zhanghao.log

import java.util.ArrayList

/**
 * Created by zhanghao.
 * 2017/10/8-13:46
 */
data class LogBean(var time: Long = 0,
                   var tag: String? = null,
                   var keyword: String? = null,
                   var priority: Int = 0,
                   var content: String? = null,
                   var wrapContent: ArrayList<String>? = null,
                   var stackTraceElement: Array<StackTraceElement>? = null){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogBean
        val stackTraceElement = other.stackTraceElement
        if (time != other.time) return false
        if (tag != other.tag) return false
        if (keyword != other.keyword) return false
        if (priority != other.priority) return false
        if (content != other.content) return false
        if (wrapContent != other.wrapContent) return false
        if (stackTraceElement != null) {
            if (other.stackTraceElement == null) return false
            if (!stackTraceElement?.contentEquals(stackTraceElement)) return false
        } else if (other.stackTraceElement != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time.hashCode()
        result = 31 * result + (tag?.hashCode() ?: 0)
        result = 31 * result + (keyword?.hashCode() ?: 0)
        result = 31 * result + priority
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (wrapContent?.hashCode() ?: 0)
        result = 31 * result + (stackTraceElement?.contentHashCode() ?: 0)
        return result
    }

//    var time: Long = 0
//    var tag: String? = null
//    var keyword: String? = null
//    var priority: Int = 0
//    var content: String? = null
//    var wrapContent: ArrayList<String>? = null
//    var stackTraceElement: Array<StackTraceElement>? = null

}


