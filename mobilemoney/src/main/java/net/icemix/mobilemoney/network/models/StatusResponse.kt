package net.icemix.mobilemoney.network.models

import com.squareup.moshi.Json

class StatusResponse {

    @Json(name = "status")
    var status: String = ""
    @Json(name = "amount")
    var amount: String = ""
    @Json(name = "currency")
    var currency: String = ""
    @Json(name = "externalId")
    var externalId: String = ""
    @Json(name = "financialTransactionId")
    var financialTransactionId: String = ""
    @Json(name = "payer")
    var payer: UGPayer = UGPayer()
    @Json(name = "reason")
    var reason: UGReason = UGReason()
    var referenceId: String = ""

}
