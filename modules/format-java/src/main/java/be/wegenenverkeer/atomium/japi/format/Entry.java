package be.wegenenverkeer.atomium.japi.format;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public final class Entry<T> {

    @XmlElement
    private String id;

    @XmlElement @XmlJavaTypeAdapter(Adapters.AtomDateTimeAdapter.class)
    DateTime updated;

    @XmlElement
    private Content<T> content;

    @XmlElement(name="link")
    private List<Link> links = new ArrayList<Link>();

    /**
     * no arg constructor, needed for JAXB and/or Jackson POJO support
     */
    private Entry() {

    }

    public Entry(String id, Content<T> content) {
        this(id, content, new ArrayList<Link>());
    }

    public Entry(String id, Content<T> content, List<Link> links) {
        this(id, new DateTime(), content, links);
    }

    public Entry(String id, DateTime updated, Content<T> content, List<Link> links) {
        this.id = id;
        this.updated = updated;
        this.content = content;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getUpdated() {
        return updated;
    }

    public void setUpdated(DateTime updated) {
        this.updated = updated;
    }

    public Content<T> getContent() {
        return content;
    }

    public void setContent(Content<T> content) {
        this.content = content;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (!content.equals(entry.content)) return false;
        if (!id.equals(entry.id)) return false;
        if (links != null ? !links.equals(entry.links) : entry.links != null) return false;
        if (updated != null ? !updated.equals(entry.updated) : entry.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + content.hashCode();
        result = 31 * result + (links != null ? links.hashCode() : 0);
        return result;
    }

    @Override
    public String    toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", updated=" + updated +
                ", content=" + content +
                ", links=" + links +
                '}';
    }
}
