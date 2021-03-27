package org.pechblenda.notarialyrest.service.`interface`

import org.pechblenda.service.Request

import org.springframework.http.ResponseEntity

interface IUserService {
	fun updateUser(request: Request): ResponseEntity<Any>
}