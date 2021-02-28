package org.pechblenda.notarialyrest.security

import java.util.Optional
import java.util.UUID

import org.pechblenda.auth.entity.IUser
import org.pechblenda.auth.repository.IAuthRepository
import org.pechblenda.notarialyrest.entity.User

import org.springframework.data.jpa.repository.Query

interface IAuthRepository: IAuthRepository<User, UUID> {

	override fun findByUserName(userName: String): Optional<IUser>

	override fun findByActivatePassword(activatePassword: UUID?): Optional<IUser>

	override fun existsByUserName(userName: String): Boolean

	override fun existsByEmail(email: String): Boolean

	override fun existsByActivatePassword(activatePassword: UUID): Boolean

	@Query(
		"select user from User user where " +
			"user.email = :userName or user.userName = :userName"
	)
	override fun findByUserNameOrEmail(userName: String): Optional<IUser>

}