package com.lmnml.group.common.model;

import java.util.Random;

/**
 * Created by daitian on 2018/5/8.
 */
public class Icon {
    static String[] icons={
            "5e38bb43e8154b52a2f99ba9ae7c7e15",
            "e1ed03ce78db4c4b902feb47871056d4",
            "9b6e19811aef476c92b39b17b41b7de5",
            "c72bf45c675541bb8c2c7c7d8f22e713",
            "13016d87c18649ffb0e10d9bbcf86013",
            "55126b03ad7f49fbb71d42f080485c59",
            "453dce9c58674370a34f8e6f82b5b9ed",
            "11098ff4095c40718b2a4b247feffece",
            "720085f7fa144ae4bd8632737b86cd3e",
            "45524c8c4a2543e1a8eab4ee0904b836",
            "85bb25a2f01c47369279d9f2f202e9c8",
            "4668dd9a22334ccba0a9f9734f34fc07"
    };
    public static final String getIcon(){
        return icons[new Random().nextInt(12)];
    }
}
