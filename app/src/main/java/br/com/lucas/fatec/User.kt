package br.com.lucas.fatec

import java.io.Serializable

data class User (
    var courseName : String? = null,
    var coursePeriod : String? = null,
    var userRA : String? = null,
    var userRG : String? = null,
    var userCPF : String? = null,
    var userBirthday : String? = null,
    var createDate : String? = null,
    var expireDate : String? = null,
    var collegeName : String? = null,
    var userProfilePhotoPath : String? = null,
    var companyPhotoPath : String? = null
) : Serializable