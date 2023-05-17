package sk.stuba.fei.uim.vsa.pr2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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

    public void setContent(List<R> content) {
        this.content = content;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageImpl{" +
                "content=[" + content.stream().map(Object::toString).collect(Collectors.joining(", ")) + "], " +
                "pageable=" + page +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageImpl<?> page1 = (PageImpl<?>) o;
        return Objects.equals(content, page1.content) && Objects.equals(page, page1.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, page);
    }
}
