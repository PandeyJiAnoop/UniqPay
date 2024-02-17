package com.akp.uniqpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.LoginScreen;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.Basic.SplashScreen;
import com.akp.uniqpay.Dash.Das_ActiveIncome;
import com.akp.uniqpay.Dash.Das_ActiveWallet;
import com.akp.uniqpay.Dash.Das_AutoPool;
import com.akp.uniqpay.Dash.Das_CreditCardLead;
import com.akp.uniqpay.Dash.Das_GENERATEURL;
import com.akp.uniqpay.Dash.Das_HoldIncome;
import com.akp.uniqpay.Dash.Das_LoanGeneration;
import com.akp.uniqpay.Dash.Das_RechargePoint;
import com.akp.uniqpay.Dash.Das_ServiceIncome;
import com.akp.uniqpay.Dash.Das_UniqStore;
import com.akp.uniqpay.IncomeDetails.MatchingIncome;
import com.akp.uniqpay.IncomeDetails.Referralncome;
import com.akp.uniqpay.Dash.RewardIncome;
import com.akp.uniqpay.IncomeDetails.ShopingWallet;
import com.akp.uniqpay.Membership.MembershipTopupReport;
import com.akp.uniqpay.Membership.Membership_Lifetime;
import com.akp.uniqpay.Membership.Membership_Upgrade;
import com.akp.uniqpay.MyAccount.ChangePassword;
import com.akp.uniqpay.MyAccount.KYCDocuments;
import com.akp.uniqpay.MyAccount.MyProfile;
import com.akp.uniqpay.MyAccount.MyProfileWeb;
import com.akp.uniqpay.MyAccount.UpdateProfile;
import com.akp.uniqpay.MyNetwork.LevelIncome;
import com.akp.uniqpay.MyNetwork.PlanPurchaseWebview;
import com.akp.uniqpay.Recharge.BBPS_BillPayment;
import com.akp.uniqpay.Recharge.PAYQRSCAN;
import com.akp.uniqpay.Recharge.UPITransfer;
import com.akp.uniqpay.Recharge.ViewReport;
import com.akp.uniqpay.Report.Report_BankOpeningReport;
import com.akp.uniqpay.Report.Report_BillReport;
import com.akp.uniqpay.Report.Report_CreditCartReport;
import com.akp.uniqpay.Report.Report_IDToIDTransfer;
import com.akp.uniqpay.Report.Report_IDToIDTransferWebview;
import com.akp.uniqpay.Report.Report_MobileReport;
import com.akp.uniqpay.Report.Report_MyDirectTeam;
import com.akp.uniqpay.Report.Report_MyDirectTeamWebview;
import com.akp.uniqpay.Report.Report_MyLevelTeam;
import com.akp.uniqpay.Report.Report_PanGenerationReport;
import com.akp.uniqpay.Report.Report_PersonalLoanReport;
import com.akp.uniqpay.SlidingMenu.CustomerRegistrtaion;
import com.akp.uniqpay.SlidingMenu.LevelTreeWebview;
import com.akp.uniqpay.SlidingMenu.ReferralList;
import com.akp.uniqpay.Walet.AddFundWallet_Web;
import com.akp.uniqpay.Walet.IdToIdTransferWeb;
import com.akp.uniqpay.Walet.Withdrawal_History_Web;
import com.akp.uniqpay.Walet.Withdrawal_Web;
import com.akp.uniqpay.WebPages.Web_Contact;
import com.akp.uniqpay.WebPages.Web_GetGenerateUCCForAccount;
import com.akp.uniqpay.WebPages.Web_Policy;
import com.akp.uniqpay.WebPages.Web_Refund;
import com.akp.uniqpay.WebPages.Web_Terms;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class DashboardActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    LinearLayout one, three;
    private ImageView sliding_menu;
    private DrawerLayout mDrawer;
    LinearLayout one_ll, two_ll,ll_two, ll_three;
    int i = 0;
    LinearLayout logout_ll, dash_ll;
    LinearLayout update_ll, myprofile_ll, password_ll, kyc_ll, address_ll, order_ll, level_income_ll, withdraw_ll;
    LinearLayout refferal_ll, mytree_ll, downlineleftright_ll;
    LinearLayout referral_ll, matching_ll, contract_level_ll;
    LinearLayout cust_reg_ll;
    String UserId, UserPass, UserRole, UserName;
    TextView packege_tv, team_tv, total_active_team_tv, inactive_team_tv, level_income_tv, withdraw_tv, contratct_income_tv, direct_saponser_income_tv, total_business_tv, toatl_income_tv, user_id_tv, user_name_tv, sponser_id_tv, activaation_date_tv,
            email_tv, mobile_tv;
    TextView referral_tv;
    LinearLayout share_ll;
    LinearLayout package_ll, total_team_ll, total_active_team_ll, total_incative_team_ll, das_withdraw_ll, contact_ll, direct_sponsor_income_ll, total_business_ll, total_income_ll, das_level_income_ll;
    LinearLayout view_report_ll, money_transfer_ll;
    RecyclerView cust_recyclerView,cust_recyclerView1;
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    TextView daily_tv, direct_tv, level_tv, package_tv, total_team_tv, salary_tv, total_bonous_tv, balance_tv, active_tema_tv, direct_sponser_income_tv;
    private SharedPreferences res_preference;
    private SharedPreferences.Editor res_editor;
    TextView pack_tv;
    TextView scan_pay_tv;
    SliderLayout sliderLayout;
    LinearLayout contactus_ll, policy_ll, refund_ll, terms_ll, m_lifetime_ll, m_upgrade_ll, rewards_ll, GenerateUCC_ForAccountll;
    TextView username_tv, userid_tv;
    TextView active_income_tv, hold_income_tv, recharge_point_tv, active_wallet_tv, autopool_tv, service_income_tv, uniqstore_tv,rewards_tv;
    LinearLayout active_income_ll, hold_income_ll, recharge_point_ll, active_wallet_ll, autopool_ll, service_income_ll, uniqstore_ll, rateus_ll, complaint_ll;
    ImageView whatsap_ll;
    LinearLayout account_opening_ll,credit_card_lead_ll,loan_generation_ll,generate_url_ll,withdraw_hist_ll,topup_ll,
            recharge_ll,bill_ll,creditcard_ll,personalloan_ll,bankopening_ll,pangen_ll;
    private android.app.AlertDialog alertDialog;
    TextView status_tv;
    EditText rupee_et,mobile_et,w_rupee_et;
    String FriendUserId;
    LinearLayout amount_ll;
    private android.app.AlertDialog alertDialog1;
    SwipeRefreshLayout srl_refresh;
    TextView tab1,tab2,tab3,tab4,maintainence_tv;
    LinearLayout two;
    ImageView notification_img;
    LinearLayout bbps_ll,levelteam_ll,directteam_ll,id_toid_ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        UserName = sharedPreferences.getString("U_name", "");
        Log.e("userId_Show",UserId);
        Toast.makeText(getApplicationContext(),"Login Customer UserId:-"+UserId,Toast.LENGTH_LONG).show();
        FindId();
        OnClick();
        getHistory();
        getHistory1();
        getWalletHistory(UserId);
        Das();
        setSliderViews();

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(DashboardActivity.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(DashboardActivity.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                } }});
//        contactUs();
    }

    private void OnClick() {
        account_opening_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateUCC_ForAccountAPI();
            }});
        notification_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationList.class);
                startActivity(intent);
            }});
        credit_card_lead_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Das_CreditCardLead.class);
                        startActivity(intent);
                    } });
        loan_generation_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Das_LoanGeneration.class);
                startActivity(intent);
            }});
//        Toast.makeText(getApplicationContext(),UserId,Toast.LENGTH_LONG).show();
        // casting of textview
        scan_pay_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle(Html.fromHtml("<font color='#9C27B0'>Select UPI Transfer Mode</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#D99D30'>Enter UPI Id</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), UPITransfer.class);
                        intent.putExtra("moneytype", "enter");
                        startActivity(intent);
                    }});
                builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Scan UPI QR</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), PAYQRSCAN.class);
                        startActivity(intent);
                    }});
                builder.create();
                builder.show();
            }});
        active_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, Das_ActiveIncome.class);
//                startActivity(intent);
            }});
        hold_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, Das_HoldIncome.class);
//                startActivity(intent);
            }});
        recharge_point_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Das_RechargePoint.class);
                startActivity(intent);
            }});
        active_wallet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Das_ActiveWallet.class);
                startActivity(intent);
            }});
        autopool_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, Das_AutoPool.class);
//                startActivity(intent);
            }});
        service_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, Das_ServiceIncome.class);
//                startActivity(intent);
            }});
        uniqstore_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, Das_UniqStore.class);
//                startActivity(intent);
            }});
        money_transfer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(DashboardActivity.this, MoneyTransfer.class);
//                startActivity(intent);
            }});
        view_report_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ViewReport.class);
                startActivity(intent);
            }});
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    one_ll.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    one_ll.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }});
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    two_ll.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    two_ll.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }});
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    ll_three.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    ll_three.setVisibility(View.VISIBLE);
                    i = 0;
                }}});
        logout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this).setTitle("Logout")
                        .setMessage("Are you sure want to Logout").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences myPrefs = getSharedPreferences("login_preference", MODE_PRIVATE);
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                                startActivity(intent);
                                //startActivity(i);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }});
        update_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfileWeb.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        dash_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.START);
            }
        });
        cust_reg_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerRegistrtaion.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        myprofile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfileWeb.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        password_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        withdraw_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Withdrawal_Web.class);
                startActivity(intent);
            }});
        kyc_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KYCDocuments.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        rewards_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RewardIncome.class);
//                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        referral_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Referralncome.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        level_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LevelIncome.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        matching_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MatchingIncome.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        contract_level_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopingWallet.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        refferal_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReferralList.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            }});
        package_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlanPurchaseWebview.class);
                startActivity(intent);
            }});
        total_team_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), B.class);
//                startActivity(intent);
            }});
        total_active_team_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LevelTreeWebview.class);
                startActivity(intent);
            }});
        total_incative_team_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }});
        das_withdraw_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Withdrawal_Web.class);
                startActivity(intent);
            }
        });
        contact_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShopingWallet.class);
                startActivity(intent);
            }
        });
        direct_sponsor_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReferralList.class);
                startActivity(intent);
            }
        });
        das_level_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(getApplicationContext(), LevelIncome.class);
//                startActivity(intent);
            }
        });
        total_business_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        total_income_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        referral_tv.setText("https://uniqpay.in/Account/CustomerSignUp?Id=" + UserId);

        share_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = referral_tv.getText().toString();
                String sub = "Hey check out app and use My Referral Link";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
                myIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(myIntent, "UniqPay Referral Link"));
            } });
//        getDashboardCatAPI();
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        sliding_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                mDrawer.openDrawer(Gravity.START);
                mDrawer.openDrawer(Gravity.START);
            } });
        contactus_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Web_Contact.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        policy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Web_Policy.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        refund_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Web_Refund.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        terms_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Web_Terms.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        rateus_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRateDialog(DashboardActivity.this);
                mDrawer.closeDrawer(Gravity.START);
            } });
        m_lifetime_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Membership_Lifetime.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        m_upgrade_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Membership_Upgrade.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        complaint_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tickets.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
        whatsap_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsNumber = "7318294548"; //without '+'
                String message = "Hello UniqPay  I need a Help?";
                String appPackage = "";
                if (isAppInstalled(DashboardActivity.this, "com.whatsapp.w4b")) {
                    appPackage = "com.whatsapp.w4b";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage(appPackage);
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + message;
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);
                    //do ...
                } else if (isAppInstalled(DashboardActivity.this, "com.whatsapp")) {
                    appPackage = "com.whatsapp";
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage(appPackage);
// you can remove this part ("&text=" + "your message")
                    String url = "https://api.whatsapp.com/send?phone=" + smsNumber + "&text=" + message;
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);
                    //do ...
                } else {
                    Toast.makeText(DashboardActivity.this, "whatsApp is not installed", Toast.LENGTH_LONG).show();
                } }});
        GenerateUCC_ForAccountll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserId.equalsIgnoreCase("")){
                    Intent intent=new Intent(getApplicationContext(), LoginScreen.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(getApplicationContext(), IdToIdTransferWeb.class);
                    startActivity(intent);
//                    fundfriendttransferpopup();
                }
//                GenerateUCC_ForAccountAPI();
                mDrawer.closeDrawer(Gravity.START);
            } });
        generate_url_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Das_GENERATEURL.class);
                startActivity(intent);
                mDrawer.closeDrawer(Gravity.START);
            } });
//        Toast.makeText(getApplicationContext(),"Data Not Found!",Toast.LENGTH_LONG).show();
    }

    private void FindId() {
        TextView txtMarquee = (TextView) findViewById(R.id.marqueeText);
        txtMarquee.setSelected(true);
        generate_url_ll=findViewById(R.id.generate_url_ll);
        account_opening_ll=findViewById(R.id.account_opening_ll);
        notification_img=findViewById(R.id.notification_img);
        credit_card_lead_ll=findViewById(R.id.credit_card_lead_ll);
        loan_generation_ll=findViewById(R.id.loan_generation_ll);
        active_income_tv = findViewById(R.id.active_income_tv);
        hold_income_tv = findViewById(R.id.hold_income_tv);
        recharge_point_tv = findViewById(R.id.recharge_point_tv);
        active_wallet_tv = findViewById(R.id.active_wallet_tv);
        autopool_tv = findViewById(R.id.autopool_tv);
        service_income_tv = findViewById(R.id.service_income_tv);
        uniqstore_tv = findViewById(R.id.uniqstore_tv);
        rewards_tv = findViewById(R.id.rewards_tv);
        srl_refresh= findViewById(R.id.srl_refresh);
        active_income_ll = findViewById(R.id.active_income_ll);
        hold_income_ll = findViewById(R.id.hold_income_ll);
        recharge_point_ll = findViewById(R.id.recharge_point_ll);
        active_wallet_ll = findViewById(R.id.active_wallet_ll);
        autopool_ll = findViewById(R.id.autopool_ll);
        service_income_ll = findViewById(R.id.service_income_ll);
        uniqstore_ll = findViewById(R.id.uniqstore_ll);
        rewards_ll = findViewById(R.id.rewards_ll);
        username_tv = findViewById(R.id.username_tv);
        userid_tv = findViewById(R.id.userid_tv);
        whatsap_ll = findViewById(R.id.whatsap_ll);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        topup_ll= findViewById(R.id.topup_ll);
        recharge_ll= findViewById(R.id.recharge_ll);
        bill_ll= findViewById(R.id.bill_ll);
        creditcard_ll= findViewById(R.id.creditcard_ll);
        personalloan_ll= findViewById(R.id.personalloan_ll);
        bankopening_ll= findViewById(R.id.bankopening_ll);
        pangen_ll= findViewById(R.id.pangen_ll);
        one_ll = findViewById(R.id.one_ll);
        two_ll = findViewById(R.id.two_ll);
        ll_two = findViewById(R.id.ll_two);
        ll_three = findViewById(R.id.ll_three);
        logout_ll = findViewById(R.id.logout_ll);
        dash_ll = findViewById(R.id.dash_ll);
        update_ll = findViewById(R.id.update_ll);
        myprofile_ll = findViewById(R.id.myprofile_ll);
        password_ll = findViewById(R.id.password_ll);
        kyc_ll = findViewById(R.id.kyc_ll);
        address_ll = findViewById(R.id.address_ll);
        order_ll = findViewById(R.id.order_ll);
        withdraw_ll = findViewById(R.id.withdraw_ll);
        withdraw_hist_ll= findViewById(R.id.withdraw_hist_ll);
        contactus_ll = findViewById(R.id.contactus_ll);
        policy_ll = findViewById(R.id.policy_ll);
        refund_ll = findViewById(R.id.refund_ll);
        terms_ll = findViewById(R.id.terms_ll);
        rateus_ll = findViewById(R.id.rateus_ll);
        complaint_ll = findViewById(R.id.complaint_ll);

        referral_tv = findViewById(R.id.referral_tv);
        share_ll = findViewById(R.id.share_ll);
        m_lifetime_ll = findViewById(R.id.m_lifetime_ll);
        m_upgrade_ll = findViewById(R.id.m_upgrade_ll);

        package_ll = findViewById(R.id.package_ll);
        total_team_ll = findViewById(R.id.total_team_ll);
        total_active_team_ll = findViewById(R.id.total_active_team_ll);
        total_incative_team_ll = findViewById(R.id.total_incative_team_ll);
        das_withdraw_ll = findViewById(R.id.das_withdraw_ll);
        contact_ll = findViewById(R.id.contact_ll);
        direct_sponsor_income_ll = findViewById(R.id.direct_sponsor_income_ll);
        total_business_ll = findViewById(R.id.total_business_ll);
        total_income_ll = findViewById(R.id.total_income_ll);
        das_level_income_ll = findViewById(R.id.das_level_income_ll);
        direct_sponser_income_tv = findViewById(R.id.direct_sponser_income_tv);
        pack_tv = findViewById(R.id.pack_tv);

        daily_tv = findViewById(R.id.daily_tv);
        direct_tv = findViewById(R.id.direct_tv);
        level_tv = findViewById(R.id.level_tv);
        package_tv = findViewById(R.id.package_tv);
        total_business_tv = findViewById(R.id.total_business_tv);
        total_team_tv = findViewById(R.id.total_team_tv);
        salary_tv = findViewById(R.id.salary_tv);
        total_bonous_tv = findViewById(R.id.total_bonous_tv);
        withdraw_tv = findViewById(R.id.withdraw_tv);
        balance_tv = findViewById(R.id.balance_tv);
        active_tema_tv = findViewById(R.id.active_tema_tv);
        inactive_team_tv = findViewById(R.id.inactive_team_tv);

        user_id_tv = findViewById(R.id.user_id_tv);
        user_name_tv = findViewById(R.id.user_name_tv);
        sponser_id_tv = findViewById(R.id.sponser_id_tv);
        activaation_date_tv = findViewById(R.id.activaation_date_tv);
        email_tv = findViewById(R.id.email_tv);
        mobile_tv = findViewById(R.id.mobile_tv);
        level_income_ll = findViewById(R.id.level_income_ll);

        refferal_ll = findViewById(R.id.refferal_ll);
        mytree_ll = findViewById(R.id.mytree_ll);
        downlineleftright_ll = findViewById(R.id.downlineleftright_ll);

        referral_ll = findViewById(R.id.referral_ll);
        matching_ll = findViewById(R.id.matching_ll);
        contract_level_ll = findViewById(R.id.contract_level_ll);

        sliding_menu = (ImageView) findViewById(R.id.sliding_menu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        cust_reg_ll = findViewById(R.id.cust_reg_ll);
        GenerateUCC_ForAccountll = findViewById(R.id.GenerateUCC_ForAccountll);

        cust_recyclerView = findViewById(R.id.cust_recyclerView);
        cust_recyclerView1 = findViewById(R.id.cust_recyclerView1);
        money_transfer_ll = findViewById(R.id.money_transfer_ll);
        view_report_ll = findViewById(R.id.view_report_ll);
        scan_pay_tv = findViewById(R.id.scan_pay_tv);
        bbps_ll = findViewById(R.id.bbps_ll);
        directteam_ll = findViewById(R.id.directteam_ll);
        levelteam_ll = findViewById(R.id.levelteam_ll);
        id_toid_ll = findViewById(R.id.id_toid_ll);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        maintainence_tv = findViewById(R.id.maintainence_tv);

        tab1.setText("\uD83D\uDC49 5% Redeem from Recharge points \n" +
                "\uD83D\uDC49 1 % Cashback");
        tab2.setText("\uD83D\uDC49Cashback of  0.25%  for Basic/Classic Members  only\n" +
                "\uD83D\uDC49Settlement time 3 hrs to 2 working days");
        tab3.setText("Online\n" +
                "Instant  settlement");
        tab4.setText("( Attractive Cashback & Recharge points on successful approval)");

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.blink);
        maintainence_tv.setAnimation(uptodown);

        topup_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MembershipTopupReport.class);
                startActivity(intent);
            }});
        recharge_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_MobileReport.class);
                startActivity(intent);
            }
        });
        bill_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_BillReport.class);
                startActivity(intent);
            } });
        creditcard_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_CreditCartReport.class);
                startActivity(intent);
            } });
        personalloan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_PersonalLoanReport.class);
                startActivity(intent);
            } });
        bankopening_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_BankOpeningReport.class);
                startActivity(intent);
            } });
        pangen_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_PanGenerationReport.class);
                startActivity(intent);
            } });
        withdraw_hist_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Withdrawal_History_Web.class);
                startActivity(intent);
            } });
        bbps_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), BBPS_BillPayment.class);
                startActivity(intent);
            } });

        levelteam_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_MyLevelTeam.class);
                startActivity(intent);
            } });

        directteam_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_MyDirectTeamWebview.class);

//                Intent intent=new Intent(getApplicationContext(), Report_MyDirectTeam.class);
                startActivity(intent);
            } });

        id_toid_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Report_IDToIDTransferWebview.class);

//                Intent intent=new Intent(getApplicationContext(), Report_IDToIDTransfer.class);
                startActivity(intent);
            } });
    }

    public void getHistory() {
        String otp1 = new GlobalAppApis().GetProviderList("Recharge");
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetProviderListService(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("fsasfaf",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
//                jsonString = jsonString.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>"," ");
//                jsonString = jsonString.replace("<string xmlns=\"http://tempuri.org/\">"," ");
//                jsonString = jsonString.replace("</string>"," ");
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayr = jsonObject.getJSONArray("ProviderRes");
                    for (int i = 0; i < jsonArrayr.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("ProviderName", jsonObject1.getString("ProviderName"));
                        hm.put("Image", jsonObject1.getString("Image"));
                        hm.put("ProviderId", jsonObject1.getString("ProviderId"));
                        hm.put("MethodType", jsonObject1.getString("MethodType"));
                        arrayList1.add(hm);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    DasRechargeListAdapter customerListAdapter = new DasRechargeListAdapter(getApplicationContext(), arrayList1);
                    cust_recyclerView.setLayoutManager(gridLayoutManager);
                    cust_recyclerView.setAdapter(customerListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, DashboardActivity.this, call1, "", true);
    }

    public void getHistory1() {
        String otp1 = new GlobalAppApis().GetProviderList("Bill");
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetProviderListService(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("fsasfaf",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
//                jsonString = jsonString.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>"," ");
//                jsonString = jsonString.replace("<string xmlns=\"http://tempuri.org/\">"," ");
//                jsonString = jsonString.replace("</string>"," ");
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayr = jsonObject.getJSONArray("ProviderRes");
                    for (int i = 0; i < jsonArrayr.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("ProviderName", jsonObject1.getString("ProviderName"));
                        hm.put("Image", jsonObject1.getString("Image"));
                        hm.put("ProviderId", jsonObject1.getString("ProviderId"));
                        hm.put("MethodType", jsonObject1.getString("MethodType"));
                        arrayList2.add(hm);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    DasRechargeListAdapter customerListAdapter = new DasRechargeListAdapter(getApplicationContext(), arrayList2);
                    cust_recyclerView1.setLayoutManager(gridLayoutManager);
                    cust_recyclerView1.setAdapter(customerListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, DashboardActivity.this, call1, "", true);
    }

    public void Das() {
        String otp1 = new GlobalAppApis().Dashboard("4", "", "", UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getDashboard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                Log.d("abcresss", "des" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String activationDate = jsonobject.getString("ActivationDate");
                        String activeTeam = jsonobject.getString("ActiveTeam");
                        String contractIncome = jsonobject.getString("ContractIncome");
                        String customerId = jsonobject.getString("CustomerId");

                        String directSponsorIncome = jsonobject.getString("DirectSponsorIncome");
                        String emailId = jsonobject.getString("EmailId");
                        String inActiveTeam = jsonobject.getString("InActiveTeam");
                        String levelIncome = jsonobject.getString("LevelIncome");
                        String mobileNo = jsonobject.getString("MobileNo");
                        String name = jsonobject.getString("Name");
                        String Package = jsonobject.getString("Package");
                        String shibaCoin = jsonobject.getString("ShibaCoin");

                        String sponsorId = jsonobject.getString("SponsorId");
                        String totalBussiness = jsonobject.getString("TotalBussiness");
                        String totalIncome = jsonobject.getString("TotalIncome");
                        String totalTeam = jsonobject.getString("TotalTeam");
                        String Tpin = jsonobject.getString("Tpin");
                        String withdrawal = jsonobject.getString("Withdrawal");

//                        Id Activation condition
                        String IdActiveStatus = jsonobject.getString("IdActiveStatus");
                        if (IdActiveStatus.equalsIgnoreCase("0")){
                            AlertDialog.Builder builder11 = new AlertDialog.Builder(DashboardActivity.this);
                            builder11.setTitle("Alert! InActive Member ID")
                                    .setMessage(Html.fromHtml("<b>"+"\uD83D\uDE4F Your user  I'd  is still not activated with any membership plan...To enjoy unlimited benefits please activate it with Life time Membership plans of UniqPay \uD83D\uDE4F"+"</b>"))
                                    .setCancelable(false)
                                    .setIcon(R.drawable.logo)
                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel(); } });
                            builder11.create().show(); }
//                        TPin status check Condition
                        String TpinGenStatus = jsonobject.getString("TpinGenStatus");
                        if (TpinGenStatus.equalsIgnoreCase("0")){
                            AlertDialog.Builder builder11 = new AlertDialog.Builder(DashboardActivity.this);
                            builder11.setTitle("Alert! T-Pin Not Found!")
                                    .setMessage(Html.fromHtml("<b>"+"Please Generate Your T-Pin (Transaction Pin )"+"</b>"))
                                    .setCancelable(false)
                                    .setIcon(R.drawable.logo)
                                    .setNegativeButton("Generate", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                           GenerateTPinpopup();
                                            dialog.cancel();
                                        }});
                            builder11.create().show(); }

//                        Dashboard data set vale
                        active_income_tv.setText("\u20B9 " +jsonobject.getString("ActiveIncome"));
                        hold_income_tv.setText("\u20B9 " +jsonobject.getString("HoldIncome"));
                        recharge_point_tv.setText("\u20B9 " +jsonobject.getString("RechargePoint"));
                        active_wallet_tv.setText("\u20B9 " +jsonobject.getString("ActiveWallet"));
                        autopool_tv.setText("\u20B9 " +jsonobject.getString("PoolIncome"));
                        service_income_tv.setText("\u20B9 " +jsonobject.getString("ServiceIncome"));
                        uniqstore_tv.setText("\u20B9 " +jsonobject.getString("UniqStoreIncome"));
                        rewards_tv.setText("\u20B9 " +jsonobject.getString("CashReward"));
                        String APB1Tv = jsonobject.getString("APB1");

                        if (APB1Tv.equalsIgnoreCase("0")) {
                            package_tv.setText("Expired");
                        }
                        package_tv.setText(Package);
                        username_tv.setText(name);
                        userid_tv.setText(UserId);
                        res_preference = getSharedPreferences("res_preference", MODE_PRIVATE);
                        res_editor = res_preference.edit();
                        res_editor.putString("U_mob", mobileNo);
                        res_editor.commit();
                        pack_tv.setText("Package\n(" + Package + ")");
                        total_business_tv.setText("\u20B9" + totalBussiness);
                        total_team_tv.setText(totalTeam);
                        withdraw_tv.setText(withdrawal);
                        active_tema_tv.setText(activeTeam);
                        inactive_team_tv.setText(inActiveTeam);
                        level_tv.setText("\u20B9" + levelIncome);
                        direct_tv.setText("\u20B9" + contractIncome);
                        daily_tv.setText("\u20B9" + totalIncome);
                        direct_sponser_income_tv.setText("\u20B9" + directSponsorIncome);
                        total_bonous_tv.setText("\u20B9" + totalIncome);
                        user_id_tv.setText(UserId);
                        user_name_tv.setText(name);
                        sponser_id_tv.setText(sponsorId);
                        activaation_date_tv.setText(activationDate);
                        email_tv.setText(emailId);
                        mobile_tv.setText(mobileNo);
                        withdraw_tv.setText("\u20B9" + withdrawal);
//                        balance_tv.setText("\u20B9"+balance);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } } }, DashboardActivity.this, call1, "", true);
    }

    public void getWalletHistory(String uid) {
        String otp1 = new GlobalAppApis().GetWallletAmount(uid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetWalletService(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayr = jsonObject.getJSONArray("LoginRes");
                    for (int i = 0; i < jsonArrayr.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } } }, DashboardActivity.this, call1, "", true);
    }

    private void setSliderViews() {
        for (int i = 0; i <= 5; i++) {
            SliderView sliderView = new SliderView(DashboardActivity.this);
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.ban_1);
                    sliderView.setDescription("Welcome To\n" + "UniqPay Company");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.ban_2);
//                    sliderView.setDescription("सच होगा सपना");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.ban_3);
//                    sliderView.setDescription("सोचो  एक  नयी  दुनिया ");
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.banner);
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.five);
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 5:
                    sliderView.setImageDrawable(R.drawable.three);
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(DashboardActivity.this, "Welcome To UniqPay " + (finalI + 1), Toast.LENGTH_SHORT).show();
                } });
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        } }

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bnav_Home:
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                return true;
            case R.id.bnav_addfund:
                startActivity(new Intent(DashboardActivity.this, AddFundWallet_Web.class));
                overridePendingTransition(0, 0);
                return true;
            case R.id.bnav_withdraw:
                startActivity(new Intent(DashboardActivity.this, Withdrawal_Web.class));
                overridePendingTransition(0, 0);
                return true; }
        return false;
    }

    private boolean isAppInstalled(Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed; }

    public static void showRateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Rate UniqPay Mobile Application")
                .setMessage("Please, rate the app at PlayStore")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context != null) {
                            String link = "market://details?id=";
                            try {
                                // play market available
                                context.getPackageManager().getPackageInfo("com.akp.uniqpay", 0);
                                // not available
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                                // should use browser
                                link = "https://play.google.com/store/apps/details?id=";
                            }
                            // starts external action
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link + context.getPackageName())));
                        }}})
                .setNegativeButton("CANCEL", null); builder.show();
    }

    public void GenerateUCC_ForAccountAPI() {
        String otp1 = new GlobalAppApis().GenerateUCC_ForAccount(UserId);
        Log.d("res_check",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetGenerateUCC_ForAccount(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("res_check",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray jsonArrayr = jsonObject.getJSONArray("UCCRes");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                            Intent intent = new Intent(getApplicationContext(), Web_GetGenerateUCCForAccount.class);
                            intent.putExtra("url", jsonObject1.getString("data"));
                            startActivity(intent);
                        } } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                    } } catch (JSONException e) {
                    e.printStackTrace();
                } } }, DashboardActivity.this, call1, "", true);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to Exit From App?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    } })
                .setNegativeButton("No", null).show(); }

    private void GenerateTPinpopup() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.friend_withdrawpopup, viewGroup, false);
        TextInputEditText edt_pass=dialogView.findViewById(R.id.edt_pass);
        TextInputEditText edt_new_pin=dialogView.findViewById(R.id.edt_new_pin);
        TextInputEditText edt_con_pin=dialogView.findViewById(R.id.edt_con_pin);
        AppCompatButton save_pin=dialogView.findViewById(R.id.save_pin);
        save_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_pass.getText().toString().equalsIgnoreCase("")){
                    edt_pass.setError("Fields can't be blank!");
                    edt_pass.requestFocus();
                }
                else if (edt_new_pin.getText().toString().equalsIgnoreCase("")){
                    edt_new_pin.setError("Fields can't be blank!");
                    edt_new_pin.requestFocus();
                }
                else if (edt_con_pin.getText().toString().equalsIgnoreCase("")){
                    edt_con_pin.setError("Fields can't be blank!");
                    edt_con_pin.requestFocus();
                }
                else if(!edt_new_pin.getText().toString().equals(edt_con_pin.getText().toString())){
                    //Toast is the pop up message
                    Toast.makeText(getApplicationContext(), "T-Pin Not matched!", Toast.LENGTH_LONG).show();
                }
                else {
                    Generate_T_Password(edt_new_pin.getText().toString(),edt_pass.getText().toString(),UserId);
                }}});
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void Generate_T_Password(String NewPin, String Password, String Userid) {
        String otp1 = new GlobalAppApis().GenerateTPin(NewPin,Password,Userid);
        Log.d("otp1","cxc"+otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GenerateTPin_url(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("GenerateTPin_url","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
//                        JSONArray Response = object.getJSONArray("Response");
//                        for (int i = 0; i < Response.length(); i++) {
//                            JSONObject jsonObject = Response.getJSONObject(i);
                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder11 = new AlertDialog.Builder(DashboardActivity.this);
                            builder11.setTitle("Successfully")
                                    .setMessage(object.getString("Message"))
                                    .setCancelable(false)
                                    .setIcon(R.drawable.logo)
                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            overridePendingTransition(0, 0);
                                            startActivity(getIntent());
                                            overridePendingTransition(0, 0);
                                            dialog.cancel();
                                        }});builder11.create().show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }} catch (JSONException e) {
                    e.printStackTrace();
                }}
        }, DashboardActivity.this, call1, "", true);
    }
}
