package pl.czak.virginbalance;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BalanceTest {
    @Mock private JSONObject json;

    @Test
    public void testConstructorWithValidJsonAssignsCorrectProperties() {
        when(json.optDouble("quantity")).thenReturn(175.3);
        when(json.optString("unit")).thenReturn("KB");
        when(json.optBoolean("unlimited")).thenReturn(false);

        Balance balance = new Balance(json);

        assertEquals(175.3, balance.getQuantity(), 0.01);
        assertEquals("KB", balance.getUnit());
        assertFalse(balance.isUnlimited());
    }
}
