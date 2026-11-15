package org.telegram.ui;

import android.content.Context;
import android.view.View;
import org.telegram.messenger.AxionConfig;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;
import org.telegram.ui.Components.UniversalFragment;
import java.util.ArrayList;

public class AxionSettingsActivity extends UniversalFragment {

    private static final int ID_GHOST_MODE = 1;
    private static final int ID_HIDE_TYPING = 2;
    private static final int ID_HIDE_READ_VOICE = 3;
    private static final int ID_OFFLINE_MODE = 4;
    private static final int ID_ALLOW_SAVE_RESTRICTED = 5;
    private static final int ID_PREVENT_TTL = 6;
    private static final int ID_BLOCK_SPONSORED = 7;
    private static final int ID_SHOW_PEER_ID = 8;

    @Override
    protected boolean useSimpleActionBar() {
        return true;
    }

    @Override
    public View createView(Context context) {
        setHasOwnBackground(true);
        fragmentView = super.createView(context);
        listView.setSections();
        listView.setSectionsDrawBackground(true);
        return fragmentView;
    }

    @Override
    protected RecyclerListView getListViewForSimpleGlass() {
        return listView;
    }

    @Override
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    @Override
    protected CharSequence getTitle() {
        return "Настройки AxionGram";
    }

    @Override
    protected void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        AxionConfig.loadConfig();

        items.add(UItem.asHeader("Режим призрака"));
        items.add(UItem.asCheck(ID_GHOST_MODE, "Режим призрака (не читать сообщения)")
                .setChecked(AxionConfig.ghostMode));
        items.add(UItem.asCheck(ID_HIDE_TYPING, "Скрывать статус набора текста")
                .setChecked(AxionConfig.hideTyping));
        items.add(UItem.asCheck(ID_HIDE_READ_VOICE, "Скрывать прослушивание ГС и видео")
                .setChecked(AxionConfig.hideReadVoice));
        items.add(UItem.asCheck(ID_OFFLINE_MODE, "Режим невидимки (офлайн статус)")
                .setChecked(AxionConfig.offlineMode));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader("Защита и контент"));
        items.add(UItem.asCheck(ID_ALLOW_SAVE_RESTRICTED, "Разрешить скриншоты и сохранение запрещенного")
                .setChecked(AxionConfig.allowSaveRestricted));
        items.add(UItem.asCheck(ID_PREVENT_TTL, "Запрет самоуничтожения таймерных медиа (TTL)")
                .setChecked(AxionConfig.preventTTL));
        items.add(UItem.asCheck(ID_BLOCK_SPONSORED, "Блокировка рекламы (спонсорские посты)")
                .setChecked(AxionConfig.blockSponsored));
        items.add(UItem.asCheck(ID_SHOW_PEER_ID, "Отображать ID пользователя / чата")
                .setChecked(AxionConfig.showPeerId));
        items.add(UItem.asShadow(null));
    }

    @Override
    protected void onClick(UItem item, View view, int position, float x, float y) {
        if (item.id == ID_GHOST_MODE) {
            AxionConfig.setGhostMode(!AxionConfig.ghostMode);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_HIDE_TYPING) {
            AxionConfig.setHideTyping(!AxionConfig.hideTyping);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_HIDE_READ_VOICE) {
            AxionConfig.setHideReadVoice(!AxionConfig.hideReadVoice);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_OFFLINE_MODE) {
            AxionConfig.setOfflineMode(!AxionConfig.offlineMode);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_ALLOW_SAVE_RESTRICTED) {
            AxionConfig.setAllowSaveRestricted(!AxionConfig.allowSaveRestricted);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_PREVENT_TTL) {
            AxionConfig.setPreventTTL(!AxionConfig.preventTTL);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_BLOCK_SPONSORED) {
            AxionConfig.setBlockSponsored(!AxionConfig.blockSponsored);
            if (adapter != null) adapter.update(true);
        } else if (item.id == ID_SHOW_PEER_ID) {
            AxionConfig.setShowPeerId(!AxionConfig.showPeerId);
            if (adapter != null) adapter.update(true);
        }
    }
}
