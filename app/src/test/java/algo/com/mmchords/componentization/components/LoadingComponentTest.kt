package algo.com.mmchords.componentization.components

import algo.com.mmchords.componentization.components.uiViews.LoadingView
import algo.com.mmchords.componentization.eventTypes.ScreenStateEvent
import algo.com.mmchords.utils.EventBusFactory
import android.app.Application
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import sg.carro.dealers.componentization.components.LoadingComponent

@RunWith(RobolectricTestRunner::class)
class LoadingComponentTest{
    private lateinit var component : LoadingComponent
    private val owner = mock<LifecycleOwner> {
        on { lifecycle } doReturn mock()
    }

    @Before
    fun setUp() {
        component = LoadingComponentMock(mock(), EventBusFactory.get(owner))
    }

    @Test
    fun testLoading_shouldShowLoadingView() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Loading)
        Mockito.verify(component.uiView, Mockito.times(0)).hide()
        Mockito.verify(component.uiView, Mockito.times(1)).show()
    }

    @Test
    fun testLoaded_showNotShowLoadingView() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Loaded)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testError_showNotShowLoadingView() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Error)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testEmpty_showNotShowLoadingView() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Empty)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testNoInternet_showNotShowLoadingView() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.NoInternet)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun getContainerId() {
        val fakeId = 12345
        val diffId = 54321
        `when`(component.uiView.containerId).thenReturn(fakeId)
        assertEquals(component.getContainerId(), fakeId)
        assertNotEquals(component.getContainerId(), diffId)
    }

    @Test
    fun getUserInteractionEvents_shouldReturnEmptyObservable() {
        assertEquals(component.getUserInteractionEvents(), Observable.empty<Unit>())
    }

    @Test
    fun initView_shouldReturnLoadingView() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewGroup = LinearLayout(application.baseContext)
        val loadingComponent = LoadingComponent(viewGroup, EventBusFactory.get(owner))
        assertNotNull(loadingComponent.uiView)
    }

}

class LoadingComponentMock(container: ViewGroup, busFactory: EventBusFactory) : LoadingComponent(container, busFactory) {
    override fun initView(container: ViewGroup): LoadingView {
        return mock()
    }
}