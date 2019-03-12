package com.endava.AnimeExplorer.Model.ReportGenerator;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

public class HeaderWriter implements FlatFileHeaderCallback {


    private final String header;

    HeaderWriter(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}
