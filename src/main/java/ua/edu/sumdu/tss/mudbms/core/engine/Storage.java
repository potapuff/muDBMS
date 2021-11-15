package ua.edu.sumdu.tss.mudbms.core.engine;

import ua.edu.sumdu.tss.mudbms.core.Record;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

public class Storage {

    public static final int INT_SIZE = 4;
    public static final int BOOL_SIZE = 1;

    private final String dataFile;

    public Storage(String dataFile) {
        this.dataFile = dataFile;
    }

    @lombok.SneakyThrows
    public String read(String key) {
        try (var file = takeStorage()) {
            if (!scroll(file, key.getBytes())) {
                //Record not found
                return null;
            }
            int recordLength = file.readInt();
            byte[] rawRecord = new byte[recordLength];
            file.read(rawRecord);
            Record rec = Record.fromBytes(rawRecord);
            return rec.getValue();
        }
    }

    @lombok.SneakyThrows
    public void write(String key, String value) {
        try (var file = takeStorage()) {
            byte[] rawRecord = new Record(key, value).getBytes();
            if (scroll(file, key.getBytes())) {
                int recordLength = file.readInt();
                if (recordLength >= rawRecord.length) {
                    //In-place editing
                    file.write(rawRecord);
                    for (int i = 0; i < recordLength - rawRecord.length; i++) {
                        //fill not-used part with 0
                        file.write(0);
                    }
                    return;
                } else {
                    //Mark Record as deleted
                    file.writeBoolean(false);
                    file.seek(file.length());
                }
            }
            file.writeInt(rawRecord.length);
            file.write(rawRecord);
        }
    }

    @lombok.SneakyThrows
    public boolean scroll(RandomAccessFile file, byte[] key) {
        long startPosition;
        int recordLength;
        int keyLength;
        boolean isValid;
        byte[] candidate = new byte[key.length];
        while (file.getFilePointer() < file.length()) {
            startPosition = file.getFilePointer();
            recordLength = file.readInt();
            isValid = file.readBoolean();
            keyLength = file.readInt();
            if (!isValid || keyLength != key.length) {
                file.skipBytes(recordLength - INT_SIZE - BOOL_SIZE);
                continue;
            }
            file.read(candidate);
            if (Arrays.equals(candidate, key)) {
                file.seek(startPosition);
                return true;
            } else {
                file.skipBytes(recordLength - INT_SIZE - BOOL_SIZE - keyLength);
            }
        }
        return false;
    }

    @lombok.SneakyThrows
    private RandomAccessFile takeStorage() {
        Path fileName = getStoragePath();
        RandomAccessFile file;
        try {
            file = new RandomAccessFile(fileName.toFile(), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Problem with access to database", e);
        }
        return file;
    }

    public Path getStoragePath() {
        return Path.of(dataFile);
    }
}
