package algo.com.mmchords.componentization.components.uiViews

import algo.com.mmchords.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import algo.com.mmchords.componentization.UIView
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.EventBusFactory
import android.widget.TextView

class NoInternetErrorView(container: ViewGroup, eventBusFactory: EventBusFactory) :
        UIView<UserInteractionEvent>(container) {
    private val view: View =
            LayoutInflater.from(container.context).inflate(R.layout.view_no_internet, container, true)
                    .findViewById(R.id.no_internet_container)

    override val containerId: Int = 0

    init {
        view.findViewById<TextView>(R.id.btn_retry)
                .setOnClickListener {
                    eventBusFactory.emit(
                            UserInteractionEvent::class.java,
                            UserInteractionEvent.Retry
                    )
                }
    }

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