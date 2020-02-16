package br.com.lucas.fatec

import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog

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

enum class DialogType {
    DEFAULT, SAVE
}

interface DialogUtilsListener {
    fun onPositiveButton()
    fun onNegativeButton()
    fun onFieldUpdated(type: EditModeType, value: String)
    fun onSave()
}

object DialogUtils {

    private var mListener: DialogUtilsListener? = null
    fun setListener(listener: DialogUtilsListener) {
        mListener = listener
    }

    private var title = ""
    private var message = ""
    private var positive = ""
    private var negative = ""

    fun showDefaultDialog(context: Context, type: DialogType) {
        when (type) {
            DialogType.DEFAULT -> {
                title = context.getString(R.string.title)
                message = context.getString(R.string.message)
                positive = context.getString(R.string.positive)
                negative = context.getString(R.string.negative)
            }
            DialogType.SAVE -> {
                title = context.getString(R.string.title_save)
                message = context.getString(R.string.message_save)
                positive = context.getString(R.string.positive_save)
                negative = context.getString(R.string.negative_save)
            }
        }

        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positive) { _, _ ->
                when (type) {
                    DialogType.DEFAULT -> mListener?.onPositiveButton()
                    DialogType.SAVE -> mListener?.onSave()
                }
            }
            setNegativeButton(negative) { _, _ ->
                mListener?.onNegativeButton()
            }.show()
        }
    }

    fun showEditFieldDialog(context: Context, type: EditModeType) {
        val input = EditText(context)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp

        AlertDialog.Builder(context).apply {
            setTitle(EditModeType.fromType(type)?.title)
            setMessage("Tem certeza que deseja editar seu documento?")
            setView(input)
            setPositiveButton("Sim") { _, _ ->
                input.text?.toString()?.let {
                    mListener?.onFieldUpdated(type, it)
                }
            }
            setNegativeButton("Cancelar") { _, _ -> }.show()
        }
    }
}

object Constants {

    const val KEY_USER = "KEY_USER"

}