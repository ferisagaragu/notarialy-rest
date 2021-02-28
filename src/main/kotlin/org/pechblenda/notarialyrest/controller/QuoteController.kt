package org.pechblenda.notarialyrest.controller

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.IQuoteService
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
	RequestMethod.GET
])
@RestController
@RequestMapping("/rest/quote")
class QuoteController(
	private val quoteService: IQuoteService,
	private val httpExceptionResponse: HttpExceptionResponse
) {

	@PostMapping
	fun createQuote(
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			quoteService.createQuote(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

}