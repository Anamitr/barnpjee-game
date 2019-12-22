package api.model;

import java.io.Serializable;
import java.util.List;

public class Minefield implements Serializable {
    String id;
    List<List<FieldType>> fieldsMatrix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<FieldType>> getFieldsMatrix() {
        return fieldsMatrix;
    }

    public void setFieldsMatrix(List<List<FieldType>> fieldsMatrix) {
        this.fieldsMatrix = fieldsMatrix;
    }
}
