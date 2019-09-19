package sg.carro.dealers.componentization.components.uiViews

import algo.com.mmchords.Objects.Song
import algo.com.mmchords.R
import algo.com.mmchords.SongRecyclerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import algo.com.mmchords.componentization.UIView
import algo.com.mmchords.componentization.eventTypes.UserInteractionEvent
import algo.com.mmchords.utils.EventBusFactory
import androidx.recyclerview.widget.DividerItemDecoration

class SongsListingView(container: ViewGroup, private val mBus: EventBusFactory) : UIView<UserInteractionEvent>(container) {

    private val mView: View = LayoutInflater.from(container.context).inflate(R.layout.auction_listing_view, container, true)

    private val mRecyclerView: RecyclerView
    private val mSwipeToRefresh: SwipeRefreshLayout
    private var mLayoutManager: LinearLayoutManager? = null

    internal lateinit var adapter: SongRecyclerAdapter


    override val containerId: Int = 0

    init {
        mRecyclerView = mView.findViewById(R.id.recycler_view)
        mSwipeToRefresh = mView.findViewById(R.id.swipe_refresh_layout)

        setupRecyclerView()
        setListeners()
    }

    private fun setupRecyclerView() {

        adapter = SongRecyclerAdapter()
        mRecyclerView.layoutManager = LinearLayoutManager(container.context, RecyclerView.VERTICAL, false)
        mRecyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(mRecyclerView.getContext(),
                RecyclerView.VERTICAL)
        mRecyclerView.addItemDecoration(dividerItemDecoration)

    }

    private fun showAuctionDetail(auctionId: String): Boolean {
        //todo show detail page
        return true
    }

    private fun setListeners() {

        //todo listen for endless scroll

        mSwipeToRefresh.setOnRefreshListener {
            mBus.emit(
                    UserInteractionEvent::class.java,
                    UserInteractionEvent.Refresh
            )
        }

    }

    fun setDataSet(songs: MutableList<Song>) {
        mSwipeToRefresh.isRefreshing = false
        adapter.set(songs)
    }

    fun appendDataSet(songs: MutableList<Song>) {
        adapter.addAll(songs)
    }

    fun showLoadMore(willShow: Boolean) {
        //todo show load more progress
    }

    override fun show() {
        mView.visibility = View.VISIBLE
    }

    override fun hide() {
        mView.visibility = View.GONE
    }

}
