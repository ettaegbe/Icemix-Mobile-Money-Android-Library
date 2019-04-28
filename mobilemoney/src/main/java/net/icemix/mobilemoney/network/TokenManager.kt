package net.icemix.mobilemoney.network

import android.content.SharedPreferences

import net.icemix.mobilemoney.network.models.AccessToken


class TokenManager private constructor(private val prefs: SharedPreferences) {
    private val editor: SharedPreferences.Editor

    val token: AccessToken
        get() {
            val token = AccessToken()
            token.access_token = prefs.getString("ACCESS_TOKEN", "")
            //token.refreshToken = prefs.getString("REFRESH_TOKEN", "")
            return token
        }

    init {
        this.editor = prefs.edit()
    }

    fun saveToken(token: AccessToken?) {
        editor.putString("ACCESS_TOKEN", token?.access_token).commit()
       // editor.putString("REFRESH_TOKEN", token?.refreshToken).commit()
    }

    fun deleteToken() {
        editor.remove("ACCESS_TOKEN").commit()
        editor.remove("REFRESH_TOKEN").commit()
    }

    companion object {

        private var INSTANCE: TokenManager? = null

        @Synchronized
        internal fun getInstance(prefs: SharedPreferences): TokenManager {
            if (INSTANCE == null) {
                INSTANCE = TokenManager(prefs)
            }
            return INSTANCE as TokenManager
        }
    }


}
