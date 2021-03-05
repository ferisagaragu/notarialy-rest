package org.pechblenda.notarialyrest.service

import org.pechblenda.exception.BadRequestException
import org.pechblenda.notarialyrest.entity.Quote
import org.pechblenda.notarialyrest.entity.User
import org.pechblenda.notarialyrest.repository.IClientRepository
import org.pechblenda.notarialyrest.repository.ICompanyRepository
import org.pechblenda.notarialyrest.repository.IQuoteRepository
import org.pechblenda.notarialyrest.security.IAuthRepository
import org.pechblenda.notarialyrest.service.`interface`.IQuoteService
import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.service.enum.IdType
import org.pechblenda.service.helper.EntityParse
import org.pechblenda.service.helper.Validation
import org.pechblenda.service.helper.ValidationType
import org.pechblenda.service.helper.Validations
import org.pechblenda.style.CategoryColor
import org.pechblenda.style.Color
import org.pechblenda.util.Report

import org.springframework.core.io.ClassPathResource
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import java.text.NumberFormat
import java.text.SimpleDateFormat

import java.util.UUID

@Service
class QuoteService(
	private val quoteRepository: IQuoteRepository,
	private val companyRepository: ICompanyRepository,
	private val clientRepository: IClientRepository,
	private val authRepository: IAuthRepository,
	private val report: Report,
	private val color: Color,
	private val response: Response
): IQuoteService {

	override fun findQuotesByUserUuid(): ResponseEntity<Any> {
		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get() as User

		return response.toListMap(
			quoteRepository.findAllByUserUuid(user.uuid)
		).ok()
	}

	override fun findQuoteByUuid(uuid: UUID): ResponseEntity<Any> {
		val quote = quoteRepository.findById(uuid).orElseThrow {
			BadRequestException("Upps no se encuentra el presupuesto")
		}

		return response.toMap(quote).ok()
	}

	override fun generatePDFQuote(uuid: UUID): ResponseEntity<Any> {
		val quote = quoteRepository.findById(uuid).orElseThrow {
			BadRequestException("Upps no se encuentra el presupuesto")
		}
		val parameters: MutableMap<String, Any> = HashMap()
		val simpleDateFormat = SimpleDateFormat("dd 'de' MMMM 'del' yyyy")
		val numberFormat = NumberFormat.getCurrencyInstance()
		var total = 0.0

		quote.works.forEach { work ->
			total += (work.quantity * work.totalPrice)
		}

		total += quote.workforce

		parameters["companyName"] = quote.company?.name as String
		parameters["companySlogan"] = quote.company?.slogan as String
		parameters["companyTitle"] = quote.company?.title as String
		parameters["companyLogoUrl"] = quote.company?.logoUrl as String

		parameters["userName"] = "${quote.user?.name} ${quote.user?.surname} ${quote.user?.motherSurname}"
		parameters["userPhoneNumber"] = quote.user?.phoneNumber as String
		parameters["userEmail"] = quote.user?.email as String

		parameters["clientName"] = "${quote.client?.name} ${quote.client?.surname} ${quote.client?.motherSurname}"
		parameters["clientAddress"] = quote.client?.address as String
		parameters["clientPhoneNumber"] = quote.client?.phoneNumber as String

		parameters["createDate"] = simpleDateFormat.format(quote.createDate)
		parameters["workforce"] = "${
			numberFormat.format(quote.workforce)
				.replace("$", "")
		} MNX"
		parameters["qrCode"] = "http://localhost:5000/api/auth/generate-profile-image/F/%23000000/%2300BCD4"
		parameters["total"] = "${
			numberFormat.format(total)
				.replace("$", "")
		} MNX"

		return response.file(
			"application/pdf",
			"${UUID.randomUUID()}.pdf",
			report.exportPdf(
				ClassPathResource("templates/report/quote.jrxml")
					.inputStream,
				parameters,
				response.toListMap(
					quote.works,
					"quantity",
					"unitPrice",
					"totalPrice"
				).json().toMutableList(),
			)
		)
	}

	override fun createQuote(request: Request): ResponseEntity<Any> {
		val quote = request.to<Quote>(
			Quote::class,
			Validations(
				Validation(
					"companyUuid",
					"Upps el uuid de la compañía es requerido",
					ValidationType.NOT_BLANK,
					ValidationType.NOT_NULL
				),
				Validation(
					"clientUuid",
					"Upps el uuid del cliente es requerido",
					ValidationType.NOT_BLANK,
					ValidationType.NOT_NULL
				)
			),
			EntityParse("companyUuid", "company", companyRepository, IdType.UUID),
			EntityParse("clientUuid", "client", clientRepository, IdType.UUID)
		)
		val user = authRepository.findByUserName(
			SecurityContextHolder.getContext().authentication.name
		).get() as User

		quote.user = user
		quote.color = color.getMaterialColor(CategoryColor.MATERIAL_500).background
		val quoteSave = quoteRepository.save(quote)

		return response.toMap(quoteSave).created()
	}

}