package tyt.sales.controller.request;

import lombok.Data;

import tyt.sales.model.PaymentMethod;

@Data
public class CheckoutRequest {

    private PaymentMethod paymentMethod;

}
