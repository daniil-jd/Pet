package tools;

import models.Entity;

public class EntityTool {

    public static boolean saveEntity(final Entity entity) {
        System.out.println("save " + entity + " " + entity.getValue());
        return true;
    }
}
