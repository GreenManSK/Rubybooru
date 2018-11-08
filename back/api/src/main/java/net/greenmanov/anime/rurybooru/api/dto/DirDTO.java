package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
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
    private Long parentId;
    private Map<String, DirDTO> subDirs = new HashMap<>();

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Map<String, DirDTO> getSubDirs() {
        return subDirs;
    }

    public void setSubDirs(Map<String, DirDTO> subDirs) {
        this.subDirs = subDirs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirDTO)) return false;
        DirDTO dirDTO = (DirDTO) o;
        return Objects.equals(getName(), dirDTO.getName()) &&
                Objects.equals(getParentId(), dirDTO.getParentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getParentId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("parent", parentId)
                .add("subDirs", subDirs)
                .toString();
    }
}
