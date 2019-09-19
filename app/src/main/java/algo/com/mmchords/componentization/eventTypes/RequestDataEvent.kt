package algo.com.mmchords.componentization.eventTypes

import algo.com.mmchords.componentization.ComponentEvent


sealed class RequestDataEvent : ComponentEvent() {
    object Fetch : RequestDataEvent()
    object LoadMore : RequestDataEvent()
    object Refresh : RequestDataEvent()
}