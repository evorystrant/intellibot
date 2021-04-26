package com.millennialmedia.intellibot.psi;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RobotKeywordTable {

    private final Map<RobotElementType, Set<String>> syntaxByType = new HashMap<>();
    private final Map<RobotElementType, Set<RecommendationWord>> recommendationsByType = new HashMap<>();

    public void addSyntax(RobotElementType type, String keyword) {
        Set<String> keywords = this.syntaxByType.get(type);
        if (keywords == null) {
            if (type == RobotTokenTypes.GHERKIN) {
                // this allows syntax for WHEN vs When vs when
                keywords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            } else {
                keywords = new HashSet<>();
            }
            this.syntaxByType.put(type, keywords);
        }
        keywords.add(keyword);
    }

    public void addRecommendation(@NotNull RobotElementType type, @NotNull String keyword, @NotNull String lookup) {
        Set<RecommendationWord> keywords = this.recommendationsByType.computeIfAbsent(type, k -> new HashSet<>());
        keywords.add(new RecommendationWord(keyword, lookup));
    }

    @NotNull
    public Set<String> getSyntaxOfType(RobotElementType type) {
        Set<String> results = this.syntaxByType.get(type);
        return results == null ? Collections.emptySet() : results;
    }

    @NotNull
    public Set<RecommendationWord> getRecommendationsForType(RobotElementType type) {
        Set<RecommendationWord> results = this.recommendationsByType.get(type);
        return results == null ? Collections.emptySet() : results;
    }
}