package ua.edu.sumdu.tss.mudbms.core.storage_engine;

import ua.edu.sumdu.tss.mudbms.core.Record;

public interface DirectAccessStorage {

    Record byAddress(long address);

    int writeRecord(Record record);

    long getAddress(String key);

}
