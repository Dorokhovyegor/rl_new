package com.nullit.rtg.ui.state

data class Message(val message: String?, val messageType: MessageType)
sealed class MessageType{

    object Snackbar : MessageType()

    object Dialog : MessageType()

    object None : MessageType()
}

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun toString(): String {
        return "Event(content=$content, hasBeenHandled=$hasBeenHandled)"
    }

    companion object{

        private val TAG: String = "AppDebug"

        // we don't want an event if the data is null
        fun <T> dataEvent(data: T?): Event<T>?{
            data?.let {
                return Event(it)
            }
            return null
        }

        // we don't want an event if the response is null
        fun responseEvent(response: Message?): Event<Message>?{
            response?.let{
                return Event(response)
            }
            return null
        }
    }


}