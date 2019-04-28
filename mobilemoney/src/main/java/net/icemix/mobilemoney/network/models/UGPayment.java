package net.icemix.mobilemoney.network.models;

public class UGPayment {
    private int amount = 0;
    private String currency = "";
    private String externalId = "";
    private String payerMessage = "";
    private String payeeNote = "";
    private UGPayer payer = new UGPayer();

    public UGPayment() {
    }

    public UGPayment(int amount, String currency, String externalId, String payerMessage, String payeeNote) {
        this.amount = amount;
        this.currency = currency;
        this.externalId = externalId;
        this.payerMessage = payerMessage;
        this.payeeNote = payeeNote;
    }

    public UGPayment(int amount, String currency, String externalId, String payerMessage, String payeeNote, UGPayer payer) {
        this.amount = amount;
        this.currency = currency;
        this.externalId = externalId;
        this.payerMessage = payerMessage;
        this.payeeNote = payeeNote;
        this.payer = payer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }

    public String getPayeeNote() {
        return payeeNote;
    }

    public void setPayeeNote(String payeeNote) {
        this.payeeNote = payeeNote;
    }

    public UGPayer getPayer() {
        return payer;
    }

    public void setPayer(UGPayer payer) {
        this.payer = payer;
    }
}
