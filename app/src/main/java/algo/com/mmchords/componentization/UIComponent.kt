package algo.com.mmchords.componentization

import io.reactivex.Observable

interface UIComponent<T> {
    fun getContainerId(): Int
    fun getUserInteractionEvents(): Observable<T>

    /**
     * Listener of changes in UIComponent.
     */
    interface EventListener {
        fun onComponentStateChanged(componentState: Int)
    }
}