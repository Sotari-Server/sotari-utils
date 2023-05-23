package fr.knsrhuseyin.utils.json_reader;

import java.io.IOException;
import java.io.Writer;

public interface JSONStreamAware {
    void writeJSONString(Writer out) throws IOException;
}