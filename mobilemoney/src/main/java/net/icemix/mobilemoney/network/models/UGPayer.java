package net.icemix.mobilemoney.network.models;

public class UGPayer {
    private String partyIdType = "MSISDN";
    private String partyId = "";

    public UGPayer() {
    }

    public UGPayer(String partyIdType, String partyId) {
        this.partyIdType = partyIdType;
        this.partyId = partyId;
    }

    public String getPartyIdType() {
        return partyIdType;
    }

    public void setPartyIdType(String partyIdType) {
        this.partyIdType = partyIdType;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
