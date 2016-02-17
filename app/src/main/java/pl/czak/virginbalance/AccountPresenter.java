package pl.czak.virginbalance;

import android.content.Context;

import java.util.Calendar;
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
        return R.layout.widget_bundle;
    }

    public String getBalanceAmount() {
        // TODO: Get the value from account
        double balance = 12.34;
        return String.format("%.02f zł", balance);
    }

    public String getAccountTariff() {
        // TODO: Get tariff name from account
        String tariffName = "#MojaTaryfa";
        return context.getString(R.string.account_tariff, tariffName);
    }

    public String getAccountValidity() {
        // TODO: Get date from account
        Calendar date = new GregorianCalendar(2017, 0, 22);
        // TODO: SimpleDateFormat preferred to "%tF" format
        return context.getString(R.string.account_validity, date);
    }

    public String getPackageName() {
        String packageName = "#megapakiet";
        return context.getString(R.string.package_name, packageName);
    }

    public String getPackageMinutes() {
        // TODO: Get seconds quantity and unlimited from account
        int quantity = 0; // NOTE: Seconds
        boolean unlimited = false;
        return unlimited ?
                context.getString(R.string.unlimited) :
                Integer.toString(quantity / 60);
    }

    public String getPackageMinutesValidity() {
        // TODO: Get date & active from account
        Calendar date = null;
        boolean active = false;
        return active ?
                context.getString(R.string.package_validity, date) :
                context.getString(R.string.invalid);
    }

    public String getPackageSms() {
        // TODO: Get sms quantity and unlimited from account
        int quantity = 49992;
        boolean unlimited = true;
        return unlimited ?
                context.getString(R.string.unlimited) :
                Integer.toString(quantity);
    }

    public String getPackageSmsValidity() {
        // TODO: Get date & active from account
        Calendar date = new GregorianCalendar(2016, 1, 14);
        boolean active = true;
        return active ?
                context.getString(R.string.package_validity, date) :
                context.getString(R.string.invalid);
    }

    public String getPackageData() {
        // TODO: Get KB quantity & unlimited from account
        int quantity = 1491000; // NOTE: KB
        boolean unlimited = false;
        return unlimited ?
                context.getString(R.string.unlimited) :
                String.format("%d MB", quantity / 1000);
    }

    public String getComplexBundleValidity() {
        // TODO: Get date from account
        Calendar date = new GregorianCalendar(2016, 2, 12);
        // TODO: SimpleDateFormat preferred to "%tF" format
        return context.getString(R.string.package_validity, date);
    }
}
