package com.redhat.lightblue.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redhat.lightblue.util.Error;
import com.redhat.lightblue.util.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by akoserwa on 3/30/17.
 */
public class ArrayContainsFieldExpression extends ArrayComparisonExpression {

    private static final long serialVersionUID = 1L;
    private final Path field;
    private final ContainsOperator op;
    private final List<Value> rfield;

    /**
     * Ctor with the given values
     */
    public ArrayContainsFieldExpression(Path field,
                                   ContainsOperator op,
                                   List<Value> rfield) {
        this.field = field;
        this.op = op;
        this.rfield = rfield;
    }

    /**
     * The array field. If this is included in a nested query, relative to the
     * context
     */
    public Path getArray() {
        return this.field;
    }

    /**
     * Contains operator
     */
    public ContainsOperator getOp() {
        return this.op;
    }

    /**
     * The rfield
     */
    public List<Value> getRfield() {
        return rfield;
    }

    /**
     * Returns a json representation of the query
     */

    @Override
    public JsonNode toJson() {
        ArrayNode arr = getFactory().arrayNode();
        for (Value x : rfield) {
            arr.add(x.toJson());
        }
        return getFactory().objectNode().
                put("field", field.toString()).
                put("contains", op.toString()).
                set("rfield", arr);

    }


    /**
     * Parses an ArrayContainsExpression from a JSON object node.
     */
    public static ArrayContainsFieldExpression fromJson(ObjectNode node) {
        JsonNode x = node.get("field");
        if (x != null) {
            Path field = new Path(x.asText());
            x = node.get("contains");
            if (x != null) {
                ContainsOperator op = ContainsOperator.fromString(x.asText());
                if (op != null) {
                    x = node.get("rfield");
                    if (x instanceof ArrayNode) {
                        ArrayList<Value> rfield = new ArrayList<>(((ArrayNode) x).size());
                        for (Iterator<JsonNode> itr = ((ArrayNode) x).elements();
                             itr.hasNext();) {
                            rfield.add(Value.fromJson(itr.next()));
                        }
                        return new ArrayContainsFieldExpression(field, op, rfield);
                    }
                }
            }
        }
        throw Error.get(QueryConstants.ERR_INVALID_ARRAY_COMPARISON_EXPRESSION, node.toString());
    }
}
