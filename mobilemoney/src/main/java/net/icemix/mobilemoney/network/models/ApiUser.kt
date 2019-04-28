package net.icemix.mobilemoney.network.models

import com.squareup.moshi.Json

class ApiUser {

    @Json(name = "code")
    var code: String = ""
    @Json(name = "message")
    var message: String = ""

}
