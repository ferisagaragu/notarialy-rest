package org.pechblenda.notarialyrest.service

import org.pechblenda.exception.BadRequestException
import org.pechblenda.notarialyrest.entity.Company
import org.pechblenda.notarialyrest.entity.User
import org.pechblenda.notarialyrest.repository.ICompanyRepository
import org.pechblenda.notarialyrest.security.IAuthRepository
import org.pechblenda.notarialyrest.service.`interface`.ICompanyService
import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.service.helper.Validation
import org.pechblenda.service.helper.ValidationType
import org.pechblenda.service.helper.Validations
import org.pechblenda.style.CategoryColor
import org.pechblenda.style.Color

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyService(
	private val companyRepository: ICompanyRepository,
	private val authRepository: IAuthRepository,
	private val response: Response,
	private val color: Color
): ICompanyService {

	override fun findAllCompaniesByUserUuid(): ResponseEntity<Any> {
		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get()

		val companies = companyRepository.findAllByUserUuid(user.uuid)

		return response.toListMap(companies).ok()
	}

	@Transactional
	override fun createCompany(request: Request): ResponseEntity<Any> {
		val company = request.to<Company>(
			Company::class,
			Validations(
				Validation(
					"name",
					"Upps el nombre de la compañía es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"slogan",
					"Upps el eslogan de la compañía es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				)
			)
		)

		if (companyRepository.existsByName(company.name)) {
			throw BadRequestException("Upps ya existe una compañía con con el mismo nombre")
		}

		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get() as User

		company.user = user
		company.color = color.getMaterialColor(CategoryColor.MATERIAL_500).background

		return response.toMap(
			companyRepository.save(company)
		).exclude(
			"user"
		).created()
	}

}