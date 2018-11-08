package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;
import net.greenmanov.iqdb.parsers.TagType;

import java.util.Objects;

/**
 * Class TagDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class TagDTO {
    private Long id;
    private String name;
    private TagType type;

    public TagDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDTO)) return false;
        TagDTO tagDTO = (TagDTO) o;
        return Objects.equals(getName(), tagDTO.getName()) &&
                getType() == tagDTO.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("type", type)
                .toString();
    }
}
