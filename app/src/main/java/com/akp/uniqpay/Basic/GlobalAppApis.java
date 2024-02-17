package com.akp.uniqpay.Basic;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class GlobalAppApis {
    public String Login(String action, String pakid, String pass,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Packageid", pakid);
            jsonObject1.put("Password", pass);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String ChangePassword(String newpass, String oldpass, String uid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("NewPassword", newpass);
            jsonObject1.put("OldPassword", oldpass);
            jsonObject1.put("Userid", uid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String GenerateTPin(String NewPin, String Password, String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("NewPin", NewPin);
            jsonObject1.put("Password", Password);
            jsonObject1.put("Userid", Userid);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String ChangeTPin(String NewPin, String OldPin, String Password,String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("NewPin", NewPin);
            jsonObject1.put("OldPin", OldPin);
            jsonObject1.put("Password", Password);
            jsonObject1.put("Userid", Userid);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Dashboard(String action, String pakid, String pass,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Packageid", pakid);
            jsonObject1.put("Password", pass);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String UPITransfer(String amount, String custmob, String custname,String upiid,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Amount", amount);
            jsonObject1.put("CustomerMobileNo", custmob);
            jsonObject1.put("Name", custname);
            jsonObject1.put("UPIID", upiid);
            jsonObject1.put("UserId", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String PostAddSenderAPI(String add, String fname, String lname, String mob, String pincode, String state) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Address", add);
            jsonObject1.put("FirstName", fname);
            jsonObject1.put("LastName", lname);
            jsonObject1.put("MobileNo", mob);
            jsonObject1.put("PinCode", pincode);
            jsonObject1.put("State", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String PostGetCustomerAPI(String mob) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MobileNo", mob);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String GetProviderList(String ProviderId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("ProviderId", ProviderId);
            //ProviderId= Recharge|Bill
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String GetWallletAmount(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Operator(String action,String providerid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("ProviderId", providerid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String GetOpratorList_AnshPay(String providerid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("ProviderType", providerid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String PostRechargeAPI(String Amount, String Circle, String MobileNo, String UserId,String optr) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Amount", Amount);
            jsonObject1.put("OptName", Circle);
            jsonObject1.put("MobileNo", MobileNo);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("optr", optr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String TransactionReporty(String ac,String rtype, String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", ac);
            jsonObject1.put("ReportType", rtype);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

//    public String TransactionReportHistory(String fromdate, String memberid, String reporttype, String status, String todate, String transactiontype) {
//        JSONObject jsonObject1 = new JSONObject();
//        try {
//            jsonObject1.put("FromDate", fromdate);
//            jsonObject1.put("MemberId", memberid);
//            jsonObject1.put("ReportType", reporttype);
//            jsonObject1.put("Status", status);
//            jsonObject1.put("ToDate", todate);
//            jsonObject1.put("TransactionType", transactiontype);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject1.toString();
//    }
    public String UpdateProfile(String aacnname,String accno,String bankbranch,String bankname,String emailid,String gender,String ifsccode,String name,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("AccHolderName", aacnname);
            jsonObject1.put("AccNo", accno);
            jsonObject1.put("BankBranch", bankbranch);
            jsonObject1.put("BankName", bankname);
            jsonObject1.put("EmailId", emailid);
            jsonObject1.put("Gender", gender);
            jsonObject1.put("IfscCode", ifsccode);
            jsonObject1.put("Name", name);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Profile(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String Wallet(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String TopupDetails(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String MyReferral(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String MyTeam(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String DirectIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String LevelIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String ContractIncomeAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String ShowWithdrwarAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String WalletHistoryAPI(String action,String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", action);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Otpverify(String mobileNo, String otp) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MobileNo", mobileNo);
            jsonObject1.put("OTP", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String NewAccount(String EmailId, String MobileNo, String Name,String Password,String SponsorId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("EmailId", EmailId);
            jsonObject1.put("MobileNo", MobileNo);
            jsonObject1.put("Name", Name);
            jsonObject1.put("Password", Password);
            jsonObject1.put("SponsorId", SponsorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }






    public String DasActiveIncome(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String CheckSponserA(String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String Support(String Message,String Subject,String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "");
            jsonObject1.put("Message", Message);
            jsonObject1.put("Subject", Subject);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String PlanAPI(String method, String mob, String operator) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("MethodName", method);
            jsonObject1.put("Number",mob );
            jsonObject1.put("Operator", operator);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String GenerateUCC_ForAccount(String CustomerId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("CustomerId", CustomerId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String BillFetchAPI(String nub, String num, String sp_key) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("ClientRefId", nub);
            jsonObject1.put("Number", num);
            jsonObject1.put("Optional1", "8778787");
            jsonObject1.put("SPKey", sp_key);
            jsonObject1.put("TelecomCircleID", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Ans_BillFetchAPI(String AccountId, String OperatorId, String UserId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("AccountId", AccountId);
            jsonObject1.put("OperatorId", OperatorId);
            jsonObject1.put("UserId", UserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

/*    public String BillerAPI(String optype, String userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {

            jsonObject1.put("OperatorType", optype);
            jsonObject1.put("Userid", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }*/

    public String BillerAPI(String optype) {
        JSONObject jsonObject1 = new JSONObject();
        try {

            jsonObject1.put("ProviderType", optype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String GenerateCreditCardLeadAPI(String UserId, String address,String customer_type,
                                            String email,String landMark,String mobile_no,String monthly_income,String name,
                                            String pinCode,String state) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("address", address);
            jsonObject1.put("customer_type", customer_type);
            jsonObject1.put("email", email);
            jsonObject1.put("landMark", landMark);
            jsonObject1.put("mobile_no", mobile_no);
            jsonObject1.put("monthly_income", monthly_income);
            jsonObject1.put("name", name);
            jsonObject1.put("pinCode", pinCode);
            jsonObject1.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String GeneratePersonalLoanLeadAPI(String UserId, String address,String customer_type,
                                            String email,String landMark,String mobile_no,String monthly_income,String name,
                                            String pinCode,String state) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("address", address);
            jsonObject1.put("customer_type", customer_type);
            jsonObject1.put("email", email);
            jsonObject1.put("landMark", landMark);
            jsonObject1.put("mobile_no", mobile_no);
            jsonObject1.put("monthly_income", monthly_income);
            jsonObject1.put("name", name);
            jsonObject1.put("pinCode", pinCode);
            jsonObject1.put("state", state);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String BillPaymentAPI(String Amount, String Circle, String MobileNo, String UserId,String optr,
                                 String Optional1,String SPKey,
                                 String ClientRefId) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Amount", Amount);
            jsonObject1.put("TelecomCircleID", Circle);
            jsonObject1.put("Number", MobileNo);
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("OperatorName", optr);

            jsonObject1.put("Optional1", Optional1);
            jsonObject1.put("SPKey", SPKey);
            jsonObject1.put("ClientRefId", ClientRefId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String Ans_BillPaymentAPI(String AcceptPartPay,String AcceptPayment,String billamount, String billnetamount, String cellno, String maxbillamount, String username,
                                 String Amount, String bbpsservice, String mode,String operatorid,String optrname,
                                     String uniqid,String UserId,String AccountId) {
        JSONObject jsonObject1 = new JSONObject();
        JSONObject billFetchStr = new JSONObject();

        try {
            billFetchStr.put("AcceptPartPay", AcceptPartPay);
            billFetchStr.put("AcceptPayment", AcceptPayment);
            billFetchStr.put("BillAmount", billamount); // Replace with actual value
            billFetchStr.put("BillNetamount", billnetamount); // Replace with actual value
            billFetchStr.put("CellNumber", cellno); // Replace with actual value
            billFetchStr.put("MaxBillAmount", maxbillamount);
            billFetchStr.put("UserName", username); // Replace with actual value

            jsonObject1.put("Amount", Amount);
            jsonObject1.put("BBPSServiceName", bbpsservice); // Replace with actual value
            jsonObject1.put("BillFetchStr", billFetchStr);
            jsonObject1.put("Mode", mode); // Replace with actual value
            jsonObject1.put("OperatorId", operatorid); // Replace with actual value
            jsonObject1.put("OperatorName", optrname);
            jsonObject1.put("UniqueId", uniqid); // Replace with actual value
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("AccountId", AccountId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String CreatePancardAPI(String UserId, String email, String firstname,String gender,
                                              String kyctype,String lastname,String middlename,String mode,String title) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("UserId", UserId);
            jsonObject1.put("email", email);
            jsonObject1.put("firstname", firstname);
            jsonObject1.put("gender", gender);
            jsonObject1.put("kyctype", kyctype);
            jsonObject1.put("lastname", lastname);
            jsonObject1.put("middlename", middlename);
            jsonObject1.put("mode", mode);
            jsonObject1.put("title", title);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String UpdgrationPackgeList(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String AccountActivation(String LoginId,String Packid,String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("LoginId", LoginId);
            jsonObject1.put("PackageId", Packid);
            jsonObject1.put("Userid", Userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String AccountUpgradtion(String LoginId,String Packid,String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("LoginId", LoginId);
            jsonObject1.put("PackageId", Packid);
            jsonObject1.put("Userid", Userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }


    public String TopupReport(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }



    public String RechargeTransactionHistroyAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String IdToIdHistoryAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String BillPaymentTransactionHistroyAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String CreditCardTransactionHistroyAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String PersnalLoanTransactionHistroyAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }
    public String PanCardTransactionHistroyAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

    public String AccountUCCTransactionHistroyAPI(String Userid) {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("Action", "LoginId");
            jsonObject1.put("Userid", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject1.toString();
    }

}

