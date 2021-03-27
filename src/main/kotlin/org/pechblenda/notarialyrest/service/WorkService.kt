package org.pechblenda.notarialyrest.service

import org.pechblenda.exception.BadRequestException
import org.pechblenda.notarialyrest.entity.Work
import org.pechblenda.notarialyrest.repository.IQuoteRepository
import org.pechblenda.notarialyrest.repository.IWorkRepository
import org.pechblenda.notarialyrest.service.`interface`.IWorkService
import org.pechblenda.service.Request
import org.pechblenda.service.Response
import org.pechblenda.service.helper.Validation
import org.pechblenda.service.helper.ValidationType
import org.pechblenda.service.helper.Validations

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.UUID

@Service
class WorkService(
	private val workRepository: IWorkRepository,
	private val quoteRepository: IQuoteRepository,
	private val response: Response
): IWorkService {

	@Transactional
	override fun saveGroupWorks(request: Request): ResponseEntity<Any> {
		request.validate(
			Validations(
				Validation(
					"uuidQuote",
					"Upps el uuid del presupuesto es requerido",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK
				),
				Validation(
					"workforce",
					"Upps la mano de obra es requerida",
					ValidationType.NOT_NULL,
					ValidationType.NOT_BLANK,
					ValidationType.NUMBER
				),
				Validation(
					"works",
					"Upps los artículos son requeridos",
					ValidationType.NOT_NULL,
					ValidationType.ARRAY
				)
			)
		)
		val includes = ArrayList<UUID>()
		val quote = quoteRepository.findById(
			UUID.fromString(request["uuidQuote"].toString())
		).orElseThrow {
			BadRequestException("Upps no se encuentra el presupuesto")
		}
		val works = request.toList<Work>(
			"works",
			Work::class,
			getValidations()
		)

		quote.workforce = request["workforce"].toString().toDouble()

		works.forEach { work ->
			println(work.measure)
			work.quote = quote
			workRepository.save(work)
			includes.add(work.uuid)
		}

		quote.works.forEach { work ->
			if (!includes.contains(work.uuid)) {
				workRepository.delete(work)
			}
		}

		return response.ok()
	}

	private fun getValidations(): Validations {
		return Validations(
			Validation(
				"concept",
				"Upps el concepto es requerido",
				ValidationType.NOT_NULL,
				ValidationType.NOT_BLANK
			),
			Validation(
				"measure",
				"Upps la medida no es correcta",
				ValidationType.NOT_NULL,
				ValidationType.NOT_BLANK,
				ValidationType.includes(
					"kg", "g", "lt", "ml", "pz", "mt", "mm"
				)
			)
		)
	}

}