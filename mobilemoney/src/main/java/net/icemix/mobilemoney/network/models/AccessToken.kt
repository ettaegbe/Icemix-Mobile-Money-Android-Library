package net.icemix.mobilemoney.network.models

import com.squareup.moshi.Json

class AccessToken {

    @Json(name = "token_type")
    var token_type: String = ""
    @Json(name = "expires_in")
    var expires_in: Int = 0
    @Json(name = "access_token")
    var access_token: String = ""
    /*@Json(name = "refresh_token")
    var refreshToken: String = ""
    @Json(name = "error")
    var error: String = ""*/
}
