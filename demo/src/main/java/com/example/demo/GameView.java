package com.example.demo;

import java.util.List;

public record GameView(
        String pageTitle,
        String sectionLabel,
        String heading,
        List<String> lines,
        List<DecisionOption> options,
        boolean showStats,
        boolean showSummary,
        GameState state
) {
}
