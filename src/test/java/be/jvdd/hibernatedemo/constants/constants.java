package be.jvdd.hibernatedemo.constants;

import be.jvdd.hibernatedemo.domain.Account;

public class constants {

    public static final String ACCOUNT_NAME_X = "accountX";
    public static final String SUPPLIER_ACCOUNT_X = "supplierAccountX";
    public static final String GLOBAL_CUSTOMER = "globalCustomer";
    public static final String CUSTOMER_X = "customerX";
    public static final String ACCOUNT_Y = "accountY";
    public static final String SUPPLIER_ACCOUNT_Y = "supplierAccountY";
    public static final String CUSTOMER_Y = "customerY";

    public static final Account ACCOUNT_X_X_G = Account.builder()
            .accountName(ACCOUNT_NAME_X)
            .supplierName(SUPPLIER_ACCOUNT_X)
            .customerName(GLOBAL_CUSTOMER)
            .amount(10.0)
            .build();

    public static final Account ACCOUNT_X_X_X = Account.builder()
            .accountName(ACCOUNT_NAME_X)
            .supplierName(SUPPLIER_ACCOUNT_X)
            .customerName(CUSTOMER_X)
            .amount(20.0)
            .build();

    public static final Account ACCOUNT_Y_Y_Y = Account.builder()
            .accountName(ACCOUNT_Y)
            .supplierName(SUPPLIER_ACCOUNT_Y)
            .customerName(CUSTOMER_Y)
            .amount(30.0)
            .build();
}
