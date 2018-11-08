package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;
import net.greenmanov.anime.rurybooru.api.enums.Order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Class GetImagesDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class GetImagesDTO {
    private List<Long> tags;
    private Long dir;

    private Order order;

    @Min(1)
    @NotNull
    private Integer perPage;

    @Min(1)
    @NotNull
    private Integer page;

    public GetImagesDTO() {
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

    public Long getDir() {
        return dir;
    }

    public void setDir(Long dir) {
        this.dir = dir;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetImagesDTO)) return false;
        GetImagesDTO that = (GetImagesDTO) o;
        return Objects.equals(getTags(), that.getTags()) &&
                Objects.equals(dir, that.dir) &&
                getOrder() == that.getOrder() &&
                Objects.equals(getPerPage(), that.getPerPage()) &&
                Objects.equals(getPage(), that.getPage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTags(), dir, getOrder(), getPerPage(), getPage());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tags", tags)
                .add("dir", dir)
                .add("order", order)
                .add("perPage", perPage)
                .add("page", page)
                .toString();
    }
}
