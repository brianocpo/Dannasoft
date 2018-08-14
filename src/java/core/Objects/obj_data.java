/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.Objects;

/**
 *
 * @author brian
 */
public class obj_data {
    private String table_name;
    private String column_name;
    private String is_nullable;
    private String data_type;
    private Integer longitud;
    public obj_data() {
        longitud=0;
        data_type="";
        table_name="";
        column_name="";
        is_nullable="";
    }

    public obj_data(String table_name, String column_name, String is_nullable, String data_type) {
        this.table_name = table_name;
        this.column_name = column_name;
        this.is_nullable = is_nullable;
        this.data_type = data_type;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getIs_nullable() {
        return is_nullable;
    }

    public void setIs_nullable(String is_nullable) {
        this.is_nullable = is_nullable;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }
    
}
