package org.pechblenda.notarialyrest.controller

import org.pechblenda.doc.annotation.ApiDocumentation
import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.ICompanyService
import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

import java.util.UUID

@CrossOrigin(methods = [
	RequestMethod.GET,
	RequestMethod.POST,
	RequestMethod.PUT,
	RequestMethod.DELETE
])
@RestController
@RequestMapping(name = "Company", value = ["/rest/companies"])
class CompanyController(
	private val companyService: ICompanyService,
	private val httpExceptionResponse: HttpExceptionResponse
) {

	@GetMapping
	fun findAllCompaniesByUserUuid(): ResponseEntity<Any> {
		return try {
			companyService.findAllCompaniesByUserUuid()
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PostMapping
	@ApiDocumentation(path = "doc/company/create-company.json")
	fun createCompany(
		@RequestPart body: String,
		@RequestPart file: MultipartFile
	): ResponseEntity<Any> {
		val request = Request().toRequest(body)
		request.remove("logoUrl")

		return try {
			companyService.createCompany(request, file)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@PutMapping
	fun updateCompany(
		@RequestPart body: String,
		@RequestPart file: MultipartFile
	): ResponseEntity<Any> {
		val request = Request().toRequest(body)
		request.remove("logoUrl")

		return try {
			companyService.updateCompany(request, file)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@DeleteMapping("/{uuid}")
	fun deleteCompany(
		@PathVariable("uuid") uuid: UUID
	): ResponseEntity<Any> {
		return try {
			companyService.deleteCompany(uuid)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

	@MessageMapping("/socket")
	@SendTo("/messages")
	fun greeting(message: String): String {
		println(message)
		//simpMessagingTemplate.convertAndSend("/message", "Hola wey")
		return message
	}

	@MessageMapping("/socket2")
	@SendTo("/notify")
	fun greeting2(message: String): String {
		println(message)
		//simpMessagingTemplate.convertAndSend("/message", "Hola wey")
		return message
	}

}