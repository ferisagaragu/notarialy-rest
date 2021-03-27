package org.pechblenda.notarialyrest.service.`interface`

import org.pechblenda.service.Request
import org.springframework.http.ResponseEntity

interface IWorkService {
	fun saveGroupWorks(request: Request): ResponseEntity<Any>
}