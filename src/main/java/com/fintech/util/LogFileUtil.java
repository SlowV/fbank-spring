package com.fintech.util;

import com.fintech.domain.Account;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Component
public class LogFileUtil {
    public void logFile(Account account, String action) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale(Locale.getDefault())
                        .withZone(ZoneId.systemDefault() );
        try (FileWriter fw = new FileWriter(getClass().getClassLoader().getResource("log/log.txt").getPath(), true)) {
            String contents = String.format("HỌ TÊN: %s --- THAO TÁC: %s --- THỜI GIAN: %s", account.getAccountInformation().getFullName(), action, formatter.format(Instant.now()));
            fw.append("\n");
            fw.append(contents);
            fw.append("\n-----------------------------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
