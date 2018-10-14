package net.greenmanov.anime.rubybooru.database.entities;

import javax.persistence.*;
import java.util.*;

/**
 * Directory entity
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
@Entity
@Table(name = "dirs")
public class Dir {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
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

    public Map<String, Image> getImages() {
        return Collections.unmodifiableMap(images);
    }

    public List<Dir> getSubDirs() {
        return Collections.unmodifiableList(subDirs);
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
        image.setParent(this);
        images.put(image.getName(), image);
    }

    public Image getImage(String name) {
        return images.get(name);
    }

    public boolean hasImage(String name) {
        return images.containsKey(name);
    }

    public void removeImage(Image image) {
        image.setParent(null);
        images.remove(image.getName());
    }
}
