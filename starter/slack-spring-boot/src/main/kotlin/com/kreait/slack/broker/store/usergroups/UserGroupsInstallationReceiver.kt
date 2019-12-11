package com.kreait.slack.broker.store.usergroups

import com.kreait.slack.api.SlackClient
import com.kreait.slack.api.contract.jackson.group.usergroups.ListRequest
import com.kreait.slack.broker.receiver.InstallationReceiver
import com.kreait.slack.broker.store.team.Team
import org.slf4j.LoggerFactory

/**
 * Receiver that stores all usersgroups after a team installs the app
 */
class UserGroupsInstallationReceiver(private val slackClient: SlackClient,
                                     private val userGroupsStore: UserGroupsStore) : InstallationReceiver {

    override fun onReceiveInstallation(code: String, state: String, team: Team) {
        this.slackClient.usergroups().listGroups(team.bot.accessToken)
                .with(ListRequest(includeCount = true, includeDisabled = true, includeUsers = true))
                .onSuccess {
                    this.userGroupsStore.put(*it.userGroups.map { userGroup -> of(userGroup) }.toTypedArray())
                }.onFailure {
                    LOG.error("Failure while trying to load usergroups\n{}", it)
                }.invoke()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(UserGroupsInstallationReceiver::class.java)
    }
}
