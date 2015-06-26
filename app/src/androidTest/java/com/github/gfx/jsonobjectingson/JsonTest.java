package com.github.gfx.jsonobjectingson;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonTest {

    @Test
    public void testJsonObjectSerialization() throws Exception {
        JsonObject json = new JsonObject();
        json.addProperty("nan", Double.NaN);
        json.addProperty("pinf", Double.POSITIVE_INFINITY);
        json.addProperty("ninf", Double.POSITIVE_INFINITY);

        assertThat(json.toString(), is("{\"nan\":NaN,\"pinf\":Infinity,\"ninf\":Infinity}"));
    }

    @Test(expected = JSONException.class)
    public void testJSONObjectSerializationOfNaN() throws Exception {
        JSONObject json = new JSONObject();
        json.put("nan", Double.NaN);
    }

    @Test(expected = JSONException.class)
    public void testJSONObjectSerializationOfPositiveInfinity() throws Exception {
        JSONObject json = new JSONObject();
        json.put("pinf", Double.POSITIVE_INFINITY);
    }

    @Test(expected = JSONException.class)
    public void testJSONObjectSerializationOfNegativeInfinity() throws Exception {
        JSONObject json = new JSONObject();
        json.put("ninf", Double.NEGATIVE_INFINITY);
    }
}