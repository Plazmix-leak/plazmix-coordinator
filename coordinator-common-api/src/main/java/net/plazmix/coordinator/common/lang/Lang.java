package net.plazmix.coordinator.common.lang;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Lang {

    RUSSIAN("Русский", "/ru"),

    ENGLISH("English", "/en"),

    UKRAINIAN("Украiнскi", "/uk"),

    GERMAN("Deutsch", "/de"),

    ITALIAN("...", "/ru"),

    SPANISH("...", "/ru"),
    ;

    private final String name;
    private final String dirpath;
}
