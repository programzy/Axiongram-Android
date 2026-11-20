package org.telegram.ui;

import android.content.Context;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AxionConfig;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
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
    private static final int ID_SMOOTH_TYPING = 9;
    private static final int ID_ANTI_DELETE = 10;
    private static final int ID_FORWARD_NO_QUOTE = 11;
    private static final int ID_CONFIRM_CALL = 12;
    private static final int ID_CUSTOM_USERNAME = 13;
    private static final int ID_LOCAL_PREMIUM = 14;
    private static final int ID_SHOW_DELETED_MARK = 15;
    private static final int ID_SAVE_EDIT_HISTORY = 16;
    private static final int ID_LOCAL_MESSAGE_EDIT = 17;

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

        items.add(UItem.asHeader("Приватность и статус"));
        items.add(UItem.asCheck(ID_GHOST_MODE, "Режим призрака")
                .setChecked(AxionConfig.ghostMode));
        items.add(UItem.asShadow("Не отправляет на сервер запросы о прочтении сообщений. Вы можете читать любые личные чаты и каналы, оставаясь незамеченным."));

        items.add(UItem.asCheck(ID_HIDE_TYPING, "Скрывать статус набора текста")
                .setChecked(AxionConfig.hideTyping));
        items.add(UItem.asShadow("Блокирует отправку статуса «печатает...» собеседнику при вводе текста в любых чатах."));

        items.add(UItem.asCheck(ID_HIDE_READ_VOICE, "Скрывать прослушивание ГС и видео")
                .setChecked(AxionConfig.hideReadVoice));
        items.add(UItem.asShadow("Голосовые сообщения и видеокружочки не помечаются прослушанными для собеседника."));

        items.add(UItem.asCheck(ID_OFFLINE_MODE, "Режим невидимки (офлайн статус)")
                .setChecked(AxionConfig.offlineMode));
        items.add(UItem.asShadow("Устанавливает статус «был в сети недавно», скрывая вашу онлайн-активность."));

        items.add(UItem.asHeader("Чаты и сообщения"));
        items.add(UItem.asCheck(ID_ANTI_DELETE, "Защита от удаления сообщений")
                .setChecked(AxionConfig.antiDelete));
        items.add(UItem.asShadow("Удаленные собеседником сообщения остаются в вашей переписке."));

        items.add(UItem.asCheck(ID_FORWARD_NO_QUOTE, "Пересылка без авторства (Clean Forward)")
                .setChecked(AxionConfig.forwardNoQuote));
        items.add(UItem.asShadow("Пересылает любые сообщения без плашки «Переслано от...»."));

        items.add(UItem.asCheck(ID_CONFIRM_CALL, "Подтверждение перед звонком")
                .setChecked(AxionConfig.confirmCall));
        items.add(UItem.asShadow("Показывает диалог подтверждения перед началом голосового или видеозвонка."));

        items.add(UItem.asCheck(ID_SHOW_DELETED_MARK, "Метка на удаленных сообщениях (🗑️)")
                .setChecked(AxionConfig.showDeletedMark));
        items.add(UItem.asShadow("Отображает значок корзины рядом со временем удаленных собеседником сообщений."));

        items.add(UItem.asCheck(ID_SAVE_EDIT_HISTORY, "История изменений сообщений")
                .setChecked(AxionConfig.saveEditHistory));
        items.add(UItem.asShadow("Сохраняет редакции сообщений и позволяет просматривать историю изменений в меню сообщения."));

        items.add(UItem.asCheck(ID_LOCAL_MESSAGE_EDIT, "Локальное редактирование любых сообщений")
                .setChecked(AxionConfig.localMessageEdit));
        items.add(UItem.asShadow("Добавляет пункт «Изменить (локально)» в контекстное меню для визуального изменения текста."));

        items.add(UItem.asHeader("Защита контента и интерфейс"));
        items.add(UItem.asCheck(ID_ALLOW_SAVE_RESTRICTED, "Разрешить скриншоты и сохранение")
                .setChecked(AxionConfig.allowSaveRestricted));
        items.add(UItem.asShadow("Снимает системный запрет на создание скриншотов (FLAG_SECURE) в секретных чатах и позволяет сохранять медиа из защищенных каналов."));

        items.add(UItem.asCheck(ID_PREVENT_TTL, "Запрет самоуничтожения медиа (TTL)")
                .setChecked(AxionConfig.preventTTL));
        items.add(UItem.asShadow("Медиа с таймером самоуничтожения не удаляются после истечения времени и доступны для повторного просмотра."));

        items.add(UItem.asCheck(ID_BLOCK_SPONSORED, "Блокировка рекламы")
                .setChecked(AxionConfig.blockSponsored));
        items.add(UItem.asShadow("Скрывает спонсорские рекламные посты в публичных Telegram-каналах."));

        items.add(UItem.asCheck(ID_SHOW_PEER_ID, "Отображать ID пользователя / чата")
                .setChecked(AxionConfig.showPeerId));
        items.add(UItem.asShadow("Показывает точный Telegram ID в профилях пользователей и чатов. Долгое зажатие копирует ID."));

        items.add(UItem.asHeader("Telegram Premium"));
        items.add(UItem.asCheck(ID_LOCAL_PREMIUM, "Локальный Telegram Premium")
                .setChecked(AxionConfig.localPremium));
        items.add(UItem.asShadow("Активирует премиум-статус клиента: звезда в профиле, кастомные эмодзи и реакции, расшифровка голосовых (Speech-to-Text), удвоенные лимиты чатов и папок, эксклюзивные иконки приложения."));

        items.add(UItem.asHeader("Кастомизация профиля и экран"));
        items.add(UItem.asCheck(ID_SMOOTH_TYPING, "Плавный набор текста и 90/120 FPS")
                .setChecked(AxionConfig.smoothTyping));
        items.add(UItem.asShadow("Включает принудительную повышенную частоту обновления экрана при наборе текста."));

        String unVal = AxionConfig.customUsernameEnabled ?
            (AxionConfig.customUsername.isEmpty() ? "Вкл" : ("@" + AxionConfig.customUsername)) : "Выкл";
        String unDesc = AxionConfig.customUsernameEnabled ? 
            ("Активно: @" + (AxionConfig.customUsername.isEmpty() ? "не задан" : AxionConfig.customUsername) + 
            (AxionConfig.extraUsernames.isEmpty() ? "" : (" + Fragment NFT: " + AxionConfig.extraUsernames))) : 
            "Нажмите для настройки визуального никнейма и коллекционных Fragment NFT юзернеймов.";
        items.add(UItem.asButton(ID_CUSTOM_USERNAME, "Визуальные и Fragment NFT юзернеймы", unVal));
        items.add(UItem.asShadow(unDesc));
    }

    @Override
    protected void onClick(UItem item, View view, int position, float x, float y) {
        if (item.id == ID_GHOST_MODE) {
            AxionConfig.setGhostMode(!AxionConfig.ghostMode);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_HIDE_TYPING) {
            AxionConfig.setHideTyping(!AxionConfig.hideTyping);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_HIDE_READ_VOICE) {
            AxionConfig.setHideReadVoice(!AxionConfig.hideReadVoice);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_OFFLINE_MODE) {
            AxionConfig.setOfflineMode(!AxionConfig.offlineMode);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_ALLOW_SAVE_RESTRICTED) {
            AxionConfig.setAllowSaveRestricted(!AxionConfig.allowSaveRestricted);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_PREVENT_TTL) {
            AxionConfig.setPreventTTL(!AxionConfig.preventTTL);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_BLOCK_SPONSORED) {
            AxionConfig.setBlockSponsored(!AxionConfig.blockSponsored);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_SHOW_PEER_ID) {
            AxionConfig.setShowPeerId(!AxionConfig.showPeerId);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_SMOOTH_TYPING) {
            AxionConfig.setSmoothTyping(!AxionConfig.smoothTyping);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_ANTI_DELETE) {
            AxionConfig.setAntiDelete(!AxionConfig.antiDelete);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_FORWARD_NO_QUOTE) {
            AxionConfig.setForwardNoQuote(!AxionConfig.forwardNoQuote);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_CONFIRM_CALL) {
            AxionConfig.setConfirmCall(!AxionConfig.confirmCall);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_LOCAL_PREMIUM) {
            AxionConfig.setLocalPremium(!AxionConfig.localPremium);
            getNotificationCenter().postNotificationName(org.telegram.messenger.NotificationCenter.currentUserPremiumStatusChanged);
            org.telegram.messenger.NotificationCenter.getGlobalInstance().postNotificationName(org.telegram.messenger.NotificationCenter.premiumStatusChangedGlobal);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_SHOW_DELETED_MARK) {
            AxionConfig.setShowDeletedMark(!AxionConfig.showDeletedMark);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_SAVE_EDIT_HISTORY) {
            AxionConfig.setSaveEditHistory(!AxionConfig.saveEditHistory);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_LOCAL_MESSAGE_EDIT) {
            AxionConfig.setLocalMessageEdit(!AxionConfig.localMessageEdit);
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        } else if (item.id == ID_CUSTOM_USERNAME) {
            showVisualUsernamesDialog();
        }
    }

    private void showVisualUsernamesDialog() {
        Context context = getContext();
        if (context == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, getResourceProvider());
        builder.setTitle("✨ Визуальные и Fragment NFT юзернеймы");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(context);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(12), AndroidUtilities.dp(24), AndroidUtilities.dp(8));

        android.widget.TextView info = new android.widget.TextView(context);
        info.setText("Задайте визуальный основной юзернейм и коллекционные Fragment NFT юзернеймы, отображаемые в вашем профиле.");
        info.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4, getResourceProvider()));
        info.setTextSize(14);
        layout.addView(info);

        final EditTextBoldCursor primaryInput = new EditTextBoldCursor(context);
        primaryInput.setHint("Основной юзернейм (например durov)");
        primaryInput.setText(AxionConfig.customUsername);
        layout.addView(primaryInput);

        final EditTextBoldCursor extraInput = new EditTextBoldCursor(context);
        extraInput.setHint("Коллекционные Fragment NFT (через запятую: nft, ton, owner)");
        extraInput.setText(AxionConfig.extraUsernames);
        layout.addView(extraInput);

        builder.setView(layout);
        builder.setPositiveButton("Применить", (dialog, which) -> {
            String primary = primaryInput.getText().toString().trim().replace("@", "");
            String extra = extraInput.getText().toString().trim();
            AxionConfig.setCustomUsername(primary);
            AxionConfig.setExtraUsernames(extra);
            AxionConfig.setCustomUsernameEnabled(!primary.isEmpty() || !extra.isEmpty());
            android.widget.Toast.makeText(context, "Визуальные юзернеймы сохранены!", android.widget.Toast.LENGTH_SHORT).show();
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        });
        builder.setNeutralButton("Отключить", (dialog, which) -> {
            AxionConfig.setCustomUsername("");
            AxionConfig.setExtraUsernames("");
            AxionConfig.setCustomUsernameEnabled(false);
            android.widget.Toast.makeText(context, "Визуальные юзернеймы отключены", android.widget.Toast.LENGTH_SHORT).show();
            if (listView != null && listView.adapter != null) listView.adapter.update(true);
        });
        builder.setNegativeButton("Отмена", null);
        showDialog(builder.create());
    }

    @Override
    protected boolean onLongClick(UItem item, View view, int position, float x, float y) {
        return false;
    }
}
