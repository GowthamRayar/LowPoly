package com.uniquestudio.lowpolyharmony.slice;

import com.uniquestudio.lowpoly.LowPoly;
import com.uniquestudio.lowpolyharmony.ResourceTable;
import com.uniquestudio.lowpolyharmony.util.ResUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Image;
import ohos.media.image.PixelMap;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Image image = (Image) findComponentById(ResourceTable.Id_imgView);
        PixelMap input = ResUtil.getPixelMap(this, ResourceTable.Media_gg).get();
        PixelMap out = LowPoly.generate(input, 40);
        image.setPixelMap(out);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
