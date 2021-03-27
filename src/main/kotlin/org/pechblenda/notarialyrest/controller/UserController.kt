package org.pechblenda.notarialyrest.controller

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.IUserService
import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@CrossOrigin(methods = [
	RequestMethod.PUT
])
@RestController
@RequestMapping(name = "Users", value = ["/rest/users"])
class UserController(
	private val userService: IUserService,
	private val httpExceptionResponse: HttpExceptionResponse
) {

	@PutMapping
	fun updateQuote(
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			userService.updateUser(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

}