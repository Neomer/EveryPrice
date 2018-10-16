package com.neomer.everyprice.api.commands;

import android.support.annotation.NonNull;

import com.neomer.everyprice.api.IWebApiCallback;
import com.neomer.everyprice.api.WebApiFacade;
import com.neomer.everyprice.api.models.TagFastSearchViewModel;

import java.util.List;

import retrofit2.Call;

public class TagsSuggestionsCommand extends AbstractWebApiCommand<TagFastSearchViewModel> {

    private String tagPart;

    public TagsSuggestionsCommand(@NonNull IWebApiCallback<TagFastSearchViewModel> callback) throws NullPointerException {
        super(callback);
    }

    @Override
    protected boolean beforeExecute() {
        if (getTagPart() == null || getTagPart().length() < 3) {
            return false;
        }
        return true;
    }

    @Override
    protected Call<TagFastSearchViewModel> getCall() {
        return getSecurityApi().FindTags(
                WebApiFacade.getInstance().getToken().getToken(),
                getTagPart()
        );
    }

    public String getTagPart() {
        return tagPart;
    }

    public void setTagPart(String tagPart) {
        this.tagPart = tagPart;
    }
}
