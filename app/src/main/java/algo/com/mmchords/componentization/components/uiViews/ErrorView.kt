package sg.carro.dealers.componentization.components.uiViews

import algo.com.mmchords.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import algo.com.mmchords.componentization.UIView
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.EventBusFactory

class ErrorView(container: ViewGroup, eventBusFactory: EventBusFactory) :
    UIView<UserInteractionEvent>(container) {
    private val view: View =
        LayoutInflater.from(container.context).inflate(R.layout.error_layout, container, true)
            .findViewById(R.id.error_container)

    override val containerId: Int = 0

    init {
        view.findViewById<Button>(R.id.button)
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