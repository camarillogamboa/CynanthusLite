package edu.cynanthus.domain;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public final class Email extends EmailTemplate {

    private static final int TEXT_STATE = 0;
    private static final int TAG_STATE = 1;

    Email(EmailTemplate emailTemplate, Map<String, ?> values) {
        super(
            interpolateMap(emailTemplate.getHeaders(), values),
            interpolate(emailTemplate.getSubject(), values),
            interpolateList(emailTemplate.getTo(), values),
            interpolateList(emailTemplate.getCc(), values),
            interpolateList(emailTemplate.getBcc(), values),
            interpolate(emailTemplate.getBody(), values),
            interpolateList(emailTemplate.getAttachments(), values)
        );
    }

    private static String interpolate(String string, Map<String, ?> values) {

        int state = TEXT_STATE;

        StringBuilder textBuilder = new StringBuilder();
        StringBuilder tagBuilder = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            switch (state) {
                case TEXT_STATE:
                    if (c == '{') state = TAG_STATE;
                    else textBuilder.append(c);
                    break;
                case TAG_STATE:
                    if (c == '}') {
                        textBuilder.append(values.get(tagBuilder.toString()));
                        tagBuilder.setLength(0);
                        state = TEXT_STATE;
                    } else tagBuilder.append(c);
            }

        }

        return textBuilder.toString();
    }

    private static List<String> interpolateList(List<String> list, Map<String, ?> values) {
        if (list != null && !list.isEmpty())
            return list.stream().map(string -> interpolate(string, values)).collect(Collectors.toList());
        return null;
    }

    private static Map<String, String> interpolateMap(Map<String, String> map, Map<String, ?> values) {
        Map<String, String> resultMap = new TreeMap<>(String::compareTo);

        map.forEach((k, v) -> resultMap.put(k, interpolate(v, values)));

        return resultMap;
    }

}
