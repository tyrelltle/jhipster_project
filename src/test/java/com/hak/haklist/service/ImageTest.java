package com.hak.haklist.service;

import com.hak.haklist.web.rest.util.ImageUtil;
import org.junit.Test;


public class ImageTest {



    @Test
    public void assertImageContentTypeRegex() {

        assert(ImageUtil.validImageType("image/jpg"));
        assert(ImageUtil.validImageType("image/png"));
        assert(ImageUtil.validImageType("image/gif"));
        assert(ImageUtil.validImageType("image/bmp"));
        assert(!ImageUtil.validImageType("helloworld"));

    }


}
