package com.codename1.fingerprint.impl;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.util.FailureCallback;
import com.codename1.util.SuccessCallback;

/**
 *
 * @deprecated This is an internal implementation detail of the fingerprint scanner
 */
public class InternalCallback {
    private static SuccessCallback<Object> onSuccess;
    private static FailureCallback<Object> onFail;
    private static Dialog d;
    public static void init (SuccessCallback<Object> o1, FailureCallback<Object> o2) {
        onSuccess = o1;
        onFail = o2;

        // Android doesn't include a UI for fingerprints
        if(Display.getInstance().getPlatformName().equals("and")) {
            d = new Dialog(new BorderLayout());
            Label icon = new Label("", "DialogBody");
            FontImage.setMaterialIcon(icon, FontImage.MATERIAL_FINGERPRINT, 7);
            d.add(BorderLayout.CENTER, icon);
            d.showPacked(BorderLayout.CENTER, false);
            d.setDisposeWhenPointerOutOfBounds(true);
        }
    }
    
    public static void scanSuccess() {
        if(onSuccess != null) {
            Display.getInstance().callSerially(() -> {
                if(d != null) {
                    d.dispose();
                }
                onSuccess.onSucess(null);
            });
        }
    }

    public static void scanFail() {
        if(onFail != null) {
            Display.getInstance().callSerially(() -> {
                if(d != null) {
                    d.dispose();
                }
                onFail.onError(null, null, 0, null);
            });
        }
    }
}
