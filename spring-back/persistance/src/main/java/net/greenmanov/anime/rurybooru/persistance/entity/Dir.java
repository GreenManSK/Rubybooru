package net.greenmanov.anime.rurybooru.persistance.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.*;

/**
 * Directory entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Entity
public class Dir {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dir parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
    @MapKey(name = "name")
    private Map<String, Image> images = new HashMap<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Dir> subDirs = new ArrayList<>();

    public Dir() {
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

    public Dir getParent() {
        return parent;
    }

    public void setParent(Dir parent) {
        this.parent = parent;
    }

    public void addSubDir(Dir dir) {
        subDirs.add(dir);
        dir.setParent(this);
    }

    public void removeSubDir(Dir dir) {
        subDirs.remove(dir);
        dir.setParent(null);
    }

    public void addImage(Image image) {
        images.put(image.getName(), image);
        image.setParent(this);
    }

    public void removeImage(Image image) {
        images.remove(image.getName());
        image.setParent(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dir that = (Dir) o;

        return Objects.equal(this.name, that.name) &&
                Objects.equal(this.parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, parent);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("parent", parent)
                .add("images", images)
                .add("subDirs", subDirs)
                .toString();
    }
}
