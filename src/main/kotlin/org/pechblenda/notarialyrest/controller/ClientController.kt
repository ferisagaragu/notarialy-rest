package org.pechblenda.notarialyrest.controller

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.IClientService
import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@CrossOrigin(methods = [
	RequestMethod.GET,
	RequestMethod.POST
])
@RestController
@RequestMapping(name = "Client", value = ["/rest/clients"])
class ClientController(
	private val clientService: IClientService,
	private val httpExceptionResponse: HttpExceptionResponse
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

}