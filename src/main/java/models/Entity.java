package models;

import lombok.Data;

import java.util.Objects;

@Data
public class Entity {
    private String name;
    private String value;

    /**
     * Constructor with only one param.
     * @param name Name
     */
    public Entity(final String name) {
        this.name = name;
        this.value = "";
    }

    /**
     * Constructor with two param.
     * @param name Name
     */
    public Entity(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
