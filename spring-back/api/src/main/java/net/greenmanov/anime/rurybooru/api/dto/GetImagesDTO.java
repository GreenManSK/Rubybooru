package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;
import net.greenmanov.anime.rurybooru.api.enums.Order;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

/**
 * Class GetImagesDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class GetImagesDTO {
    private List<TagDTO> tags;
    private DirDTO dir;

    private Order order;

    @Min(1)
    private Integer prePage;

    @Min(1)
    private Integer page;

    public GetImagesDTO() {
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
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
                Objects.equals(getPrePage(), that.getPrePage()) &&
                Objects.equals(getPage(), that.getPage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTags(), dir, getOrder(), getPrePage(), getPage());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tags", tags)
                .add("dir", dir)
                .add("order", order)
                .add("prePage", prePage)
                .add("page", page)
                .toString();
    }
}