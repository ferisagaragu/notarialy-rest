package org.pechblenda.notarialyrest.service

import org.pechblenda.notarialyrest.entity.User
import org.pechblenda.notarialyrest.repository.IUserRepository
import org.pechblenda.notarialyrest.service.`interface`.IUserService
import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.service.enum.IdType
import org.pechblenda.service.helper.EntityParse

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
	private val userRepository: IUserRepository,
	private val response: Response
): IUserService {

	@Transactional
	override fun updateUser(request: Request): ResponseEntity<Any> {
		val user = request.merge<User>(
			EntityParse(
				"uuid",
				userRepository,
				IdType.UUID
			)
		)

		return response.ok()
	}

}