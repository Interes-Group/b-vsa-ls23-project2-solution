package sk.stuba.fei.uim.vsa.pr2.model;

import lombok.Builder;

import java.util.Objects;

@Builder
public class PageableImpl implements Pageable {

    public static final Integer DEFAULT_PAGE_SIZE = 20;

    private Integer number;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;

    public PageableImpl() {
        this.number = 0;
        this.size = DEFAULT_PAGE_SIZE;
    }

    public PageableImpl(Integer number, Integer size) {
        this();
        this.number = number != null ? number : 0;
        this.size = size != null ? size : DEFAULT_PAGE_SIZE;
    }

    public PageableImpl(Integer number, Integer size, Long totalElements, Integer totalPages) {
        this();
        this.number = number != null ? number : 0;
        this.size = size != null ? size : DEFAULT_PAGE_SIZE;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    @Override
    public Pageable of(int page, int size) {
        return new PageableImpl(page, size);
    }

    @Override
    public Pageable first() {
        return new PageableImpl(0, this.size);
    }

    @Override
    public Pageable previous() {
        if (number == 0) return this.first();
        return new PageableImpl(number - 1, size);
    }

    @Override
    public Pageable next() {
        return new PageableImpl(number + 1, size);
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    @Override
    public Long getTotalElements() {
        return totalElements;
    }

    @Override
    public void setTotalElements(Long totalElements) {
        if (this.totalElements == null || this.totalElements == 0L)
            this.totalElements = totalElements;
    }

    @Override
    public Integer getTotalPages() {
        if ((totalPages == null || totalPages == 0) && this.totalElements != null) {
            totalPages = ((Double) Math.ceil(this.totalElements.doubleValue() / this.size.doubleValue())).intValue();
        }
        return totalPages;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageableImpl pageable = (PageableImpl) o;
        return Objects.equals(number, pageable.number) && Objects.equals(size, pageable.size) && Objects.equals(totalElements, pageable.totalElements) && Objects.equals(totalPages, pageable.totalPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, size, totalElements, totalPages);
    }

    @Override
    public String toString() {
        return "PageableImpl{" +
                "number=" + number +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
