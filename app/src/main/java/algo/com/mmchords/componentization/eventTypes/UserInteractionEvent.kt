package algo.com.mmchords.componentization.eventTypes

import algo.com.mmchords.componentization.ComponentEvent

/**
 * List of all events Views can emit
 */
sealed class UserInteractionEvent : ComponentEvent() {
    object Retry: UserInteractionEvent()
    object Refresh: UserInteractionEvent()
    object LoadMore: UserInteractionEvent()
}