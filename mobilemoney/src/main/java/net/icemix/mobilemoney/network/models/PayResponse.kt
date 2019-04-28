package net.icemix.mobilemoney.network.models

import com.squareup.moshi.Json

class PayResponse {

    @Json(name = "statusCode")
    var statusCode: String = ""
    @Json(name = "statusDescription")
    var statusDescription: String = ""

}
