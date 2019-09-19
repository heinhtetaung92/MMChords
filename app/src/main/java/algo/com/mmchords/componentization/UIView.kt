package algo.com.mmchords.componentization

import android.view.ViewGroup
import androidx.annotation.IdRes

abstract class UIView<T>(val container: ViewGroup) {
    /**
     * Get the XML id for the IUIView
     */
    @get:IdRes
    abstract val containerId: Int

    /**
     * Show the IUIView
     */
    abstract fun show()

    /**
     * Hide the IUIView
     */
    abstract fun hide()

}