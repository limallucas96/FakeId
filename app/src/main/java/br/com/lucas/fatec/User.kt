package br.com.lucas.fatec

import java.io.Serializable

data class User (
    var courseName : String? = null,
    var coursePeriod : String? = null,
    var ra : String? = null,
    var rg : String? = null,
    var cpf : String? = null,
    var birthday : String? = null,
    var create_date : String? = null,
    var expire_date : String? = null,
    var college_name : String? = null
) : Serializable