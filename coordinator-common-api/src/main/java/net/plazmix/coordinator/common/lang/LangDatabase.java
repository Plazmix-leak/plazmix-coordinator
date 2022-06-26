package net.plazmix.coordinator.common.lang;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public record LangDatabase(Lang lang, LinkedHashMap<String, Object> handle) {

    private static final char COLOR_CHAR = '\u00A7';

    public String getString(String path) {
        return handle.get(path).toString();
    }

    public List<String> getStringList(String path) {
        return (List<String>) handle.get(path);
    }

    public String getColouredString(String path) {
        return getString(path).replace(COLOR_CHAR, '&');
    }

    public List<String> getColouredStringList(String path) {
        List<String> stringList = new ArrayList<>(getStringList(path));
        stringList.replaceAll(s -> s.replace(COLOR_CHAR, '&'));

        return stringList;
    }

}
