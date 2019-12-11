package com.kreait.slack.broker.store.usergroups

import com.kreait.slack.api.contract.jackson.event.Event
import com.kreait.slack.api.contract.jackson.event.SlackEvent
import com.kreait.slack.broker.receiver.adapter.TypedEventReceiverAdapter
import com.kreait.slack.broker.store.team.Team
import org.springframework.http.HttpHeaders

class UserGroupCreatedEventReceiver(private val userGroupsStore: UserGroupsStore) : TypedEventReceiverAdapter<Event.SubteamCreated>(Event.SubteamCreated.TYPE) {

    override fun onReceive(slackEvent: SlackEvent<Event.SubteamCreated>, headers: HttpHeaders, team: Team) {
        userGroupsStore.put(of(slackEvent.event.userGroup))
    }

}
