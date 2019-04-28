package net.icemix.mobilemoney;



public class PaymentRequest {

    private String phone = "";
    private String provider = "MOMO_UG";
    private Integer amount = 0;
    public PaymentRequest(){}

    public PaymentRequest(String phone, Integer amount) {
        this.phone = phone;
        this.amount = amount;
    }

    public PaymentRequest(String phone, Integer amount, String provider) {
        this.phone = phone;
        this.provider = provider;
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
