package de.tarent.nca.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JsonConverter {

    ObjectMapper mapper = new ObjectMapper();

    public JsonConverter() {
        mapper.registerModule(new JsonOrgModule());
    }

    public JSONObject merge(Object ... objects) {
        JSONObject result = new JSONObject();
        for (Object o: objects) {
            JSONObject jsonObject = mapper.convertValue(o, JSONObject.class);
            result = mergeJSONObjects(result, jsonObject);
        }
        return result;
    }

    public Object[] split(JSONObject o, Class ... classes) {
        Object[] result = new Object[classes.length];
        for (int i=0; i < classes.length; i++) {
            result[i] = mapper.convertValue(o, classes[i]);
        }
        return result;
    }

    public String stringOf(JSONObject o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject jsonObjectOf(String s) {
        try {
            return mapper.readValue(s, JSONObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * taken from {https://crunchify.com/how-to-merge-concat-multiple-jsonobjects-in-java-best-way-to-combine-two-jsonobjects/
     * and modified a little
     */
    private static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
        JSONObject mergedJSON;
        try {
            mergedJSON = new JSONObject(json1, emptyArrayIfNull(json1));
            for (String crunchifyKey : emptyArrayIfNull(json2)) {
                mergedJSON.put(crunchifyKey, json2.get(crunchifyKey));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return mergedJSON;
    }

    private static String[] emptyArrayIfNull(JSONObject json1) {
        String[] names = JSONObject.getNames(json1);
        return (names == null) ? new String[0] : names;
    }
}
