package org.pechblenda.notarialyrest.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

import java.util.UUID
import org.pechblenda.auth.entity.IUser

@Entity
@Table(name = "users")
class User(
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	override var uuid: UUID,
	override var name: String,
	override var surname: String,
	override var motherSurname: String,

	@Column(unique = true)
	override var userName: String,

	@Column(unique = true)
	override var email: String,

	override var password: String,
	var phoneNumber: String,
	override var photo: String,

	@Column(columnDefinition = "boolean default false")
	override var enabled: Boolean,

	@Column(columnDefinition = "boolean default false")
	override var active: Boolean,

	override var activatePassword: UUID?,

	@ManyToMany
	var roles: MutableList<Role>,

	@OneToMany(mappedBy = "user")
	var companies: MutableList<Company>,

	@OneToMany(mappedBy = "user")
	var client: MutableList<Client>
): IUser {

	constructor(): this(
		uuid = UUID.randomUUID(),
		name = "",
		surname = "",
		motherSurname = "",
		userName = "",
		email = "",
		password = "",
		phoneNumber = "",
		photo = "",
		enabled = false,
		active = false,
		activatePassword = null,
		roles = mutableListOf<Role>(),
		companies = mutableListOf<Company>(),
		client = mutableListOf<Client>()
	)

}
