package com.astromc.stfu.configuration;

import java.util.Arrays;
import java.util.List;

public class Conf {

    private List<String> blacklist = Arrays.asList(
            "fake players",
            "bots",
            "bot",
            "fake",
            "trash server",
            "spoof",
            "no latency",
            "no ping"
    );

    public List<String> getBlacklist() {
        return blacklist;
    }

}
