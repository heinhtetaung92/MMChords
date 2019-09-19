package algo.com.mmchords.componentization.components

import algo.com.mmchords.Constants
import algo.com.mmchords.Objects.Song
import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import io.reactivex.Observable
import algo.com.mmchords.componentization.UIComponent
import algo.com.mmchords.componentization.eventTypes.RequestDataEvent

import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.DeviceUtil
import algo.com.mmchords.utils.EventBusFactory
import com.google.firebase.firestore.FirebaseFirestore
import sg.carro.dealers.componentization.components.uiViews.SongsListingView
import java.util.ArrayList
import java.util.HashMap

@SuppressLint("CheckResult")
open class SongsListingComponent(container: ViewGroup, private val bus: EventBusFactory) : UIComponent<UserInteractionEvent> {

    companion object {
        /**
         * Component loading auctions
         */
        const val STATE_LOADING = 1
        /**
         * Component already loaded auctions
         */
        const val STATE_LOADED = 2
        /**
         * There is not auctions to show
         */
        const val STATE_EMPTY = 3
        /**
         * Error in loading auctions from Algolia
         */
        const val STATE_ERROR = 4
        /**
         * Error in loading auctions from Algolia
         */
        const val STATE_NO_INTERNET = 5

    }
    private var mChangeListener: UIComponent.EventListener? = null
    private var title: String? = "မျှော်လင့်"

    override fun getContainerId(): Int {
        return uiView.containerId
    }

    override fun getUserInteractionEvents(): Observable<UserInteractionEvent> {
        return bus.getSafeManagedObservable(UserInteractionEvent::class.java)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val uiView = initView(container, bus)

    open fun initView(container: ViewGroup, bus: EventBusFactory): SongsListingView {
        return SongsListingView(container, bus)
    }

    fun addListener(changeListener: UIComponent.EventListener) {
        this.mChangeListener = changeListener
    }

    init {

        bus.getSafeManagedObservable(RequestDataEvent::class.java)
                .subscribe {
                    when (it) {
                        RequestDataEvent.Fetch -> {
                            fetchData()
                        }
                        RequestDataEvent.Refresh-> {
                            refreshData()
                        }
                        RequestDataEvent.LoadMore -> {
                            loadMoreData()
                        }
                    }
                }

    }

    private fun fetchData() {
        if (!DeviceUtil.isNetworkConnected()) {
            mChangeListener?.onComponentStateChanged(STATE_NO_INTERNET)
        } else {
            mChangeListener?.onComponentStateChanged(STATE_LOADING)
            searchSongsByTitle(isNewRequest = true, isLoadMore = false)
        }
    }

    private fun refreshData() {
        searchSongsByTitle(isNewRequest = false, isLoadMore = false)
    }

    private fun loadMoreData() {
        uiView.showLoadMore(true)
        searchSongsByTitle(isNewRequest = false, isLoadMore = true)
    }

    fun searchSongsByTitle(isNewRequest: Boolean, isLoadMore: Boolean) {

        // Access a Cloud Firestore instance from your Activity
        val db = FirebaseFirestore.getInstance()
        db.collectionGroup(Constants.KEY_SONGS)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result == null) {
                            if (isNewRequest) {
                                mChangeListener?.onComponentStateChanged(STATE_EMPTY)
                            }
                        } else {

                            uiView.show()

                            val songs = ArrayList<Song>()
                            for (document in task.result!!) {
                                val singer = document.get("singer")
                                var singerName = ""
                                if (singer is HashMap<*, *>) {
                                    singerName = singer["name"] as String
                                }
                                songs.add(Song(document.id, document.getString("title"), singerName))
                            }

                            if (isLoadMore) {
                                uiView.showLoadMore(false)
                                uiView.appendDataSet(songs)
                            } else {
                                uiView.setDataSet(songs)
                            }

                            mChangeListener?.onComponentStateChanged(STATE_LOADED)

                        }
                    } else {
                        //error
                        if (isNewRequest)
                            mChangeListener?.onComponentStateChanged(STATE_ERROR)
                    }
                }

    }

}


