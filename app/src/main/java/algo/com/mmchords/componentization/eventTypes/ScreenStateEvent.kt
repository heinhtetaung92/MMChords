package algo.com.mmchords.componentization.eventTypes

import algo.com.mmchords.componentization.ComponentEvent

/**
 * List of all events this Screen can emit
 */
sealed class ScreenStateEvent : ComponentEvent() {
    object Loading : ScreenStateEvent()
    object Loaded : ScreenStateEvent()
    object Error : ScreenStateEvent()
    object Empty: ScreenStateEvent()
    object NoInternet: ScreenStateEvent()
}
