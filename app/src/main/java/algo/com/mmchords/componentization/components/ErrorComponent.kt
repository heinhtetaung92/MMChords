package algo.com.mmchords.componentization.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import io.reactivex.Observable
import algo.com.mmchords.componentization.UIComponent
import sg.carro.dealers.componentization.components.uiViews.ErrorView
import algo.com.mmchords.componentization.eventTypes.ScreenStateEvent
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.EventBusFactory

@SuppressLint("CheckResult")
open class ErrorComponent(container: ViewGroup, private val bus: EventBusFactory) : UIComponent<UserInteractionEvent> {
    override fun getContainerId(): Int {
        return uiView.containerId
    }

    override fun getUserInteractionEvents(): Observable<UserInteractionEvent> {
        return bus.getSafeManagedObservable(UserInteractionEvent::class.java)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val uiView = initView(container, bus)

    open fun initView(container: ViewGroup, bus: EventBusFactory): ErrorView {
        return ErrorView(container, bus)
    }

    init {
        bus.getSafeManagedObservable(ScreenStateEvent::class.java)
            .subscribe {
                when (it) {
                    ScreenStateEvent.Error -> {
                        uiView.show()
                    }
                    else -> {
                        uiView.hide()
                    }
                }
            }
    }
}
