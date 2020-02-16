package br.com.lucas.fatec

import android.content.Context
import com.google.gson.Gson
import android.content.Context.MODE_PRIVATE
import br.com.lucas.fatec.Constants.KEY_USER

class Session(private val context: Context) {

    private var sharedPreferences = context.getSharedPreferences("FAKE_ID", MODE_PRIVATE)
    private var gson = Gson()

    fun getSession(): User? {
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return gson.fromJson(userJson, User::class.java)
    }

    fun setSession(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER, gson.toJson(user))
        editor.apply()
    }

}