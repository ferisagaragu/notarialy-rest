package org.pechblenda.notarialyrest.config

import org.pechblenda.auth.AuthController
import org.pechblenda.doc.Documentation
import org.pechblenda.doc.entity.ApiInfo
import org.pechblenda.doc.entity.Credential
import org.pechblenda.notarialyrest.controller.ClientController
import org.pechblenda.notarialyrest.controller.CompanyController
import org.pechblenda.notarialyrest.controller.QuoteController

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("org.pechblenda.bean")
class BeanConfig {

	@Bean
	fun documentation(): Documentation {
		val bodyRequest = LinkedHashMap<String, String>()
		bodyRequest["userName"] = "ferisagaragu@gmail.com"
		bodyRequest["password"] = "fernny27"

		return Documentation(
			ApiInfo(
				title = "Notarialy",
				description = "Notarialy rest api",
				iconUrl = "",
				version = "0.0.1",
				credentials = listOf(
					Credential(
						name = "User Root",
						endPoint = "http://localhost:5000/rest/auth/sign-in",
						bodyRequest = bodyRequest,
						tokenMapping = "data.session.token"
					)
				)
			),
			AuthController::class,
			CompanyController::class,
			ClientController::class,
			QuoteController::class
		)
	}

}