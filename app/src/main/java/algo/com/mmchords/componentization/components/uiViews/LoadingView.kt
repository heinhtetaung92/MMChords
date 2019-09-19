package algo.com.mmchords.componentization.components.uiViews

import algo.com.mmchords.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import algo.com.mmchords.componentization.UIView
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent

class LoadingView(container: ViewGroup) : UIView<UserInteractionEvent>(container) {
    private val view: View =
        LayoutInflater.from(container.context).inflate(R.layout.loading_view, container, true)
            .findViewById(R.id.loading_spinner)

    override val containerId: Int = view.id

    fun getVisibility() : Int {
        return view.visibility
    }

    override fun show() {
        view.visibility = View.VISIBLE
    }

    override fun hide() {
        view.visibility = View.GONE
    }
}
