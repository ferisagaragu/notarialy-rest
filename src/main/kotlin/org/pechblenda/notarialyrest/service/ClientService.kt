package org.pechblenda.notarialyrest.service

import org.pechblenda.notarialyrest.entity.Client
import org.pechblenda.notarialyrest.entity.User
import org.pechblenda.notarialyrest.repository.IClientRepository
import org.pechblenda.notarialyrest.security.IAuthRepository
import org.pechblenda.notarialyrest.service.`interface`.IClientService
import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.service.helper.Validation
import org.pechblenda.service.helper.ValidationType
import org.pechblenda.service.helper.Validations
import org.pechblenda.util.Color

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ClientService(
	private val clientRepository: IClientRepository,
	private val authRepository: IAuthRepository,
	private val response: Response,
	private val color: Color
): IClientService {

	override fun findAllClientsByUserUuid(): ResponseEntity<Any> {
		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get() as User

		val clients = clientRepository.findAllByUserUuid(user.uuid)

		return response.toListMap(clients).ok()
	}

	override fun createClient(request: Request): ResponseEntity<Any> {
		val client = request.to<Client>(
			Client::class,
			Validations(
				Validation(
					"name",
					"Upps el nombre del cliente es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"surname",
					"Upps el apellido paterno del cliente es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"surname",
					"Upps el apellido materno del cliente es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"address",
					"Upps la dirección del cliente es requerida",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"phoneNumber",
					"Upps el número telefónico del cliente es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				)
			)
		)
		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get() as User

		client.color = color.getHexRandomColor()
		client.user = user

		return response.toMap(
			clientRepository.save(client)
		).created()
	}

}