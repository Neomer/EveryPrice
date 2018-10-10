package com.neomer.everyprice.api.models;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TagFastSearchViewModel {

    private String Suggestion;
    private List<TagViewModel> Tags;

    public TagFastSearchViewModel(String suggestion) {
        Suggestion = suggestion;
        Tags = new ArrayList<>();
    }

    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }

    @Nullable
    public List<TagViewModel> getTags() {
        return Tags;
    }
}
