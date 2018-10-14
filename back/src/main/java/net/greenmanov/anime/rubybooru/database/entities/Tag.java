package net.greenmanov.anime.rubybooru.database.entities;

import net.greenmanov.iqdb.parsers.TagType;

import javax.persistence.*;
import java.util.Objects;

/**
 * Tag entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Entity
@Table(name = "tags")
public class Tag {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TagType type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name) &&
                type == tag.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
