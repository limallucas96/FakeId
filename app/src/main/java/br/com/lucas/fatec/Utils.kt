package br.com.lucas.fatec

enum class EditModeType(var title: String) {

    COURSE_NAME("Nome do curso"),
    COURSE_PERIOD("Perido"),
    RA("Documento escolar"),
    RG("RG"),
    CPF("CPF"),
    BIRTHDAY("Data de nascimento"),
    CREATE_DATE("Data de expedição"),
    EXPIRE_DATE("Validade"),
    COLLEGE_NAME("Nome da instituição");

    companion object {
        fun fromType(type: EditModeType) = values().find { it == type }
    }

}

object Constants {
    const val KEY_USER = "KEY_USER"
}

