package com.mazentop.searcher.index.snapshot;

import com.alibaba.fastjson.JSON;
import com.mazentop.searcher.Snapshot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.lucene.document.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 商品参数快照
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/5/12 04:43
 */
@Data
public class ProductParamSnapshot extends Snapshot {

    private static final long serialVersionUID = -8059585184282441756L;

    private Brand brand;

    private List<Type> types;

    private List<Spec> specs;

    private List<Color> colors;

    public Brand getBrand() {
        return Objects.isNull(brand) ? new Brand() : brand;
    }

    public List<Type> getTypes() {
        return Objects.isNull(types) ? new ArrayList<>() : types;
    }

    public List<Spec> getSpecs() {
        return Objects.isNull(specs) ? new ArrayList<>() : specs;
    }

    public List<Color> getColors() {
        return Objects.isNull(colors) ? new ArrayList<>() : colors;
    }

    public void warp(Document document) {
//        setBrand(JSON.parseObject(document.get(ProductSnapshot.F_BRAND), Brand.class));
        setTypes(JSON.parseArray(document.get(ProductSnapshot.F_TAXONOMY_VALUE), Type.class));
//        setSpecs(JSON.parseArray(document.get(ProductSnapshot.F_SPEC_VALUE), Spec.class));
//        setColors(JSON.parseArray(document.get(ProductSnapshot.F_COLOR_VALUE), Color.class));
    }

    @Data
    @EqualsAndHashCode(exclude = { "name", "thumbnail", "count" })
    public static class Brand implements Serializable {

        private String id;

        private String name;

        private String thumbnail;

        private int count;
    }

    @Data
    public static class Color implements Serializable {
        private String id;

        private String name;

        private String value;
    }

    @Data
    public static class Spec implements Serializable {

        private String name;

        private List<String> attrs;

        public List<String> getAttrs() {
            return Objects.isNull(attrs) ? new ArrayList<>() : attrs;
        }
    }

    @Data
    @EqualsAndHashCode(exclude = {"name"})
    public static class Type implements Serializable {

        private String id;

        private String name;


    }
}
