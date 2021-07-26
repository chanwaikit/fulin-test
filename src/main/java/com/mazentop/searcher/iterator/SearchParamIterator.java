package com.mazentop.searcher.iterator;

import com.mazentop.searcher.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: Wangzy
 * @Date: 2019/7/1 10:18
 * @Description:
 */
@Slf4j
public class SearchParamIterator implements InputIterator{

    private Iterator<SearchParam> searchParamIterator;
    private SearchParam currentProduct;

    public SearchParamIterator (Iterator<SearchParam> searchParamIterator) {
        this.searchParamIterator = searchParamIterator;
    }

    /**
     * 设置是否启用Contexts域
     * @return
     */
    public boolean hasContexts() {
        return true;
    }

    @Override
    public long weight() {
        return 0;
    }

    @Override
    public boolean hasPayloads() {
        return true;
    }

    @Override
    public Set<BytesRef> contexts() {
        return null;
    }

    @Override
    public BytesRef payload() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(currentProduct.getTimes());
            out.close();
            return new BytesRef(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Well that's unfortunate.");
        }
    }



    @Override
    public BytesRef next() throws IOException {
        if (searchParamIterator.hasNext()) {
            currentProduct = searchParamIterator.next();
            BytesRef bytesRef = new BytesRef();
            try {
                //返回当前Project的name值，把product类的name属性值作为key
                return new BytesRef(currentProduct.getKeywords().getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Couldn't convert to UTF-8",e);
            }
        }else {
            return null;
        }
    }


}
