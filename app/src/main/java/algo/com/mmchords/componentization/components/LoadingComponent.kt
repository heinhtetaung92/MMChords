package sg.carro.dealers.componentization.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import io.reactivex.Observable
import algo.com.mmchords.componentization.UIComponent
import algo.com.mmchords.componentization.components.uiViews.LoadingView
import algo.com.mmchords.componentization.eventTypes.ScreenStateEvent
import algo.com.mmchords.utils.EventBusFactory

@SuppressLint("CheckResult")
open class LoadingComponent(container: ViewGroup, bus: EventBusFactory) : UIComponent<Unit> {
    override fun getContainerId(): Int {
        return uiView.containerId
    }

    override fun getUserInteractionEvents(): Observable<Unit> {
        return Observable.empty()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val uiView = initView(container)

    open fun initView(container: ViewGroup): LoadingView {
        return LoadingView(container)
    }

    init {
        bus.getSafeManagedObservable(ScreenStateEvent::class.java)
            .subscribe {
                when (it) {
                    ScreenStateEvent.Loading -> {
                        uiView.show()
                    }
                    else -> {
                        uiView.hide()
                    }
                }
            }
    }
}
