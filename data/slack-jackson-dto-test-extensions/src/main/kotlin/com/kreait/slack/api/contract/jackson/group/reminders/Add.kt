package com.kreait.slack.api.contract.jackson.group.reminders

import com.kreait.slack.api.contract.jackson.common.InstantSample
import com.kreait.slack.api.contract.jackson.group.reminders.common.Reminder

fun RemindersAddRequest.Companion.sample(): RemindersAddRequest {
    return RemindersAddRequest("", InstantSample.sample(), "")
}

fun SuccessfulRemindersAddResponse.Companion.sample(): SuccessfulRemindersAddResponse {
    return SuccessfulRemindersAddResponse(true, Reminder.sample())
}

fun ErrorRemindersAddResponse.Companion.sample(): ErrorRemindersAddResponse {
    return ErrorRemindersAddResponse(true, "")
}


