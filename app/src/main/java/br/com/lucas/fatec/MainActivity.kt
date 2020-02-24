package br.com.lucas.fatec

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.lucas.fatec.FileUtils.RESULT_GALLERY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DialogUtilsListener {

    private var menuSaveItem: MenuItem? = null

    private var easterEggCount = 0
    private var isEditableMode = false // TODO -> INIT THIS AS FALSE
    private var session: Session? = null

    private var profilePhotoPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DialogUtils.setListener(this)
        session = Session(this)

        setupListeners()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3A1212")))
        supportActionBar?.title = "Centro Paula Souza"
    }

    private fun setupListeners() {

        profile_picture.setOnClickListener {
            easterEggCount += 1
            if (easterEggCount >= 5) {
                DialogUtils.showDefaultDialog(this, DialogType.DEFAULT)
            }

            if (isEditableMode) {
                FileUtils.openGallery(this@MainActivity)
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

    private fun enterInEditableMode() {
        isEditableMode = true
        menuSaveItem?.isVisible = isEditableMode
    }

    private fun exitEditableMode() {
        easterEggCount = 0
        isEditableMode = false
        menuSaveItem?.isVisible = isEditableMode
        saveChanges()
    }

    private fun fetchSession() {
        session?.getSession()?.let {
            course.text = it.courseName
            time.text = it.coursePeriod
            ra.text = it.userRA
            rg.text = it.userRG
            cpf.text = it.userCPF
            birthday.text = it.userBirthday
            expediton_time.text = it.createDate
            expire_date.text = it.expireDate
            colle_name.text = it.collegeName
            profilePhotoPath = it.userProfilePhotoPath ?: ""
        }
    }

    private fun saveChanges() {
        var user = session?.getSession()
        if (user == null) {
            user = User()
        }
        user?.apply {
            courseName = course.text.toString()
            coursePeriod = time.text.toString()
            userRA = ra.text.toString()
            userRG = rg.text.toString()
            userCPF = cpf.text.toString()
            userBirthday = birthday.text.toString()
            createDate = expediton_time.text.toString()
            expireDate = expire_date.text.toString()
            collegeName = colle_name.text.toString()
        }
        session?.setSession(user)
    }

    private fun showEditFieldDialog(type: EditModeType) {
        if (isEditableMode) {
            DialogUtils.showEditFieldDialog(this, type)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RESULT_GALLERY -> {
                profilePhotoPath = FileUtils.getPath(data?.data, this@MainActivity)
                FileUtils.toBitmap(profilePhotoPath)?.let { profilePhoto ->
                    profile_picture.setImageBitmap(profilePhoto)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchSession()
    }

    override fun onPositiveButton() {
        easterEggCount = 0
        enterInEditableMode()
    }

    override fun onNegativeButton() {
        exitEditableMode()
    }

    override fun onFieldUpdated(type: EditModeType, value: String) {
        updateView(type, value)
    }

    override fun onSave() {
        exitEditableMode()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menuSaveItem = menu.findItem(R.id.save)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> DialogUtils.showDefaultDialog(this, DialogType.SAVE)
        }
        return super.onOptionsItemSelected(item)
    }
}
