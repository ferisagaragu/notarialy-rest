package org.pechblenda.notarialyrest.controller

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.IClientService
import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PutMapping

import java.util.UUID

@CrossOrigin(methods = [
	RequestMethod.GET,
	RequestMethod.POST,
	RequestMethod.PUT,
	RequestMethod.DELETE
])
@RestController
@RequestMapping(name = "Client", value = ["/rest/clients"])
class ClientController(
	private val clientService: IClientService,
	private val httpExceptionResponse: HttpExceptionResponse,
	private val simpMessagingTemplate: SimpMessagingTemplate
) {

	@GetMapping
	fun findAllCompaniesByUserUuid(): ResponseEntity<Any> {
		return try {
			clientService.findAllClientsByUserUuid()
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PostMapping
	fun createClient(
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			clientService.createClient(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PutMapping
	fun updateClient(
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			clientService.updateClient(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@DeleteMapping("/{uuid}")
	fun deleteClient(
		@PathVariable uuid: UUID
	): ResponseEntity<Any> {
		return try {
			clientService.deleteClient(uuid)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@GetMapping("/send")
	fun ds() {
		simpMessagingTemplate.convertAndSend(
			"/notify",
			"hola primo"
		)
		simpMessagingTemplate.convertAndSend(
			"/notify",
			"como has"
		)
		simpMessagingTemplate.convertAndSend(
			"/notify/12",
			"estado"
		)
	}

}