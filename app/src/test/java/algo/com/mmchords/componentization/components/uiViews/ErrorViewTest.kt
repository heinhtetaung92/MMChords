package algo.com.mmchords.componentization.components.uiViews

import algo.com.mmchords.utils.EventBusFactory
import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import sg.carro.dealers.componentization.components.uiViews.ErrorView

@RunWith(RobolectricTestRunner::class)
class ErrorViewTest {
    private val owner = mock<LifecycleOwner> {
        on { lifecycle } doReturn mock()
    }

    lateinit var errorView: ErrorView

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewGroup = LinearLayout(application.baseContext)
        errorView = ErrorView(viewGroup, EventBusFactory.get(owner))
    }

    @Test
    fun getContainerId() {
        assertEquals(errorView.containerId, 0)
    }

    @Test
    fun show() {
        errorView.show()
        val visibility = errorView.getVisibility()
        assertEquals(visibility, View.VISIBLE)
        assertNotEquals(visibility, View.INVISIBLE)
        assertNotEquals(visibility, View.GONE)
    }

    @Test
    fun hide() {
        errorView.hide()
        assertEquals(errorView.getVisibility(), View.GONE)
        assertNotEquals(errorView.getVisibility(), View.VISIBLE)
    }
}