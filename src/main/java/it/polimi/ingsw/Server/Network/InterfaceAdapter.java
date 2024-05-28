package it.polimi.ingsw.Server.Network;

import com.google.gson.*;
import it.polimi.ingsw.Server.Model.Cards.GoldCard;
import it.polimi.ingsw.Server.Model.Cards.Objectives.*;
import it.polimi.ingsw.Server.Model.Cards.ResourceCard;
import it.polimi.ingsw.Server.Model.Cards.StartingCard;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public final class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    /**
     * A map of classes from their simple name to their full class name
     */
    private static final Map<String, Class<?>> nameToClassMap = new HashMap<>();
    static {
        nameToClassMap.put(CornerCardObjective.class.getSimpleName(),CornerCardObjective.class);
        nameToClassMap.put(DiagonalPatternObjective.class.getSimpleName(),DiagonalPatternObjective.class);
        nameToClassMap.put(MixSymbolObjective.class.getSimpleName(),MixSymbolObjective.class);
        nameToClassMap.put(PointsCardObjective.class.getSimpleName(),PointsCardObjective.class);
        nameToClassMap.put(SymbolObjective.class.getSimpleName(),SymbolObjective.class);
        nameToClassMap.put(VerticalPatternObjective.class.getSimpleName(),VerticalPatternObjective.class);
        nameToClassMap.put(StartingCard.class.getSimpleName(), StartingCard.class);
        nameToClassMap.put(ResourceCard.class.getSimpleName(), ResourceCard.class);
        nameToClassMap.put(GoldCard.class.getSimpleName(), GoldCard.class);
    }

    /**
     * Serializes object declared as interfaces or abstract classes. It divides the class into type
     * (the name of the class) and into data (the attributes of the class)
     * @param object the object to serialize
     * @param interfaceType the interface declared type
     * @param context the serialization context
     * @return a serialization of the object
     */
    public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getSimpleName());
        nameToClassMap.put(object.getClass().getSimpleName(), object.getClass());
        wrapper.add("data", context.serialize(object));
        return wrapper;
    }

    /**
     * Deserializes object declared as interface or abstract class.
     * @param elem the JsonElement to deserialize
     * @param interfaceType the interface type declared
     * @param context the serialization context
     * @return a object of the type
     * @throws JsonParseException if the type doesn't exist
     */
    public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) elem;
        final JsonElement typeName = get(wrapper, "type");
        final JsonElement data = get(wrapper, "data");
        final Type actualType = typeForName(typeName);
        return context.deserialize(data, actualType);
    }

    /**
     * Return the full name of a type given his simplename
     * @param typeElem
     * @return
     */
    private Type typeForName(final JsonElement typeElem) {
        return nameToClassMap.get(typeElem.getAsString());
    }

    /**
     * gets a field from a wrapper if it's serialized as interface
     * @param wrapper
     * @param memberName
     * @return the field
     */
    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null) throw new JsonParseException(memberName + "' not found");
        return elem;
    }
}
