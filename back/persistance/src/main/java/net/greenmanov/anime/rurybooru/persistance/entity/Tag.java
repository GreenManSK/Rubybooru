package net.greenmanov.anime.rurybooru.persistance.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import net.greenmanov.iqdb.parsers.TagType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Tag entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TagType type;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

    public Tag() {
    }

    public Tag(String name, TagType type) {
        this.name = name;
        this.type = type;
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

    public void addImage(Image image) {
        images.add(image);
    }
    public void removeImage(Image image) {
        images.remove(image);
    }

    public Set<Image> getImages() {
        return ImmutableSet.copyOf(images);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag that = (Tag) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type);
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
