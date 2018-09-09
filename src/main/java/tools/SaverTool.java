package tools;

import models.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaverTool {

    public static boolean saveEntity(final Entity currentEntity) {
        PropertyTool pt = new PropertyTool();
        if (currentEntity != null
                && !currentEntity.getName().equals("info")
                && !currentEntity.getValue().equals("")) {
            Entity encodedEntity = new Entity(currentEntity.getName(),
                    AuthTool.getEncodedText(currentEntity.getValue()));
            EntityTool saveTool = new EntityTool(false);
            saveTool.saveEntityXML(encodedEntity, new PropertyTool().getPath());

            Map<String, String> map = new HashMap<>();
            map.put(currentEntity.getName(),
                    pt.getPath() + currentEntity.getName() + ".xml");
            pt.saveProperties(map);
            return true;
        }
        return false;
    }

    public static void saveAllEntities(final List<Entity> entities) {
        for(Entity currentEntity : entities) {
            saveEntity(currentEntity);
        }
    }

}
