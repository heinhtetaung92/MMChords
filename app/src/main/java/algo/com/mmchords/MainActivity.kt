package algo.com.mmchords

import algo.com.mmchords.componentization.UIComponent
import algo.com.mmchords.componentization.components.ErrorComponent
import algo.com.mmchords.componentization.components.SongsListingComponent
import algo.com.mmchords.componentization.eventTypes.RequestDataEvent
import algo.com.mmchords.componentization.eventTypes.ScreenStateEvent
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.EventBusFactory
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import sg.carro.dealers.componentization.components.LoadingComponent
import sg.carro.dealers.componentization.components.NoInternetErrorComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents(findViewById(R.id.container))

        EventBusFactory.get(this).emit(RequestDataEvent::class.java, RequestDataEvent.Fetch)


    }

    @SuppressLint("CheckResult")
    private fun initComponents(rootViewContainer: ViewGroup) {

        val listingComponent = SongsListingComponent(rootViewContainer, EventBusFactory.get(this))
        listingComponent.addListener(ListingComponentEventListener(EventBusFactory.get(this)))

        LoadingComponent(rootViewContainer, EventBusFactory.get(this))
        NoInternetErrorComponent(rootViewContainer, EventBusFactory.get(this))
        ErrorComponent(rootViewContainer, EventBusFactory.get(this))

        EventBusFactory.get(this).getSafeManagedObservable(UserInteractionEvent::class.java)
                .subscribe {
                    when (it) {
                        UserInteractionEvent.Retry -> {
                            onTabRetry()
                        }
                        UserInteractionEvent.Refresh -> {
                            EventBusFactory.get(this).emit(RequestDataEvent::class.java, RequestDataEvent.Refresh)
                        }
                        UserInteractionEvent.LoadMore -> {
                            EventBusFactory.get(this).emit(RequestDataEvent::class.java, RequestDataEvent.LoadMore)
                        }
                    }

                }

    }

    private fun onTabRetry() {
        EventBusFactory.get(this).emit(RequestDataEvent::class.java, RequestDataEvent.Fetch)
    }

    private inner class ListingComponentEventListener(val eventBusFactory: EventBusFactory) : UIComponent.EventListener {
        override fun onComponentStateChanged(componentState: Int) {
            when (componentState) {
                SongsListingComponent.STATE_LOADING -> {
                    eventBusFactory.emit(ScreenStateEvent::class.java, ScreenStateEvent.Loading)
                }
                SongsListingComponent.STATE_LOADED -> {
                    eventBusFactory.emit(ScreenStateEvent::class.java, ScreenStateEvent.Loaded)
                }
                SongsListingComponent.STATE_EMPTY -> {
                    eventBusFactory.emit(ScreenStateEvent::class.java, ScreenStateEvent.Empty)
                }
                SongsListingComponent.STATE_ERROR -> {
                    eventBusFactory.emit(ScreenStateEvent::class.java, ScreenStateEvent.Error)
                }
                SongsListingComponent.STATE_NO_INTERNET -> {
                    eventBusFactory.emit(ScreenStateEvent::class.java, ScreenStateEvent.NoInternet)
                }
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        return true
    }
}
