/*
 * Copyright KhulnaSoft, Ltd.
 */

package com.khulnasoft.intellij

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationFullContent
import com.intellij.openapi.util.NlsContexts.NotificationContent
import com.intellij.openapi.util.NlsContexts.NotificationTitle

class KhulnasoftNotification(
    groupId: String,
    title: @NotificationTitle String,
    content: @NotificationContent String,
    type: NotificationType
) : Notification(groupId, title, content, type), NotificationFullContent