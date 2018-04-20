package com.johnlpage.pocdriver;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRecordTest {

    private final int binsize = 128;
    private final int numberSize = 1024;

    @Test
    public void testFlat() {
        int nFields = 10;
        int depth = 0;
        int[] array = new int[2];
        TestRecord testRecord = new BasicTestRecord(nFields, depth, 24,
                123, depth, numberSize,
                array, binsize);
        System.out.println(testRecord.getDocument().toJson());
        Set<String> fields = testRecord.getDocument().keySet();
        System.out.println(fields);
        // fields + _id + bin
        assertEquals(nFields + 2, fields.size());
    }

    @Test
    public void testDepth1() {
        int depth = 1;
        int nFields = 16;
        int[] array = new int[2];
        TestRecord testRecord = new BasicTestRecord(nFields, depth, 24,
                123, 0, numberSize,
                array, binsize);
        JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);
        System.out.println(testRecord.getDocument().toJson(writerSettings));
        assertTrue(testRecord.getDocument().containsKey("node2"));
        Document node2 = (Document) testRecord.getDocument().get("node2");
        assertTrue(node2.containsKey("fld10"));
        // test field lists
        Collection<String> fields = (Collection<String>) testRecord.listFields();
        System.out.println(fields);
        assertEquals(nFields, fields.size());
        assertTrue(fields.contains("node2.fld10"));
    }

    /**
     * Create documents with 2 levels of sub-document
     */
    @Test
    public void testDepth2() {
        int depth = 2;
        int nFields = 10;
        int[] array = new int[2];
        TestRecord testRecord = new BasicTestRecord(nFields, depth, 24,
                123, 0, numberSize,
                array, binsize);
        JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);
        System.out.println(testRecord.getDocument().toJson(writerSettings));
        assertTrue(testRecord.getDocument().containsKey("node1"));
        Document node1 = (Document) testRecord.getDocument().get("node1");
        assertTrue(node1.containsKey("node0"));
        Document node1_0 = (Document) node1.get("node0");
        assertTrue(node1_0.containsKey("fld6"));
        // test field lists
        Collection<String> fields = testRecord.listFields();
        System.out.println(fields);
        assertEquals(nFields, fields.size());
        assertTrue(fields.contains("node1.node0.fld6"));
    }

}
