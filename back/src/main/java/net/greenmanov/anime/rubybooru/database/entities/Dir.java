package net.greenmanov.anime.rubybooru.database.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * Directory entity
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
    private List<Image> images;

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

    public List<Image> getImages() {
        return Collections.unmodifiableList(images);
    }

    public void addImage(Image image) {
        image.setParent(this);
        images.add(image);
    }

    public void removeImage(Image image) {
        image.setParent(null);
        images.remove(image);
    }
}
