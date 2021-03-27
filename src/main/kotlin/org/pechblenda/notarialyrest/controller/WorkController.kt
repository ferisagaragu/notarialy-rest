package org.pechblenda.notarialyrest.controller

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.WorkService
import org.pechblenda.service.Request
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@CrossOrigin(methods = [
	RequestMethod.POST
])
@RestController
@RequestMapping(name = "Work", value = ["/rest/works"])
class WorkController(
	private val workService: WorkService,
	private val httpExceptionResponse: HttpExceptionResponse
) {

	@PostMapping("/save-group")
	fun saveGroupWorks(
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			workService.saveGroupWorks(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PutMapping
	fun put(
		@RequestBody request: List<Request>
	) {
		println(request)
	}

}