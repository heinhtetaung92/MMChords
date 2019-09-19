package algo.com.mmchords.componentization.components.uiViews

import android.app.Application
import android.view.View
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoadingViewTest {

    lateinit var loadingView: LoadingView

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewGroup = LinearLayout(application.baseContext)
        loadingView = LoadingView(viewGroup)
    }

    @Test
    fun show_shouldVisibleView() {
        loadingView.show()
        assertEquals(loadingView.getVisibility(), View.VISIBLE)
        assertNotEquals(loadingView.getVisibility(), View.INVISIBLE)
        assertNotEquals(loadingView.getVisibility(), View.GONE)
    }

    @Test
    fun test_shouldRemoveView() {
        loadingView.hide()
        assertEquals(loadingView.getVisibility(), View.GONE)
        assertNotEquals(loadingView.getVisibility(), View.VISIBLE)
    }

}