/*
 * Copyright (c) 2022 Worldline Global Collect B.V
 */

package com.worldline.connect.sdk.client.android.model.paymentrequest;

import com.worldline.connect.sdk.client.android.model.accountonfile.AccountOnFile;
import com.worldline.connect.sdk.client.android.model.paymentproduct.PaymentProduct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import com.worldline.connect.sdk.client.android.helper.GsonHelper;
import static org.junit.Assert.*;

/**
 * Junit Testclass which tests PaymentRequest
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentRequestTest {

    private final PaymentProduct paymentProductVisa =  GsonHelper.fromResourceJson("paymentProductVisa.json", PaymentProduct.class);
    private final PaymentProduct paymentProductInvoice = GsonHelper.fromResourceJson("paymentProductInVoice.json", PaymentProduct.class);
    private final PaymentProduct paymentProductPayPal = GsonHelper.fromResourceJson("paymentProductPayPal.json", PaymentProduct.class);
    private final PaymentProduct paymentProductBoletoBancario = GsonHelper.fromResourceJson("paymentProductBoletoBancario.json", PaymentProduct.class);

    private final AccountOnFile accountOnFileVisa = GsonHelper.fromResourceJson("accountOnFileVisa.json", AccountOnFile.class);

    public static Map<String, String> allValidValuesVisa = new HashMap<>();
    static {
        allValidValuesVisa.put("cardNumber", "4012000033330026");
        allValidValuesVisa.put("expiryDate", "1240");
        allValidValuesVisa.put("cvv", "123");
    }

    public static Map<String, String> allValidValuesInVoice = new HashMap<>();
    static {
        allValidValuesInVoice.put("stateCode", "abcdefgh");
        allValidValuesInVoice.put("city", "Amsterdam");
        allValidValuesInVoice.put("street", "De Dam");
    }

    public static Map<String, String> invalidCCNVisa = new HashMap<>();
    static {
        invalidCCNVisa.put("cardNumber", "401200");
        invalidCCNVisa.put("expiryDate", "1240");
        invalidCCNVisa.put("cvv", "123");
    }

    public static Map<String, String> invalidStateInVoice = new HashMap<>();
    static {
        invalidStateInVoice.put("stateCode", "abcdefghijklmnopqrstuvwxyz");
        invalidStateInVoice.put("city", "Amsterdam");
        invalidStateInVoice.put("street", "De dam");
    }

    public static Map<String, String> missingCCNVisa = new HashMap<>();
    static {
        missingCCNVisa.put("expiryDate", "1240");
        missingCCNVisa.put("cvv", "123");
    }

    public static Map<String, String> missingCityInVoice = new HashMap<>();
    static {
        missingCityInVoice.put("stateCode", "abcdefgh");
        missingCityInVoice.put("street", "De Dam");
    }

    public static Map<String, String> allValidValuesBoleto = new HashMap<>();
    static {
        allValidValuesBoleto.put("fiscalNumber", "11111111111111");
        allValidValuesBoleto.put("companyName", "Worldline Global Collect");
        allValidValuesBoleto.put("firstName", "John");
        allValidValuesBoleto.put("surname", "");
        allValidValuesBoleto.put("street", "De dam");
        allValidValuesBoleto.put("houseNumber", "11");
        allValidValuesBoleto.put("zip", "12345678");
        allValidValuesBoleto.put("city", "Amsterdam");
        allValidValuesBoleto.put("stateCode", "NH");
    }

    public static Map<String, String> inValidEmptyCompanyBoleto = new HashMap<>();
    static {
        inValidEmptyCompanyBoleto.put("fiscalNumber", "11111111111111");
        inValidEmptyCompanyBoleto.put("companyName", "");
        inValidEmptyCompanyBoleto.put("firstName", "John");
        inValidEmptyCompanyBoleto.put("surname", "Doe");
        inValidEmptyCompanyBoleto.put("street", "De dam");
        inValidEmptyCompanyBoleto.put("houseNumber", "11");
        inValidEmptyCompanyBoleto.put("zip", "12345678");
        inValidEmptyCompanyBoleto.put("city", "Amsterdam");
        inValidEmptyCompanyBoleto.put("stateCode", "NH");
    }

    public static Map<String, String> missingNameStillValidValuesBoleto = new HashMap<>();
    static {
        missingNameStillValidValuesBoleto.put("fiscalNumber", "11111111111111");
        missingNameStillValidValuesBoleto.put("companyName", "Worldline Global Collect");
        missingNameStillValidValuesBoleto.put("street", "De dam");
        missingNameStillValidValuesBoleto.put("houseNumber", "11");
        missingNameStillValidValuesBoleto.put("zip", "12345678");
        missingNameStillValidValuesBoleto.put("city", "Amsterdam");
        missingNameStillValidValuesBoleto.put("stateCode", "NH");
    }

    @Test
    public void testValidateSucceedsForValidValuesVisa() {
        PaymentRequest validVisaValuesRequest = new PaymentRequest();
        setValuesInRequest(allValidValuesVisa, validVisaValuesRequest);
        validVisaValuesRequest.setPaymentProduct(paymentProductVisa);
        assertTrue(validVisaValuesRequest.validate().isEmpty());
    }

    @Test
    public void testValidateSucceedsForValidValuesInVoice() {
        PaymentRequest validInVoiceValuesRequest = new PaymentRequest();
        setValuesInRequest(allValidValuesInVoice, validInVoiceValuesRequest);
        validInVoiceValuesRequest.setPaymentProduct(paymentProductInvoice);
        assertTrue(validInVoiceValuesRequest.validate().isEmpty());
    }

    @Test
    public void testValidateSucceedsForNoValuesPayPal() {
        PaymentRequest validPayPalValuesRequest = new PaymentRequest();
        validPayPalValuesRequest.setPaymentProduct(paymentProductPayPal);
        assertTrue(validPayPalValuesRequest.validate().isEmpty());
    }

    @Test
    public void testValidateFailsForInvalidCCNVisa() {
        PaymentRequest invalidVisaCCNRequest = new PaymentRequest();
        setValuesInRequest(invalidCCNVisa, invalidVisaCCNRequest);
        invalidVisaCCNRequest.setPaymentProduct(paymentProductVisa);
        assertFalse(invalidVisaCCNRequest.validate().isEmpty());
    }

    @Test
    public void testValidateFailsForInValidStateInVoice() {
        PaymentRequest invalidStateInVoiceRequest = new PaymentRequest();
        setValuesInRequest(invalidStateInVoice, invalidStateInVoiceRequest);
        invalidStateInVoiceRequest.setPaymentProduct(paymentProductInvoice);
        assertFalse(invalidStateInVoiceRequest.validate().isEmpty());
    }

    @Test
    public void testValidateFailsForMissingRequiredValuesVisa() {
        PaymentRequest missingCCNVisaRequest = new PaymentRequest();
        setValuesInRequest(missingCCNVisa, missingCCNVisaRequest);
        missingCCNVisaRequest.setPaymentProduct(paymentProductVisa);
        assertFalse(missingCCNVisaRequest.validate().isEmpty());
    }

    @Test
    public void testValidateFailsForMissingRequiredValuesInVoice() {
        PaymentRequest missingCityInVoiceRequest = new PaymentRequest();
        setValuesInRequest(missingCityInVoice, missingCityInVoiceRequest);
        missingCityInVoiceRequest.setPaymentProduct(paymentProductInvoice);
        assertFalse(missingCityInVoiceRequest.validate().isEmpty());
    }

    @Test
    public void TestValidAccountOnFile(){
        PaymentRequest accountOnFileVisaRequest = new PaymentRequest();
        accountOnFileVisaRequest.setPaymentProduct(paymentProductVisa);
        accountOnFileVisaRequest.setAccountOnFile(accountOnFileVisa);
        assertTrue(accountOnFileVisaRequest.validate().isEmpty());
    }

    @Test
    public void TestInvalidCvvNumberAccountOnFile(){
        PaymentRequest accountOnFileVisaRequest = new PaymentRequest();
        accountOnFileVisaRequest.setPaymentProduct(paymentProductVisa);
        accountOnFileVisaRequest.setAccountOnFile(accountOnFileVisa);
        accountOnFileVisaRequest.setValue("cvv", "  ");
        assertFalse(accountOnFileVisaRequest.validate().isEmpty());
    }

    @Test
    public void TestInvalidExpiryDateMustWriteAccountOnFile(){
        PaymentRequest accountOnFileVisaRequest = new PaymentRequest();
        accountOnFileVisaRequest.setPaymentProduct(paymentProductVisa);
        accountOnFileVisaRequest.setAccountOnFile(accountOnFileVisa);
        accountOnFileVisaRequest.setValue("expiryDate", "1221");
        assertFalse(accountOnFileVisaRequest.validate().isEmpty());
    }

    @Test
    public void TestValidExpiryDateMustWriteAccountOnFile(){
        PaymentRequest accountOnFileVisaRequest = new PaymentRequest();
        accountOnFileVisaRequest.setPaymentProduct(paymentProductVisa);
        accountOnFileVisaRequest.setAccountOnFile(accountOnFileVisa);
        assertTrue(accountOnFileVisaRequest.validate().isEmpty());
    }

    @Test
    public void testValidateSucceedsForBoleto() {
        PaymentRequest validBoletoRequest = new PaymentRequest();
        setValuesInRequest(allValidValuesBoleto, validBoletoRequest);
        validBoletoRequest.setPaymentProduct(paymentProductBoletoBancario);
        assertTrue(validBoletoRequest.validate().isEmpty());
    }

    @Test
    public void testValidateEmptyCompanyFailsForBoleto() {
        PaymentRequest inValidBoletoRequest = new PaymentRequest();
        setValuesInRequest(inValidEmptyCompanyBoleto, inValidBoletoRequest);
        inValidBoletoRequest.setPaymentProduct(paymentProductBoletoBancario);
        assertFalse(inValidBoletoRequest.validate().isEmpty());
    }

    @Test
    public void testValidateMissingFirstLastNameSucceedsForBoleto() {
        PaymentRequest validBoletoRequest = new PaymentRequest();
        setValuesInRequest(missingNameStillValidValuesBoleto, validBoletoRequest);
        validBoletoRequest.setPaymentProduct(paymentProductBoletoBancario);
        assertTrue(validBoletoRequest.validate().isEmpty());
    }

    private static void setValuesInRequest(Map<String, String> values, PaymentRequest request) {
        for (Map.Entry<String, String> entry: values.entrySet()) {
            request.setValue(entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testMaskedValue() {
        PaymentRequest validVisaValuesRequest = new PaymentRequest();
        setValuesInRequest(allValidValuesVisa, validVisaValuesRequest);
        validVisaValuesRequest.setPaymentProduct(paymentProductVisa);

        assertEquals(validVisaValuesRequest.getValue("cardNumber"), "4012000033330026");

        String maskedCardNumber = validVisaValuesRequest.getMaskedValue("cardNumber");

        assertEquals(maskedCardNumber, "4012 0000 3333 0026 ");
    }

    @Test
    public void testUnmaskedValue() {
        PaymentRequest validVisaValuesRequest = new PaymentRequest();
        validVisaValuesRequest.setPaymentProduct(paymentProductVisa);
        validVisaValuesRequest.setValue("cardNumber", "4012 0000 3333 0026");
        validVisaValuesRequest.setValue("expiryDate", "1240");
        validVisaValuesRequest.setValue("cvv", "123");

        assertEquals(validVisaValuesRequest.getValue("cardNumber"), "4012 0000 3333 0026");
        String unmaskedCardNumber = validVisaValuesRequest.getUnmaskedValue("cardNumber");

        assertEquals(unmaskedCardNumber, "4012000033330026");
    }
}
