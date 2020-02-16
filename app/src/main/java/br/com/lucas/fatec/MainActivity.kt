package br.com.lucas.fatec

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.LinearLayout
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private var easterEggCount = 0
    private var isEditableMode = true // TODO -> INIT THIS AS FALSE
    private var session: Session? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session = Session(this)

        setupListeners()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3A1212")))
        supportActionBar?.title = "Centro Paula Souza"
    }

    private fun setupListeners() {

        profile_picture.setOnClickListener {
            easterEggCount += 1
            if (easterEggCount >= 5) {
                showEasterEggDialog()
            }
        }

        course.setOnClickListener { showEditFieldDialog(EditModeType.COURSE_NAME) }
        time.setOnClickListener { showEditFieldDialog(EditModeType.COURSE_PERIOD) }
        ra.setOnClickListener { showEditFieldDialog(EditModeType.RA) }
        rg.setOnClickListener { showEditFieldDialog(EditModeType.RG) }
        cpf.setOnClickListener { showEditFieldDialog(EditModeType.CPF) }
        birthday.setOnClickListener { showEditFieldDialog(EditModeType.BIRTHDAY) }
        expediton_time.setOnClickListener { showEditFieldDialog(EditModeType.CREATE_DATE) }
        expire_date.setOnClickListener { showEditFieldDialog(EditModeType.EXPIRE_DATE) }
        colle_name.setOnClickListener { showEditFieldDialog(EditModeType.COLLEGE_NAME) }
    }

    private fun updateSession(type: EditModeType, value: String) {
        session?.getSession()?.apply {
            when (type) {
                EditModeType.COURSE_NAME -> courseName = value
                EditModeType.COURSE_PERIOD -> coursePeriod = value
                EditModeType.RA -> ra = value
                EditModeType.RG -> rg = value
                EditModeType.CPF -> cpf = value
                EditModeType.BIRTHDAY -> birthday = value
                EditModeType.CREATE_DATE -> create_date = value
                EditModeType.EXPIRE_DATE -> expire_date = value
                EditModeType.COLLEGE_NAME -> college_name = value
            }
        }?.let { user ->
            session?.setSession(user)
        }
    }

    private fun updateView(type: EditModeType, value: String) {
        when (type) {
            EditModeType.COURSE_NAME -> course.text = value
            EditModeType.COURSE_PERIOD -> time.text = value
            EditModeType.RA -> ra.text = value
            EditModeType.RG -> rg.text = value
            EditModeType.CPF -> cpf.text = value
            EditModeType.BIRTHDAY -> birthday.text = value
            EditModeType.CREATE_DATE -> expediton_time.text = value
            EditModeType.EXPIRE_DATE -> expire_date.text = value
            EditModeType.COLLEGE_NAME -> colle_name.text = value
        }
    }

    private fun showEasterEggDialog() {
        AlertDialog.Builder(this@MainActivity).apply {
            setTitle("Editar documento")
            setMessage("Tem certeza que deseja editar seu documento?")
            setPositiveButton("Sim") { _, _ ->
                isEditableMode = true
            }
            setNegativeButton("Cancelar") { _, _ ->
                easterEggCount = 0
            }.show()
        }
    }

    private fun showEditFieldDialog(type: EditModeType) {
        if (isEditableMode) {
            val input = EditText(this@MainActivity)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            input.layoutParams = lp

            AlertDialog.Builder(this@MainActivity).apply {
                setTitle(EditModeType.fromType(type)?.title)
                setMessage("Tem certeza que deseja editar seu documento?")
                setView(input)
                setPositiveButton("Sim") { _, _ ->
                    input.text?.toString()?.let {
                        updateSession(type, it)
                        updateView(type, it)
                    }
                }
                setNegativeButton("Cancelar") { _, _ ->
                    easterEggCount = 0
                }.show()
            }
        }
    }
}
