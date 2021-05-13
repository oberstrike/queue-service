package de.ma.rest.util

import io.quarkus.runtime.annotations.RegisterForReflection

interface KeycloakAdminService {
    fun deleteByName(username: String)
    fun deleteById(id: String)
    fun register(username: String, password: String, email: String)
    fun getUserIdByUsername(username: String): UserDTO?
    fun resetPassword(email: String, newPassword: String)
    fun addRoleToUserById(role: String, id: String)
    fun hasRoleByUserId(role: String, id: String): Boolean
}

@RegisterForReflection
data class UserDTO(
    var id: String = "",
    var username: String = "",
    var email: String? = ""
)
