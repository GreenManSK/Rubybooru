package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Class TagInfoDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class TagInfoDTO extends TagDTO {
    private Long count;

    public TagInfoDTO() {
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagInfoDTO)) return false;
        if (!super.equals(o)) return false;
        TagInfoDTO that = (TagInfoDTO) o;
        return Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCount());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("count", count)
                .add("id", getId())
                .add("name", getName())
                .add("type", getType())
                .toString();
    }
}
