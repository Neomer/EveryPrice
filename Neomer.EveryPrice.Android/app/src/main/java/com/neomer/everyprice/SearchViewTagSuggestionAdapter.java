package com.neomer.everyprice;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.neomer.everyprice.api.models.TagViewModel;

import java.util.List;

public class SearchViewTagSuggestionAdapter  extends SimpleCursorAdapter {

    private List<TagViewModel> tagViewModelList;

    public SearchViewTagSuggestionAdapter(Context context, int flags) {
        super(context,
                R.layout.mainmenu_search_item,
                null,
                new String[] { "text" },
                new int[] { R.id.mainmenu_searchitem_txtTag },
                flags);
    }



    public synchronized void applySuggestions(List<TagViewModel> tagViewModelList) {
        if (tagViewModelList == null) {
            return;
        }
        this.tagViewModelList = tagViewModelList;

        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "Uid", "text" });
        int idx = 0;
        for (TagViewModel tag : tagViewModelList) {
            c.addRow(new Object[] { idx++, tag.getUid(), tag.getValue() });
        }
        changeCursor(c);
    }

    public List<TagViewModel> getTags() {
        return tagViewModelList;
    }
}
