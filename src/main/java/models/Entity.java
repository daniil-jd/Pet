package models;

import lombok.Data;

import java.util.Objects;

/**
 * Entity class. The entity includes the title and text.
 */
@Data
public class Entity {
    /**
     * Entity name.
     */
    private String name;
    /**
     * Entity text.
     */
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

    /**
     * Gets text.
     * @return text
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     * @param value text
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name name
     */
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
