package pl.czak.virginbalance;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountTest {
    private JSONObject json;

    @Before
    public void setUp() throws JSONException {
        json = new JSONObject("{\"type\":\"SelfCareMsisdnDetailsDTO\",\"msisdn\":\"48123456789\",\"name\":null,\"customerBalancesDto\":{\"msisdnState\":\"active\",\"generalBalance\":{\"quantity\":7.30,\"unit\":\"PLN\",\"validDate\":\"2017-01-30T22:59:59.000+0000\",\"unlimited\":false},\"valuePackBalance\":{\"quantity\":0.00,\"unit\":\"PLN\",\"validDate\":\"2016-09-27T21:59:59.000+0000\",\"unlimited\":false},\"voiceBalance\":null,\"complexBundleVoiceBalance\":{\"quantity\":3966,\"unit\":\"SECONDS\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":false},\"voiceOnNetBalance\":{\"quantity\":2999898,\"unit\":\"SECONDS\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":true},\"dataBalance\":{\"quantity\":1447000,\"unit\":\"KB\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":false},\"smsBalance\":{\"quantity\":49992.0,\"unit\":\"UNIT\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":true}},\"lastOrders\":[{\"date\":\"2015-09-28T18:38:03.911+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":1815881,\"price\":10.00},{\"date\":\"2015-10-30T05:29:04.946+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":2074980,\"price\":19.00}],\"accountValidDate\":\"2017-01-30T22:59:59.000+0000\",\"complexBundleName\":\"#testopakiet\",\"complexBundleValidDate\":\"2016-03-01T22:59:59.000+0000\",\"newTariff\":true,\"tariffName\":\"#TestowaTaryfa\",\"migratedFromOldTariff\":false,\"migrationTargetTariff\":null,\"migrationPending\":false,\"bundleOperationsFlags\":false,\"mnp\":true,\"complexBundleState\":\"ACTIVATED\",\"bundleFund\":\"PAYPAL\",\"complexBundleActivationSubmitted\":false,\"virginClubAwardDto\":null,\"mgmParentMsisdn\":null,\"lastCalls\":null}");
    }

    @Test
    public void testConstructorWithValidJson() throws JSONException {
        Account account = new Account(json);

        assertNotNull(account.getGeneralBalance());
        assertNotNull(account.getVoiceBalance());
        assertNotNull(account.getSmsBalance());
        assertNotNull(account.getDataBalance());
    }

    @Test
    public void testGetTariffName() throws JSONException {
        Account account = new Account(json);

        assertEquals("#TestowaTaryfa", account.getTariffName());
    }
}
