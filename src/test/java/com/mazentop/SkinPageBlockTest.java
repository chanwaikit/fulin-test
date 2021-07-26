package com.mazentop;


import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mztframework.StarterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterApplication.class)
public class SkinPageBlockTest {

    @Autowired
    ProductSpecialService productSpecialService;

    @Test
    public void Test() {
        SkinPageBlock.me().includeSelectFields(SkinPageBlock.F_ID, SkinPageBlock.F_PID).setId("dsfdsfs").find();
    }

}
