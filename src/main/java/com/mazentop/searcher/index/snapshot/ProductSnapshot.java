package com.mazentop.searcher.index.snapshot;

import com.mazentop.searcher.Snapshot;
import com.mztframework.commons.Utils;
import lombok.Data;
import org.apache.lucene.document.Document;


/**
 * @author zhaoqt
 * @title: ProductSnapshot
 * @description: 商品快照
 * @date 2019/5/923:34
 */
@Data
public class ProductSnapshot extends Snapshot {

    private static final long serialVersionUID = -3957393526468264947L;

    public static final String F_PRICE = "price";

    public static final String F_OLD_PRICE = "oldPrice";

    /**
     * 返现金额
     */
    public static final String F_CASH_BACK = "cashBack";

    public static final String F_TIMES = "times";

    /**
     * 标签
     */
    public static final String F_TAG = "tag";

    public static final String F_SALE_NUM = "salenum";

    /**
     * 国家
     */
    public static final String F_COUNTRY = "country";

    private Long price;

    private String viewPrice;

    /**
     * 原价
     */
    private String oldPrice;

    private String cashBack;

    private String times;

    private Integer sales;

    public void warpper(Document document) {
        setId(document.get(F_ID));
        setTitle(document.get(F_TITLE));
        setUrl(document.get(F_URL));
        setThumbnail(document.get(F_THUMBNAIL));
        setPrice(Long.valueOf(document.get(F_PRICE)));
        setViewPrice(Utils.transformF2Y(Long.valueOf(document.get(F_PRICE))).toPlainString());
        setOldPrice(Utils.transformF2Y(Long.valueOf(document.get(F_OLD_PRICE))).toPlainString());
        setCashBack(Utils.transformF2Y(Long.valueOf(document.get(F_CASH_BACK))).toPlainString());
        setTimes(document.get(F_TIMES));
        setCreated(document.get(F_TIMES));
        setSales(Integer.parseInt(document.get(F_SALE_NUM)));
    }

}
