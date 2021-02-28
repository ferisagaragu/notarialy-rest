package org.pechblenda.notarialyrest.service.`interface`

import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity

interface ICompanyService {
	fun findAllCompaniesByUserUuid(): ResponseEntity<Any>
	fun createCompany(request: Request): ResponseEntity<Any>
}