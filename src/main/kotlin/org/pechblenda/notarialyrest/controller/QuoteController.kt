package org.pechblenda.notarialyrest.controller

import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.IQuoteService
import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.bind.annotation.PutMapping

import java.util.UUID

import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.DeleteMapping

@CrossOrigin(methods = [
	RequestMethod.GET,
	RequestMethod.POST,
	RequestMethod.PUT,
	RequestMethod.DELETE
])
@RestController
@RequestMapping(name = "Quote", value = ["/rest/quotes"])
class QuoteController(
	private val quoteService: IQuoteService,
	private val httpExceptionResponse: HttpExceptionResponse
) {

	@GetMapping
	fun findQuotesByUserUuid(): ResponseEntity<Any> {
		return try {
			quoteService.findQuotesByUserUuid()
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@GetMapping("/{uuid}")
	fun findQuoteByUuid(
		@PathVariable("uuid") uuid: UUID
	): ResponseEntity<Any> {
		return try {
			quoteService.findQuoteByUuid(uuid)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@GetMapping("/generate-pdf/{uuid}")
	fun generatePDFQuote(
		@PathVariable("uuid") uuid: UUID,
		servletRequest: HttpServletRequest
	): ResponseEntity<Any> {
		return try {
			quoteService.generatePDFQuote(uuid)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

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

	@PutMapping
	fun updateQuote(
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			quoteService.updateQuote(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@DeleteMapping("/{uuid}")
	fun deleteQuote(
		@PathVariable("uuid") uuid: UUID
	): ResponseEntity<Any> {
		return try {
			quoteService.deleteQuote(uuid)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

}