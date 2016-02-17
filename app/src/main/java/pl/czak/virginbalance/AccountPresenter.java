package pl.czak.virginbalance;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by czak on 17/02/16.
 */
public class AccountPresenter {
    private Account account;
    private Context context;

    public AccountPresenter(Account account, Context context) {
        this.account = account;
        this.context = context;
    }

    public int getLayoutId() {
        boolean hasComplexBundle =
                account.getComplexBundleState().equals("ACTIVATED");
        return hasComplexBundle ? R.layout.widget_bundle : R.layout.widget_nobundle;
    }

    public String getBalanceAmount() {
        double balance = account.getGeneralBalance().getQuantity();
        return String.format("%.02f zł", balance);
    }

    public String getAccountTariff() {
        return context.getString(R.string.account_tariff, account.getTariffName());
    }

    public String getAccountValidity() {
        Date date = account.getAccountValidDate();
        return context.getString(R.string.account_validity, date);
    }

    public String getPackageName() {
        String packageName = account.getComplexBundleName();
        return context.getString(R.string.package_name, packageName);
    }

    public String getPackageMinutes() {
        Balance voiceBalance = account.getComplexBundleVoiceBalance();
        if (voiceBalance == null) voiceBalance = account.getVoiceBalance();
        if (voiceBalance == null) return "0";
        return voiceBalance.isUnlimited() ?
                context.getString(R.string.unlimited) :
                Integer.toString((int) voiceBalance.getQuantity() / 60);
    }

    public String getPackageMinutesValidity() {
        Balance voiceBalance = account.getComplexBundleVoiceBalance();
        if (voiceBalance == null) voiceBalance = account.getVoiceBalance();
        if (voiceBalance == null) return context.getString(R.string.invalid);
        Date date = voiceBalance.getValidDate();
        return date != null ?
                context.getString(R.string.package_validity, date) :
                context.getString(R.string.invalid);
    }

    public boolean isPackageMinutesActive() {
        Balance voiceBalance = account.getComplexBundleVoiceBalance();
        if (voiceBalance == null) voiceBalance = account.getVoiceBalance();
        return voiceBalance != null && voiceBalance.getValidDate() != null;
    }

    public String getPackageSms() {
        Balance smsBalance = account.getSmsBalance();
        return smsBalance.isUnlimited() ?
                context.getString(R.string.unlimited) :
                String.format("%.0f", smsBalance.getQuantity());
    }

    public String getPackageData() {
        Balance dataBalance = account.getDataBalance();
        return dataBalance.isUnlimited() ?
                context.getString(R.string.unlimited) :
                String.format("%.0f MB", dataBalance.getQuantity() / 1000);
    }

    public String getPackageDataValidity() {
        Balance dataBalance = account.getDataBalance();
        Date date = dataBalance.getValidDate();
        return date != null ?
                context.getString(R.string.package_validity, date) :
                context.getString(R.string.invalid);
    }

    public boolean isPackageDataActive() {
        Balance dataBalance = account.getDataBalance();
        return dataBalance != null && dataBalance.getValidDate() != null;
    }

    public String getComplexBundleValidity() {
        Date date = account.getComplexBundleValidDate();
        return context.getString(R.string.package_validity, date);
    }
}
