package org.paygear.web;


import androidx.annotation.Nullable;

import org.paygear.model.AvailableClubs_Result;
import org.paygear.model.Card;
import org.paygear.model.CashoutUserConfirm;
import org.paygear.model.ConfirmVoucherQr_Result;
import org.paygear.model.CreditLimit;
import org.paygear.model.Iban;
import org.paygear.model.MerchantsResult;
import org.paygear.model.OTPVerifyResult;
import org.paygear.model.Order;
import org.paygear.model.PaymentAuth;
import org.paygear.model.PaymentResult;
import org.paygear.model.Province;
import org.paygear.model.QRResponse;
import org.paygear.model.Transport;

import java.util.ArrayList;
import java.util.List;

import ir.radsense.raadcore.model.Account;
import ir.radsense.raadcore.model.City;
import ir.radsense.raadcore.model.PaginateList;
import ir.radsense.raadcore.model.Product;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Software1 on 8/12/2017.
 */

public interface WebService {

    @GET("club/v3/custom/clubs")
    Call<List<AvailableClubs_Result>>getAvailableClubs(@Query("merchant_id") String merchantId, @Query("member_id")String userId);

    @GET("users/v3/merchants")
    Call<MerchantsResult>searchAccounts(@Query("per_page")  @Nullable Integer perPage, @Query("page") @Nullable Integer page, @Query("role") String role, @Query("role") String role2);

    @POST("users/v3/auth/otp")
    Call<Void> getOTP(@Body RequestBody phoneJsonBody);

    @POST("users/v3/auth/2step_verification/accounts/{account_id}/login")
    Call<Void> validatePassword(@Path("account_id") String accountId,
                                @Body RequestBody otpJsonBody);

    @POST("users/v3/auth/otp/verify")
    Call<OTPVerifyResult> verifyOTP(@Body RequestBody otpJsonBody);


    @POST("users/v3/auth/2step_verification/accounts/{account_id}")
    Call<Void> setAccountPassword(@Path("account_id") String accountId,
                                  @Body RequestBody otpJsonBody);


    @PUT("users/v3/accounts/{account_id}")
    Call<Void> updateAccount(@Path("account_id") String accountId,
                             @Body RequestBody accountJsonBody,
                             @Query("ref_code") String refCode);

    @GET("users/v3/accounts/{account_id}")
    Call<Account> getAccountInfo(@Path("account_id") String accountId,
                                 @Query("mod") int mode);


    @GET("credit/v3/users/{id}/credit")
    Call<ArrayList<Card>> getCredit(@Path("id") String accountId);

    @Multipart
    @POST("files/v3/upload/images")
    Call<String> uploadImage(@Part MultipartBody.Part part);

    @GET("geo/v3/cities")
    Call<ArrayList<City>> getCities();

    @GET("geo/v3/provinces")
    Call<ArrayList<Province>> getProvinces();

    @POST("users/v3/logout")
    Call<Void> logout(@Body RequestBody phoneJsonBody);


    @GET("payment/v3/cards")
    Call<ArrayList<Card>> getCards(@Query("order_id") String orderId,
                                   @Query("cashout") boolean cashOut,
                                   @Query("club") boolean hasClub);

    @PUT("payment/v3/cards/{card_token}")
    Call<Void> updateCard(@Path("card_token") String cardToken,
                          @Body RequestBody phoneJsonBody);

    @GET("payment/v3/accounts/{account_id}/cards")
    Call<ArrayList<Card>> getUserCards(@Path("account_id") String accountId);

    @GET("credit/v3/password/{card_token}/accounts/{account_id}")
    Call<Void> getForgotPassword(@Path("card_token") String card_token,
                                 @Path("account_id") String account_id);

    @PUT("credit/v3/password/{card_token}/accounts/{account_id}")
    Call<Void> PutForgotPassword(@Path("card_token") String card_token,
                                 @Path("account_id") String account_id);


    @POST("payment/v3/cards")
    Call<Card> addCard(@Body RequestBody cardJsonBody);

    @DELETE("payment/v3/cards/{token}")
    Call<Void> deleteCard(@Path("token") String token);


    @GET("payment/v3/key")
    Call<PaymentAuth> getPaymentKey();

    @POST("payment/v3/init")
    Call<PaymentAuth> initPayment(@Body RequestBody paymentJsonBody);

    @POST("payment/v3/pay")
    Call<PaymentResult> pay(@Body RequestBody cardInfoJsonBody);

//    @POST("payment/v3/pay")
//    Call<PaymentResult> pay(@Body RequestBody cardInfoJsonBody);

    @GET("payment/v3/accounts/{account_id}/orders")
    Call<PaginateList<Order>> getOrders(@Path("account_id") String accountId,
                                        @Query("mode") int mode,
                                        @Query("order_type") String orderType,
                                        @Query("pre_orders") boolean preOrders,
                                        @Query("last_id") String lastToken,
                                        @Query("per_page") int perPage);

    @GET("payment/v3/accounts/{account_id}/orders/{order_id}")
    Call<Order> getSingleOrder(@Path("account_id") String accountId,
                               @Path("order_id") String orderId);

    //@GET("users/v3/accounts/{account_id}/payment")
    //Call<UserPaymentInfo> getUserPaymentInfo(@Path("account_id") String accountId);

    @PUT("users/v3/accounts/{account_id}/payment")
    Call<Void> saveUserPaymentInfo(@Path("account_id") String accountId,
                                   @Body RequestBody cardInfoJsonBody);

    @GET("cashout/v3/{account_id}/{card_token}/limits")
    Call<CreditLimit> getCashOutLimits(@Path("account_id") String accountId,
                                       @Path("card_token") String cardToken);

    @POST("cashout/v3/{account_id}/{card_token}")
    Call<Void> requestCashOut(@Path("account_id") String accountId,
                              @Path("card_token") String cardToken,
                              @Body RequestBody dataJsonBody);

    @GET("cashout/v3/{account_id}/confirmation")
    Call<CashoutUserConfirm> getCashOutUserConfirm(@Path("account_id") String accountId,
                                                   @Query("amount") String amount,
                                                   @Query("card_number") String cardNumber,
                                                   @Query("card_token") String cardToken);

    @GET("products/v3/merchants/{account_id}/product/{product_id}")
    Call<Product> getProduct(@Path("account_id") String accountId,
                             @Path("product_id") String productId);

    @GET("transport/v3/transports/{product_id}")
    Call<Transport> getTransport(@Path("product_id") String productId);

    @GET("qrcode/v3/qr/{data}")
    Call<QRResponse> getQRData(@Path("data") String data);

    @PUT("credit/v3/password/{card_token}/accounts/{account_id}")
    Call<Void> setCreditCardPin(@Path("card_token") String cardToken,@Path("account_id") String accountId,
                                @Body RequestBody cardInfoJsonBody);

    @PUT("credit/v3/password/{card_token}/accounts/{account_id}")
    Call<Void> getResetPassword(@Path("card_token") String card_token,
                                @Path("account_id") String account_id,
                                @Body RequestBody cardInfoJsonBody);

    @PUT("credit/v3/password/{paygear_card_token}/accounts/{account_id}")
    Call<Void>setNewPassword(@Path("paygear_card_token") String PGCardToken,@Path("account_id") String accountId,@Body RequestBody passwordJson) ;

    @GET("credit/v3/password/{paygear_card_token}/accounts/{account_id}")
    Call<Void>getRecoverPasswordOTP(@Path("paygear_card_token") String PGCardToken,@Path("account_id") String accountId);

    @GET("payment/v3/accounts/{account_id}/ibans")
    Call<List<Iban>>getIbanList(@Path("account_id") String accountId);

    @POST("payment/v3/accounts/{account_id}/ibans")
    Call<Iban>addNewIban(@Path("account_id") String accountId,@Body RequestBody iban);

    @POST("users/v3/thirdparty/auth")
    Call<OTPVerifyResult> getIGapToken(@Body RequestBody requestBody, @Header("abcdef") String ip);

    @POST("qrcode/v3/qr/club/{sequence_number}")
    Call<ConfirmVoucherQr_Result>confirmVoucherQr(@Path("sequence_number") String sequenceNumber);


}
