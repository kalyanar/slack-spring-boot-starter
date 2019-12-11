package com.kreait.slack.broker.store.usergroups

import java.time.Instant

interface UserGroupsStore {

    /**
     * Adds [UserGroup]s to the [UserGroupsStore]
     *
     * @param usersGroups the [UserGroup]s you want to add
     */
    fun put(vararg usersGroups: UserGroup)
}

/**
 * The Usergroup dto
 *
 * @property id the id of the usergroup
 * @property teamId the id of the according team
 * @property isUsergroup will be true
 * @property name the name of this usergroup
 * @property description the description of this usergroup
 * @property handle the handle of the usergroup, unique for all usergroups in that workspace
 * @property isExternal determines if the usergroup was created externally
 * @property dateCreate the date when the usergroup was created
 * @property dateUpdate the date when the usergroup was updated
 * @property dateDelete the date when the usergroup was deleted
 * @property autoType
 * @property createdBy the user-id of the user that created the usergroup
 * @property updatedBy the user-id of the user that updated the usergroup
 * @property deletedBy the user-id of the user that deleted the usergroup
 * @property prefs
 * @property users list of users in that usergroup
 * @property userCount the amount of users in that usergroup
 */
data class UserGroup(
        val id: String,
        val teamId: String,
        val isUserGroup: Boolean,
        val name: String,
        val description: String,
        val handle: String,
        val isExternal: Boolean,
        val createdAt: Instant,
        val updatedAt: Instant,
        val deletedAt: Instant,
        val autoType: AutoType,
        val createdBy: String,
        val updatedBy: String,
        val deletedBy: String?,
        val prefs: Prefs,
        val userIds: List<String>,
        val userCount: Int
) {
    companion object
}

data class Prefs(
        val channelIds: List<String>,
        val groupIds: List<String>
) {
    companion object
}

/**
 * admins for a Workspace Admins group
 * owners for a Workspace Owners group
 * null for a custom group
 * see: https://api.slack.com/types/usergroup
 */
enum class AutoType {
    ADMINS,
    OWNERS,
    NULL
}


fun of(it: com.kreait.slack.api.contract.jackson.group.usergroups.UserGroup): UserGroup {
    return UserGroup(it.id, it.teamId, it.isUserGroup, it.name, it.description, it.handle, it.isExternal,
            it.createdAt, it.updatedAt, it.deletedAt, AutoType.valueOf(it.autoType.name), it.createdBy, it.updatedBy, it.deletedBy,
            Prefs(it.prefs.channelIds, it.prefs.groupIds), it.userIds, it.userCount)
}
