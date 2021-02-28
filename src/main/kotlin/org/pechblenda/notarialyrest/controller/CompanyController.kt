package org.pechblenda.notarialyrest.controller

import org.pechblenda.doc.annotation.ApiDocumentation
import org.pechblenda.exception.HttpExceptionResponse
import org.pechblenda.notarialyrest.service.`interface`.ICompanyService
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
		@RequestBody request: Request
	): ResponseEntity<Any> {
		return try {
			companyService.createCompany(request)
		} catch (e: ResponseStatusException) {
			httpExceptionResponse.error(e)
		}
	}

}