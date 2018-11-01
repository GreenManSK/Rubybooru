package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Class TagInfoDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class TagInfoDTO {
    private TagDTO tag;
    private Long count;

    public TagInfoDTO() {
    }

    public TagDTO getTag() {
        return tag;
    }

    public void setTag(TagDTO tag) {
        this.tag = tag;
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
        TagInfoDTO that = (TagInfoDTO) o;
        return Objects.equals(getTag(), that.getTag()) &&
                Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag(), getCount());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("count", count)
                .toString();
    }
}
