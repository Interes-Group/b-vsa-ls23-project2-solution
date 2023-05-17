package sk.stuba.fei.uim.vsa.pr2.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageImpl<R> implements Page<R> {

    private List<R> content;
    private Pageable page;

    public PageImpl() {
        this.content = new ArrayList<>();
    }

    public PageImpl(List<R> content, Pageable pageable) {
        this();
        this.content = content;
        this.page = pageable;
    }

    @Override
    public List<R> getContent() {
        return content;
    }

    @Override
    public Pageable getPage() {
        return page;
    }


    @Override
    public String toString() {
        return "PageImpl{" +
                "content=" + content.getClass().getName() +
                "pageable=" + page +
                '}';
    }
}
