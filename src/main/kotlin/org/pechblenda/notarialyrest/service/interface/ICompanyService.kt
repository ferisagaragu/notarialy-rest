package org.pechblenda.notarialyrest.service.`interface`

import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

import java.util.UUID

import org.pechblenda.service.Request

interface ICompanyService {
	fun findAllCompaniesByUserUuid(): ResponseEntity<Any>
	fun createCompany(request: Request, file: MultipartFile): ResponseEntity<Any>
	fun updateCompany(request: Request, file: MultipartFile): ResponseEntity<Any>
	fun deleteCompany(uuid: UUID): ResponseEntity<Any>
}