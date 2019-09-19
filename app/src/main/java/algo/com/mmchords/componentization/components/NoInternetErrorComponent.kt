package sg.carro.dealers.componentization.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import io.reactivex.Observable
import algo.com.mmchords.componentization.UIComponent
import algo.com.mmchords.componentization.components.uiViews.NoInternetErrorView
import algo.com.mmchords.componentization.eventTypes.ScreenStateEvent
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.EventBusFactory

@SuppressLint("CheckResult")
open class NoInternetErrorComponent(container: ViewGroup, private val bus: EventBusFactory) : UIComponent<UserInteractionEvent> {

    override fun getContainerId(): Int {
        return uiView.containerId
    }

    override fun getUserInteractionEvents(): Observable<UserInteractionEvent> {
        return bus.getSafeManagedObservable(UserInteractionEvent::class.java)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val uiView = initView(container, bus)

    open fun initView(container: ViewGroup, bus: EventBusFactory): NoInternetErrorView {
        return NoInternetErrorView(container, bus)
    }

    init {
        bus.getSafeManagedObservable(ScreenStateEvent::class.java)
                .subscribe {
                    when (it) {
                        ScreenStateEvent.NoInternet -> {
                            uiView.show()
                        }
                        else -> {
                            uiView.hide()
                        }
                    }
                }
    }


}