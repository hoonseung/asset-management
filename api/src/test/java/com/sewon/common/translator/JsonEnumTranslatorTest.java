package com.sewon.common.translator;

import com.sewon.asset.constant.AssetDivision;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonEnumTranslatorTest {


    @DisplayName("번역 테스트")
    @Test
    void translate_test() throws Exception {
        int parameter = 0;
        String nation = "cn";
        AssetDivision assetDivision = AssetDivision.fromValue(parameter);
        String languages = JsonEnumTranslator
            .translate("assetdivision", assetDivision.name(), nation);

        Assertions.assertNotNull(languages);
        System.out.println(languages);
    }
}