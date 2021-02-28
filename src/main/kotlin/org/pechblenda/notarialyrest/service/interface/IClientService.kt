package org.pechblenda.notarialyrest.service.`interface`

import org.pechblenda.service.Request
import org.springframework.http.ResponseEntity

interface IClientService {
	fun findAllClientsByUserUuid(): ResponseEntity<Any>
	fun createClient(request: Request): ResponseEntity<Any>
}