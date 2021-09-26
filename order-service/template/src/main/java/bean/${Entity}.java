package ${targetPackage}.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

${tools.entityImports(${columns})}

@Data
public class ${Entity} extends IDEntity {

    ${tools.entityFields(${columns}, false)}
}