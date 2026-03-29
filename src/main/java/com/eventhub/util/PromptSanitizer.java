package com.eventhub.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PromptSanitizer {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "(?<!\\w)(?:\\+?1[\\s.-]?)?(?:\\(?\\d{3}\\)?[\\s.-]?)\\d{3}[\\s.-]?\\d{4}(?!\\w)"
    );

    private static final Pattern SIN_PATTERN = Pattern.compile(
            "(?<!\\d)\\d{3}[\\s-]?\\d{3}[\\s-]?\\d{3}(?!\\d)"
    );

    public String sanitize(String input) {
        if (input == null || input.isBlank()) {
            return input;
        }

        String sanitized = input;
        sanitized = EMAIL_PATTERN.matcher(sanitized).replaceAll("[REDACTED_EMAIL]");
        sanitized = PHONE_PATTERN.matcher(sanitized).replaceAll("[REDACTED_PHONE]");
        sanitized = SIN_PATTERN.matcher(sanitized).replaceAll("[REDACTED_SIN]");

        return sanitized.trim();
    }

    public List<String> sanitizeList(List<String> values) {
        if (values == null) {
            return Collections.emptyList();
        }

        List<String> sanitizedValues = new ArrayList<>();
        for (String value : values) {
            sanitizedValues.add(sanitize(value));
        }

        return sanitizedValues;
    }

    public String sanitizeAndJoin(List<String> values) {
        List<String> sanitizedValues = sanitizeList(values);
        if (sanitizedValues.isEmpty()) {
            return "";
        }

        return String.join(", ", sanitizedValues);
    }
}