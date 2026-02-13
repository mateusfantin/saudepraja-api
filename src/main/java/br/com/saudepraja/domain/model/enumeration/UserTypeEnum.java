package br.com.saudepraja.domain.model.enumeration;

public enum UserTypeEnum {

    ADMIN,
    CUSTOMER,
    MEDIC,
    PROVIDER;

    public boolean isCustomer() {
        return CUSTOMER.equals(this);
    }

    public boolean isProvider() {
        return PROVIDER.equals(this);
    }

    public boolean isMedic() { return MEDIC.equals(this); }
}
