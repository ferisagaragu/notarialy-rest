package org.pechblenda.notarialyrest.repository

import java.util.*
import org.pechblenda.notarialyrest.entity.Work
import org.springframework.data.jpa.repository.JpaRepository

interface IWorkRepository: JpaRepository<Work, UUID>