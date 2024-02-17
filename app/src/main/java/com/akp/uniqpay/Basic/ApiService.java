package com.akp.uniqpay.Basic;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("Login")
    Call<String> getLogin(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ChangePassword")
    Call<String> changepassAPI(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GenerateTPin")
    Call<String> GenerateTPin_url(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ChangeTPin")
    Call<String> ChangeTPin_url(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Dashboard")
    Call<String> getDashboard(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("UPITransfer")
    Call<String> getUPITransfer(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Profile")
    Call<String> getProfile(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ProfileUpdate")
    Call<String> getUpdateProfile(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("TopupDetails")
    Call<String> getTopupDetails(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetWallet")
    Call<String> getWallet(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("MyRefferal")
    Call<String> getReferral(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("MyTeam")
    Call<String> getMyTeam(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("DirectIncome")
    Call<String> getDirectincome(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("LevelIncome")
    Call<String> getLevelincome(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ROIBonus")
    Call<String> getContractincome(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("OpenWithdrawal")
    Call<String> getwithdrwar(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("WithdrawalHistory")
    Call<String> getWalletHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @GET("GetServiceList")
    Call<String> OperatorList();
    @Headers("Content-Type: application/json")
    @POST("GetProviderList")
    Call<String> GetProviderListService(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetWallet")
    Call<String> GetWalletService(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetOpratorList")
    Call<String> OperatorService(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetOpratorList_AnshPay")
    Call<String> get_GetOpratorList_AnshPay(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("PostRecharge")
    Call<String> PostRecharge(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("MakeRecharge_AnshPay")
    Call<String> MakeRecharge_AnshPay(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("BillPayment")
    Call<String> BillPayment(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("BillPayment_AnshPay")
    Call<String> BillPayment_AnshPayAPI(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("AddSender")
    Call<String> AddSenderAPI(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Otpverify")
    Call<String> Otpverify(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetCustomer")
    Call<String> GetCustomerAPI(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("AllTransHistory")
    Call<String> TransactionReportAPI(
            @Body String body);
    @Headers("Content-Type: application/json")
    @GET("GetCountryList")
    Call<String> API_GetCountryList();

    @Headers("Content-Type: application/json")
    @POST("Register")
    Call<String> getRegister(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ActiveIncomeHistory")
    Call<String> getActiveIncomeHistory(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("RewardIncomeHistory")
    Call<String> getRewardIncomeHistory(
            @Body String body);


    @Headers("Content-Type: application/json")
    @POST("ActiveWalletHistory")
    Call<String> getActiveWalletHistory(
            @Body String body);

    @Headers("Content-Type: application/json")
    @POST("WalletTransHistory")
    Call<String> getWalletTransHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("HoldIncomeHistory")
    Call<String> getHoldIncomeHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("RechragePointHistroy")
    Call<String> getRechragePointHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("ServiceIncomeHistory")
    Call<String> getServiceIncomeHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("TotalIncomeHistory")
    Call<String> getTotalIncomeHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("UniqueStoreIncomeHistory")
    Call<String> getUniqueStoreIncomeHistory(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetSponserDetails")
    Call<String> getCheckSponser(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("Support")
    Call<String> getSupport(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GetAllPlan_MPLANAPI")
    Call<String> GetAllPlan(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GenerateUCC_ForAccount")
    Call<String> GetGenerateUCC_ForAccount(
            @Body String body);
/*    @Headers("Content-Type: application/json")
    @POST("BillerList")
    Call<String> BillerListAPI(
            @Body String body);*/
    @Headers("Content-Type: application/json")
    @POST("GetBBPSOperator_AnshPay")
    Call<String> GetBBPSOperator_AnshPayAPI(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("BillFetch")
    Call<String> getBillFetch(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("BillFetch_AnshPay")
    Call<String> getBillFetch_AnshPay(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GenerateCreditCardLead")
    Call<String> getGenerateCreditCardLead(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("GeneratePersonalLoanLead")
    Call<String> getGeneratePersonalLoanLead(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("CreatePancard")
    Call<String> getCreatePancard(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("UpdgrationPackgeList")
    Call<String> GetUpdgrationPackgeList(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("AccountActivation")
    Call<String> GetAccountActivation(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("AccountUpgradtion")
    Call<String> GetAccountUpgradtion(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("TopupReport")
    Call<String> GetTopupReport(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("RechargeTransactionHistroy")
    Call<String> RechargeTransactionHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("BillPaymentTransactionHistroy")
    Call<String> BillPaymentTransactionHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("CreditCardTransactionHistroy")
    Call<String> CreditCardTransactionHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("PersnalLoanTransactionHistroy")
    Call<String> PersnalLoanTransactionHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("PanCardTransactionHistroy")
    Call<String> PanCardTransactionHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("AccountUCCTransactionHistroy")
    Call<String> AccountUCCTransactionHistroy(
            @Body String body);
    @Headers("Content-Type: application/json")
    @POST("IdToIdHistory")
    Call<String> IdToIdHistory(
            @Body String body);
}