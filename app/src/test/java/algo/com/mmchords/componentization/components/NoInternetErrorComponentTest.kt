package algo.com.mmchords.componentization.components

import algo.com.mmchords.componentization.components.uiViews.NoInternetErrorView
import algo.com.mmchords.componentization.eventTypes.ScreenStateEvent
import algo.com.mmchords.utils.EventBusFactory
import android.app.Application
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import sg.carro.dealers.componentization.components.NoInternetErrorComponent

@RunWith(RobolectricTestRunner::class)
class NoInternetErrorComponentTest {
    private lateinit var component : NoInternetErrorComponent
    private val owner = mock<LifecycleOwner> {
        on { lifecycle } doReturn mock()
    }

    @Before
    fun setUp() {
        component = NoInternetErrorComponentMock(mock(), EventBusFactory.get(owner))
    }


    @Test
    fun testLoading() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Loading)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testLoaded() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Loaded)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testError() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Error)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testEmpty() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.Empty)
        Mockito.verify(component.uiView, Mockito.times(1)).hide()
        Mockito.verify(component.uiView, Mockito.times(0)).show()
    }

    @Test
    fun testNoInternet() {
        EventBusFactory.get(owner).emit(ScreenStateEvent::class.java, ScreenStateEvent.NoInternet)
        Mockito.verify(component.uiView, Mockito.times(0)).hide()
        Mockito.verify(component.uiView, Mockito.times(1)).show()
    }

    @Test
    fun getContainerId() {
        val fakeId = 12345
        val diffId = 54321
        Mockito.`when`(component.uiView.containerId).thenReturn(fakeId)
        Assert.assertEquals(component.getContainerId(), fakeId)
        Assert.assertNotEquals(component.getContainerId(), diffId)
    }

    @Test
    fun initView_shouldReturnLoadingView() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewGroup = LinearLayout(application.baseContext)
        val noInternetComponent = NoInternetErrorComponent(viewGroup, EventBusFactory.get(owner))
        Assert.assertNotNull(noInternetComponent.uiView)
    }


}

class NoInternetErrorComponentMock(container: ViewGroup, busFactory: EventBusFactory) : NoInternetErrorComponent(container, busFactory) {
    override fun initView(container: ViewGroup, bus: EventBusFactory): NoInternetErrorView {
        return mock()
    }
}