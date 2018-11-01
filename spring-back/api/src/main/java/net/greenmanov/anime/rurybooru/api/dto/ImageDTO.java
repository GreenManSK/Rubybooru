package net.greenmanov.anime.rurybooru.api.dto;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class ImageDTO
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class ImageDTO {
    private Long id;
    private String name;
    private Date date;
    private Integer width;
    private Integer height;
    private String source;
    private String infoSource;
    private List<TagDTO> tags;
    private DirDTO parent;

    public ImageDTO() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public DirDTO getParent() {
        return parent;
    }

    public void setParent(DirDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageDTO)) return false;
        ImageDTO imageDTO = (ImageDTO) o;
        return Objects.equals(getName(), imageDTO.getName()) &&
                Objects.equals(getParent(), imageDTO.getParent());
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
                .add("date", date)
                .add("width", width)
                .add("height", height)
                .add("source", source)
                .add("infoSource", infoSource)
                .add("tags", tags)
                .add("parent", parent)
                .toString();
    }
}
