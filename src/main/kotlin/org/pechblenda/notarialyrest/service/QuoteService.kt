package org.pechblenda.notarialyrest.service

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
import org.pechblenda.util.Report

import org.springframework.core.io.ClassPathResource
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class QuoteService(
	private val quoteRepository: IQuoteRepository,
	private val companyRepository: ICompanyRepository,
	private val clientRepository: IClientRepository,
	private val authRepository: IAuthRepository,
	private val report: Report,
): IQuoteService {

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

		println(quote.client.name)
		println(quote.client.user)

		quoteRepository.save(quote)

		/*val user = quoteService.findById(
			UUID.fromString("2da28a72-e618-4d78-a887-26d70a31d4fc")
		).get()

		val parameters: MutableMap<String, Any> = HashMap()
		parameters["companyName"] = user.companies[0].name
		parameters["name"] = user.name
		parameters["phoneNumber"] = user.phoneNumber
		parameters["imageUrl"] = "https://freepngimg.com/thumb/mario/20723-2-mario-image.png"
		parameters["createDate"] = Date().toString()
		parameters["clientName"] = "Alberto Rolando Mota"
		parameters["direction"] = "Lopez Mateos Sur 1111"
		parameters["city"] = "Tlajomulco de Zuñiga"
		parameters["workforce"] = 2000.toString()
		parameters["total"] = 100.toString()*/

		return Response().file(
			"application/pdf",
			"out.pdf",
			report.exportPdf(
				ClassPathResource("templates/report/quote.jrxml").inputStream,
				null!!,
				arrayListOf()
			)
		)
	}

}