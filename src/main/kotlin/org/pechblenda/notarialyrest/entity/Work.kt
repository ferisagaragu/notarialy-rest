package org.pechblenda.notarialyrest.entity

import java.text.NumberFormat
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Table
import org.pechblenda.service.annotation.Key
import org.pechblenda.service.enum.DefaultValue

@Entity
@Table(name = "works")
class Work(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var uuid: UUID,
	var quantity: Double,
	var concept: String,
	var unitPrice: Double,
	var totalPrice: Double,

	@ManyToOne
	var quote: Quote?
) {

	constructor():this(
		uuid = UUID.randomUUID(),
		quantity = 0.0,
		concept = "",
		unitPrice = 0.0,
		totalPrice = 0.0,
		quote = null
	)

	@Key(name = "quantity", autoCall = false, defaultNullValue = DefaultValue.TEXT)
	fun quantity(): String {
		val numberFormat = NumberFormat.getCurrencyInstance()
		return numberFormat.format(quantity).replace("$", "") + " MNX"
	}

	@Key(name = "unitPrice", autoCall = false, defaultNullValue = DefaultValue.TEXT)
	fun unitPrice(): String {
		val numberFormat = NumberFormat.getCurrencyInstance()
		return numberFormat.format(unitPrice).replace("$", "") + " MNX"
	}

	@Key(name = "totalPrice", autoCall = false, defaultNullValue = DefaultValue.TEXT)
	fun totalPrice(): String {
		val numberFormat = NumberFormat.getCurrencyInstance()
		return numberFormat.format(totalPrice).replace("$", "") + " MNX"
	}

}