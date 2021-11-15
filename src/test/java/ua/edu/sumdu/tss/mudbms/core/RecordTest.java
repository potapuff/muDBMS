package ua.edu.sumdu.tss.mudbms.core;

import org.junit.jupiter.api.Test;
import ua.edu.sumdu.tss.mudbms.test_utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordTest {

    @Test
    void serializeAndDeserializeTest() {
        for (int i = 0; i < 10; i++) {
            Record record = Utils.randomRecord();
            assertEquals(record, Record.fromBytes(record.getBytes()));
        }
    }


}