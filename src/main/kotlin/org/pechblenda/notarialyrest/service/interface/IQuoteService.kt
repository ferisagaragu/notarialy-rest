package org.pechblenda.notarialyrest.service.`interface`

import org.pechblenda.service.Request
import org.springframework.http.ResponseEntity

interface IQuoteService {
	fun createQuote(request: Request): ResponseEntity<Any>
}