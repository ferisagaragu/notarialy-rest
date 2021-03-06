package org.pechblenda.notarialyrest.service

import org.pechblenda.exception.BadRequestException
import org.pechblenda.notarialyrest.entity.Client
import org.pechblenda.notarialyrest.entity.User
import org.pechblenda.notarialyrest.repository.IClientRepository
import org.pechblenda.notarialyrest.security.IAuthRepository
import org.pechblenda.notarialyrest.service.`interface`.IClientService
import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.service.enum.IdType
import org.pechblenda.service.helper.EntityParse
import org.pechblenda.service.helper.Validation
import org.pechblenda.service.helper.ValidationType
import org.pechblenda.service.helper.Validations
import org.pechblenda.style.CategoryColor
import org.pechblenda.style.Color

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.UUID

@Service
class ClientService(
	private val clientRepository: IClientRepository,
	private val authRepository: IAuthRepository,
	private val response: Response,
	private val color: Color
): IClientService {

	@Transactional(readOnly = true)
	override fun findAllClientsByUserUuid(): ResponseEntity<Any> {
		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get() as User

		val clients = clientRepository.findAllByUserUuid(user.uuid)

		return response.toListMap(clients).ok()
	}

	@Transactional
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
					"motherSurname",
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

		client.color = color.getMaterialColor(CategoryColor.MATERIAL_500).background
		client.user = user

		return response.toMap(
			clientRepository.save(client)
		).created()
	}

	@Transactional
	override fun updateClient(request: Request): ResponseEntity<Any> {
		println(request)
		val client = request.merge<Client>(
			EntityParse(
				"uuid",
				clientRepository,
				IdType.UUID
			),
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
					"motherSurname",
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

		return response.toMap(client).ok()
	}

	@Transactional
	override fun deleteClient(uuid: UUID): ResponseEntity<Any> {
		val client = clientRepository.findById(uuid).orElseThrow {
			BadRequestException("Upps no se encuentra el cliente")
		}

		clientRepository.delete(client)

		return response.ok()
	}

}