package pl.czak.virginbalance;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {
    @Mock
    private JSONObject json;

    @Test
    public void testConstructorWithValidJson() throws JSONException {
        Account details = new Account(json);

        assertNotNull(details.getGeneralBalance());
        assertNotNull(details.getVoiceBalance());
        assertNotNull(details.getSmsBalance());
        assertNotNull(details.getDataBalance());
    }
}
