package com.redhat.lightblue.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redhat.lightblue.util.JsonUtils;
import com.redhat.lightblue.util.Path;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by akoserwa on 3/30/17.
 *  *
 * Query of the form
 * <pre>
 * array_contains_expression := { array: <field>,
 *                               contains: "$any" | "$all" | "$none",
 *                               <rfield>: value_list_array }
 * </pre>
 *
 */
public class ArrayContainsFieldExpressionTest {


    @Test
    public void testToJson() throws IOException {
        List<Value> l = new ArrayList<>();
        ArrayContainsFieldExpression instance = new ArrayContainsFieldExpression(Path.EMPTY, ContainsOperator._any, l);
        l.add(new Value(Path.EMPTY));
        JsonNode expResult = JsonUtils.json("{\"field\":\"\",\"contains\":\"$any\",\"rfield\":[\"\"]}");
        JsonNode result = instance.toJson();
        assertEquals(expResult, result);
    }


    @Test
    public void testFromJson() throws IOException {
        //List<Value> l = new ArrayList<>();
        // ArrayContainsExpression instance = new ArrayContainsExpression(Path.EMPTY, ContainsOperator._any, l);
        ObjectNode node = (ObjectNode) JsonUtils.json("{\"field\":\"\",\"contains\":\"$any\",\"rfield\":[\"\"]}");
        ArrayContainsFieldExpression result=  ArrayContainsFieldExpression.fromJson(node);
        ArrayComparisonExpression expResult = ArrayComparisonExpression.fromJson(node);
        assertEquals(expResult.toString(), result.toString());
    }

}
