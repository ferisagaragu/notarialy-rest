package org.pechblenda.notarialyrest.service.`interface`

import java.util.UUID

import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity

interface IQuoteService {
	fun findQuotesByUserUuid(): ResponseEntity<Any>
	fun findQuoteByUuid(uuid: UUID): ResponseEntity<Any>
	fun generatePDFQuote(uuid: UUID): ResponseEntity<Any>
	fun createQuote(request: Request): ResponseEntity<Any>
}