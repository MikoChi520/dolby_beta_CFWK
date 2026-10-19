package com.raincat.dolby_beta.view.proxy.configuration;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.raincat.dolby_beta.helper.SettingHelper;
import com.raincat.dolby_beta.view.BaseDialogInputItem;

/**
 * Cloudflare Worker base URL, for example https://music.example.com
 */
public class ProxyWorkerUrlView extends BaseDialogInputItem {
    public ProxyWorkerUrlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ProxyWorkerUrlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProxyWorkerUrlView(Context context) {
        super(context);
    }

    @Override
    public void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        title = SettingHelper.worker_url_title;
        editView.setSingleLine(true);
        editView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        editView.setHint(SettingHelper.worker_url_default);
        setData(SettingHelper.getInstance().getWorkerUrl(), "");

        defaultView.setOnClickListener(view -> {
            editView.setText(SettingHelper.worker_url_default);
            editView.setSelection(editView.getText().length());
        });

        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SettingHelper.getInstance().setWorkerUrl(editView.getText().toString());
            }
        });
    }
}
