package com.mazentop;

import com.mazentop.entity.ProComment;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.searcher.ISearcher;
import com.mztframework.StarterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarterApplication.class)
public class ProProductMasterTest {

    @Autowired
    ISearcher searcher;

    @Test
    public void Test() {
        //数据源
        List<ProComment> list = ProComment.me().setFkProductId("4210803391745491458").find();
        OptionalDouble average = list.stream().mapToInt(ProComment::getRangeNum).average();
        BigDecimal decimal=new BigDecimal(average.getAsDouble()).setScale(2,BigDecimal.ROUND_DOWN);

        System.out.println(decimal);

    }

    @Test
    public void Test1(){
        List<ProProductMaster> productMasters = ProProductMaster.me().setLimit(5).find();
        System.out.println("--------------排序前---------------------");
        for (ProProductMaster productMaster : productMasters) {
            System.out.println("排序前"+productMaster.getSales());
        }
        Comparator<ProProductMaster> comparator = Comparator.comparing(ProProductMaster::getSales);
        productMasters.sort(comparator);
        System.out.println("--------------排序后---------------------");
        for (ProProductMaster productMaster : productMasters) {
            System.out.println("排序后"+productMaster.getSales());
        }

    }
}
