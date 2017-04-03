package com.poovarasan.deepstream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.Objects;

class UtilJSONPath {
    private JsonElement coreElement;
    private Gson gson = new Gson();

    public UtilJSONPath(JsonElement e) {
        this.coreElement = e;
    }

    /**
     * Traverses through the json element tree for retrieving values
     *
     * @param element The element to traverse through
     * @param path    The path to find
     * @return The element indicated by the path
     */
    private static JsonElement getIterateThrough(JsonElement element, String path) {
        String[]    st        = path.split("\\.");
        JsonElement traverser = element;
        String      token     = null;

        for (int i = 0; i < st.length; i++) {
            token = st[i];

            try {
                if (isArray(token)) {
                    String prefix = getTokenPrefix(token);
                    int    index  = Integer.parseInt(getIndex(token));

                    traverser = traverser.getAsJsonObject().get(prefix).getAsJsonArray().get(index);
                } else if (traverser.isJsonObject()) {
                    traverser = traverser.getAsJsonObject().get(token);
                } else if (traverser.isJsonArray()) {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                return null;
            } catch (NullPointerException e) {
                return null;
            }
        }
        return traverser;
    }

    private static JsonElement setIterateThrough(JsonElement element, String path, JsonElement value, boolean delete) {
        String[]    st        = path.split("\\.");
        JsonElement traverser = element;
        JsonElement parent    = null;
        String      token     = null;

        for (int i = 0; i < st.length; i++) {
            token = st[i];
            parent = traverser;

            if (isArray(token)) {
                String prefix = getTokenPrefix(token);
                token = getIndex(token);
                int        index        = Integer.parseInt(token);
                JsonObject parentObject = traverser.getAsJsonObject();

                if (parentObject.get(prefix) == null) {
                    parentObject.add(prefix, initialiseArray(index));
                }
                try {
                    parentObject.get(prefix).getAsJsonArray().get(index);
                } catch (IndexOutOfBoundsException e) {
                    extendArray(parentObject.get(prefix).getAsJsonArray(), index);
                }
                parent = parentObject.get(prefix);
                traverser = parentObject.get(prefix).getAsJsonArray().get(index);//traverser.getAsJsonObject().get( prefix );
            } else if (traverser.isJsonObject()) {
                JsonElement property = traverser.getAsJsonObject().get(token);
                if (property == null) {
                    traverser.getAsJsonObject().add(token, new JsonObject());
                }
                parent = traverser.getAsJsonObject();
                traverser = traverser.getAsJsonObject().get(token);

            } else if (traverser.isJsonArray()) {
                break;
            }
        }

        if (token != null && (value != null || delete)) {
            updateValue(value, parent, token, delete);
        }
        return traverser;
    }

    private static JsonArray initialiseArray(int size) {
        JsonArray array = new JsonArray();
        for (int j = 0; j < size; j++) {
            array.add(JsonNull.INSTANCE);
        }
        JsonElement temp = new JsonObject();
        array.add(temp);
        return array;
    }

    private static void extendArray(JsonArray array, int size) {
        for (int j = array.size(); j < size; j++) {
            array.add(JsonNull.INSTANCE);
        }
        JsonElement temp = new JsonObject();
        array.add(temp);
    }

    private static void updateValue(JsonElement value, JsonElement parent, String token, boolean delete) {
        if (parent.isJsonObject()) {
            JsonObject object = (JsonObject) parent;
            if (delete) {
                object.remove(token);
            } else {
                object.add(token, value);
            }
        } else if (parent.isJsonArray()) {
            JsonArray object = (JsonArray) parent;
            int       size   = object.size();
            int       index  = Integer.parseInt(token);

            if (delete) {
                object.remove(index);
            } else {
                for (int i = size; i <= index; i++) {
                    object.add(JsonNull.INSTANCE);
                }
                object.set(index, value);
            }
        }
    }

    private static JsonElement getArrayElement(JsonElement traverser,
                                               String token) {

        int index = Integer.valueOf(getIndex(token));
        try {
            return traverser.getAsJsonObject()
                    .get(getTokenPrefix(token)).getAsJsonArray()
                    .get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private static String getTokenPrefix(String token) {
        return token.substring(0, token.indexOf("["));
    }

    private static String getIndex(String token) {
        return token.substring(token.indexOf("[") + 1, token.indexOf("]")).trim();
    }

    private static boolean isArray(String token) {
        boolean isArray = (token.contains("[") && token.contains("]") && (token.indexOf("[") < token.indexOf("]")));
        try {
            Integer.parseInt(token.substring(token.indexOf("[") + 1, token.indexOf("]")).trim());
            return isArray;
        } catch (Exception e) {
            return false;
        }
    }

    public JsonElement get(String path) {
        if (Objects.equals(path, "") || path == null) {
            return this.coreElement;
        } else {
            return getIterateThrough(this.coreElement, path);
        }
    }

    public void set(String path, JsonElement value) {
        if (Objects.equals(path, "")) {
            throw new RuntimeException("Setting an entire object must be done via setValue( JsonElement value );");
        } else if (path == null) {
            this.coreElement = value;
        } else {
            setIterateThrough(this.coreElement, path, value, false);
        }
    }

    /**
     * Deletes the value specified in the path.
     *
     * Traverses through the tree to find the parent object and then
     * just removes the key
     *
     * @param path The path to delete
     */
    protected void delete(String path) {
        setIterateThrough(this.coreElement, path, null, true);
    }

    public JsonElement getCoreElement() {
        return coreElement;
    }

    public void setCoreElement(JsonElement coreElement) {
        this.coreElement = coreElement;
    }

    public class Array implements Iterable<UtilJSONPath> {
        private final JsonArray   root;
        private       JsonElement coreElement;

        public Array(JsonArray root) {
            this.root = root;
        }

        @Override
        public Iterator<UtilJSONPath> iterator() {
            return new Iterator<UtilJSONPath>() {
                final Iterator<JsonElement> it = root.iterator();

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public UtilJSONPath next() {
                    return new UtilJSONPath(it.next());
                }

                @Override
                public void remove() {
                    it.remove();
                }
            };
        }
    }
}