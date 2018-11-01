package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class DirDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class DirDTO {
    private Long id;
    private String name;
    private DirDTO parent;
    private Map<String, ImageDTO> images;
    private List<DirDTO> subDirs;

    public DirDTO() {
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

    public DirDTO getParent() {
        return parent;
    }

    public void setParent(DirDTO parent) {
        this.parent = parent;
    }

    public Map<String, ImageDTO> getImages() {
        return images;
    }

    public void setImages(Map<String, ImageDTO> images) {
        this.images = images;
    }

    public List<DirDTO> getSubDirs() {
        return subDirs;
    }

    public void setSubDirs(List<DirDTO> subDirs) {
        this.subDirs = subDirs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirDTO)) return false;
        DirDTO dirDTO = (DirDTO) o;
        return Objects.equals(getName(), dirDTO.getName()) &&
                Objects.equals(getParent(), dirDTO.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getParent());
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
